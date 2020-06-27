package eu.rex2go.hardcore;

import eu.rex2go.hardcore.command.HeadCommand;
import eu.rex2go.hardcore.listener.*;
import eu.rex2go.hardcore.manager.*;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Hardcore extends JavaPlugin {

    @Getter
    private Location voidSpawn;

    @Getter
    private Location worldSpawn;

    @Getter
    private static Hardcore instance;

    @Getter
    private static DatabaseManager databaseManager;

    @Getter
    private static BanManager banManager;

    @Getter
    private static RevivalManager revivalManager;

    @Getter
    private static ChatManager chatManager;

    @Getter
    private static StartManager startManager;

    @Override
    public void onEnable() {
        instance = this;

        setupConfig();

        for(World world : Bukkit.getWorlds()) {
            world.setDifficulty(Difficulty.HARD);
            world.setHardcore(true);
            world.setGameRule(GameRule.NATURAL_REGENERATION, false);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        }

        try {
            databaseManager = new DatabaseManager(
                    getConfig().getString("host"),
                    getConfig().getString("database"),
                    getConfig().getString("user"),
                    getConfig().getString("password")
            );
        } catch (Exception exception) {
            getLogger().log(Level.SEVERE, "Config or credentials invalid:" + exception.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }

        registerManagers();
        registerListeners();
        registerCommands();

        worldSpawn = new Location(Bukkit.getWorld("world"), 0, 80, 0); // TODO

        if(!startManager.isStarted()) {
            Bukkit.createWorld(new WorldCreator("void"));
            voidSpawn = new Location(Bukkit.getWorld("void"), 0, 81, 0); // TODO
        }
    }

    private void setupConfig() {
        getConfig().addDefault("host", "");
        getConfig().addDefault("database", "");
        getConfig().addDefault("user", "");
        getConfig().addDefault("password", "");

        saveDefaultConfig();
    }

    private void registerCommands() {
        new HeadCommand();
    }

    private void registerManagers() {
        banManager = new BanManager();
        revivalManager = new RevivalManager();
        chatManager = new ChatManager();

        try {
            startManager = new StartManager();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void registerListeners() {
        new AsyncPlayerChatListener();
        new AsyncPlayerPreLoginListener();
        new BlockBreakListener();
        new BlockPlaceListener();
        new EntityDamageListener();
        new PlayerArmorStandManipulateListener();
        new PlayerCommandPreProcessListener();
        new PlayerCommandSendListener();
        new PlayerDeathListener();
        new PlayerDropItemListener();
        new PlayerJoinListener();
        new ServerListPingListener();
    }

    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public Object getDataSkull(Skull skull, PersistentDataType dataType, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(this, key);

        return skull.getPersistentDataContainer().get(namespacedKey, dataType);
    }

    public Object getDataSkullItem(ItemStack skull, PersistentDataType dataType, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(this, key);

        return skull.getItemMeta().getPersistentDataContainer().get(namespacedKey, dataType);
    }

    public void setDataSkull(Skull skull, PersistentDataType dataType, String key, Object data) {
        NamespacedKey namespacedKey = new NamespacedKey(this, key);

        skull.getPersistentDataContainer().set(namespacedKey, dataType, data);
    }

    public void setDataSkullItem(ItemStack skull, PersistentDataType dataType, String key, Object data) {
        NamespacedKey namespacedKey = new NamespacedKey(this, key);

        ItemMeta itemMeta = skull.getItemMeta();
        itemMeta.getPersistentDataContainer().set(namespacedKey, dataType, data);
        skull.setItemMeta(itemMeta);
    }
}
