package eu.rex2go.hardcore.listener;

import eu.rex2go.hardcore.Hardcore;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends AbstractListener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendMessage("§7Discord » https://discord.gg/SJvRtHp");

        if(!Hardcore.getStartManager().isStarted()) {
            player.teleport(Hardcore.getInstance().getVoidSpawn());
            player.setGameMode(GameMode.ADVENTURE);
        } else if(player.getLocation().getWorld().getName().equalsIgnoreCase("void")) {
            player.teleport(Hardcore.getInstance().getWorldSpawn().getWorld().getSpawnLocation());
            player.setGameMode(GameMode.SURVIVAL);
            player.setLevel(0);
        }
    }
}
