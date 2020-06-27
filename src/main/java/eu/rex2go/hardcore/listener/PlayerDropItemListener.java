package eu.rex2go.hardcore.listener;

import eu.rex2go.hardcore.Hardcore;
import eu.rex2go.hardcore.revival.RevivalShrine;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerDropItemListener extends AbstractListener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Item item = event.getItemDrop();
        ItemStack itemStack = item.getItemStack();
        Location location = item.getLocation();
        Player player = event.getPlayer();

        if (itemStack.getType() == Material.DIAMOND) {
            List<Block> blocks = Hardcore.getNearbyBlocks(location, 2);

            for (Block block : blocks) {
                if (block.getType() == Material.GOLD_BLOCK) {
                    Location center = block.getLocation();
                    RevivalShrine revivalShrine = Hardcore.getRevivalManager().getRevivalShrine(center, player);

                    if (revivalShrine != null && !revivalShrine.isActive()) {
                        revivalShrine.startRevival();
                        item.remove();
                        location.getWorld().playSound(location, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1, 1);
                    }
                }
            }
        }
    }
}
