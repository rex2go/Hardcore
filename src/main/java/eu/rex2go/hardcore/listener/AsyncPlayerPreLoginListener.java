package eu.rex2go.hardcore.listener;

import eu.rex2go.hardcore.Hardcore;
import eu.rex2go.hardcore.ban.Ban;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AsyncPlayerPreLoginListener extends AbstractListener {

    @EventHandler
    public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
        for(Ban ban : Hardcore.getBanManager().getBans()) {
            if(ban.getUuid().equals(event.getUniqueId())) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ban.getReason());
            }
        }
    }
}
