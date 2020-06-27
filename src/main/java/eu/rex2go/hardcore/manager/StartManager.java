package eu.rex2go.hardcore.manager;

import eu.rex2go.hardcore.Hardcore;
import eu.rex2go.hardcore.util.TimeStringUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartManager {

    @Getter
    private boolean started;

    public StartManager() throws ParseException {
        Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2020-05-26 19:30:00.000");
        started = start.before(new Date());

        if (!started) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    int[] announcementSeconds = new int[]{60 * 10, 60 * 5, 180, 120, 90, 60, 30, 20, 15, 10, 5, 4, 3,
                            2, 1};

                    if (!start.before(new Date())) {
                        int secondsRemaining = (int) ((start.getTime() - new Date().getTime()) / 1000);

                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.setFoodLevel(20);
                            all.setLevel(secondsRemaining);
                        }

                        for (int announcementSecond : announcementSeconds) {
                            if (announcementSecond == secondsRemaining) {
                                Bukkit.broadcastMessage(
                                        "§c[Hardcore] §7Das Projekt startet in §e"
                                                + TimeStringUtil.formatSeconds(secondsRemaining));
                                break;
                            }
                        }
                    } else {
                        start();
                        this.cancel();
                    }
                }

            }.runTaskTimer(Hardcore.getInstance(), 20, 20);
        }
    }

    private void start() {
        started = true;

        Hardcore.getInstance().getWorldSpawn().getWorld().setTime(0);
        Hardcore.getInstance().getWorldSpawn().getWorld().setThundering(false);

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setSaturation(20);
            all.setHealthScale(20);
            all.setHealth(20);
            all.setFoodLevel(20);
            all.setLevel(0);
            all.setExp(0);
            all.setFallDistance(0);
            all.setGameMode(GameMode.SURVIVAL);

            all.teleport(Hardcore.getInstance().getWorldSpawn().getWorld().getSpawnLocation());
        }
    }
}
