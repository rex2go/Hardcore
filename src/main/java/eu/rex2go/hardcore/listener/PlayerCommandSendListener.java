package eu.rex2go.hardcore.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class PlayerCommandSendListener extends AbstractListener {

    @EventHandler
    public void onCommandSend(PlayerCommandSendEvent event) {
        event.getCommands().clear();
        event.getCommands().add("msg");
    }
}
