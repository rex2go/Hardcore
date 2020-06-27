package eu.rex2go.hardcore.listener;


import eu.rex2go.hardcore.Hardcore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class BlockPlaceListener extends AbstractListener {

    @EventHandler
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.PLAYER_HEAD) {
            ItemStack itemStack = event.getItemInHand();

            UUID uuid = UUID.fromString((String) Hardcore.getInstance().getDataSkullItem(itemStack,
                    PersistentDataType.STRING, "uuid"));
            String name = (String) Hardcore.getInstance().getDataSkullItem(itemStack, PersistentDataType.STRING,
                    "name");

            BlockState state = block.getState();
            Skull skull = (Skull) state;
            skull.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(uuid));

            Hardcore.getInstance().setDataSkull(skull, PersistentDataType.STRING, "uuid", uuid.toString());
            Hardcore.getInstance().setDataSkull(skull, PersistentDataType.STRING, "name", name);

            skull.update();
        }
    }
}
