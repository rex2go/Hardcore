package eu.rex2go.hardcore.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreProcessListener extends AbstractListener {

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (player.isOp()) return;

        String first = event.getMessage().split(" ")[0];
        first = first.substring(1);

        if (!(first.equalsIgnoreCase("msg") || first.equalsIgnoreCase("tell"))) {
            event.setCancelled(true);
            player.sendMessage("§cmeh");
        }
    }
}
