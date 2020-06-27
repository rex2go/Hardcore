package eu.rex2go.hardcore.listener;


import eu.rex2go.hardcore.Hardcore;
import eu.rex2go.hardcore.revival.RevivalShrine;
import eu.rex2go.hardcore.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class BlockBreakListener extends AbstractListener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();

        if (block.getType() == Material.GOLD_BLOCK || block.getType() == Material.PLAYER_HEAD || block.getType() == Material.PLAYER_WALL_HEAD) {
            if (block.getType() == Material.PLAYER_HEAD || block.getType() == Material.PLAYER_WALL_HEAD) {
                BlockState state = block.getState();
                Skull skull = (Skull) state;

                UUID uuid = UUID.fromString((String) Hardcore.getInstance().getDataSkull(skull,
                        PersistentDataType.STRING, "uuid"));
                String name = (String) Hardcore.getInstance().getDataSkull(skull,
                        PersistentDataType.STRING, "name");

                ItemStack itemStack = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(uuid).build();

                Hardcore.getInstance().setDataSkullItem(itemStack, PersistentDataType.STRING, "uuid", uuid.toString());
                Hardcore.getInstance().setDataSkullItem(itemStack, PersistentDataType.STRING, "name", name);

                event.setDropItems(false);
                location.getWorld().dropItemNaturally(location, itemStack);

                event.getPlayer().sendMessage("§cDu hast einen Kopf von einem toten Spieler abgebaut. Du kannst ihn " +
                        "wie folgt wiederbeleben:§7 https://imgur.com/PAfihdU");
            }

            for (RevivalShrine revivalShrine : Hardcore.getRevivalManager().getRevivalShrines()) {
                if (location.equals(revivalShrine.getCenter())
                        || location.equals(revivalShrine.getCenter().clone().add(0, 1, 0))) {
                    if (revivalShrine.isActive()) event.setCancelled(true);
                }
            }
        }
    }
}
