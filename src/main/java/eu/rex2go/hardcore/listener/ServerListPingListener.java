package eu.rex2go.hardcore.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener extends AbstractListener {

    @EventHandler
    public void onListPing(ServerListPingEvent event) {
        event.setMotd("§b§l[!] §frex2go.eu §b§l[!]\n§cHardcore Event Server");
    }
}
