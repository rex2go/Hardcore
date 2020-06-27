package eu.rex2go.hardcore.listener;

import eu.rex2go.hardcore.Hardcore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener extends AbstractListener {

    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();

        message = Hardcore.getChatManager().filter(message);

        event.setMessage(message);
    }
}
