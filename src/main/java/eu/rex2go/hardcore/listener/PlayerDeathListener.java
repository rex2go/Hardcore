package eu.rex2go.hardcore.listener;

import eu.rex2go.hardcore.Hardcore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerDeathListener extends AbstractListener {

    private List<Material> replaceBlocks = new ArrayList<>();

    public PlayerDeathListener() {
        replaceBlocks.add(Material.AIR);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Location location = player.getLocation();
        Block block = location.getBlock();
        UUID uuid = player.getUniqueId();

        while (!replaceBlocks.contains(block.getType())) {
            block = location.add(0, 1, 0).getBlock();
        }

        block.setType(Material.PLAYER_HEAD);
        BlockState state = block.getState();
        Skull skull = (Skull) state;
        skull.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(uuid));

        Hardcore.getInstance().setDataSkull(skull, PersistentDataType.STRING, "uuid", player.getUniqueId().toString());
        Hardcore.getInstance().setDataSkull(skull, PersistentDataType.STRING, "name", player.getName());

        skull.update();

        player.getLocation().getWorld().strikeLightningEffect(player.getLocation());

        Hardcore.getBanManager().ban(player, event.getDeathMessage(), block.getLocation());
    }
}
