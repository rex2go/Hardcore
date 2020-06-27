package eu.rex2go.hardcore.manager;

import com.zaxxer.hikari.HikariDataSource;
import eu.rex2go.hardcore.Hardcore;
import eu.rex2go.hardcore.ban.Ban;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BanManager {

    @Getter
    private List<Ban> bans = new ArrayList<>();

    public BanManager() {
        loadBans();
    }

    public void ban(Player player, String deathMessage, Location skullLocation) {
        String coordinates =
                "X: " + skullLocation.getBlockX() + " Y: " + skullLocation.getBlockY() + " Z: " + skullLocation.getBlockZ();
        String message =
                "§c" + deathMessage +
                        "\n\n§fAndere Spieler können dich mit einem Ritual zurückholen." +
                        "\n\n§f" + coordinates +
                        "\n\n§7Discord » https://discord.gg/SJvRtHp";
        Ban ban = new Ban(player.getUniqueId(), message);
        bans.add(ban);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.kickPlayer(message);
            }
        }.runTaskLater(Hardcore.getInstance(), 1);


        try {
            DatabaseManager databaseManager = Hardcore.getDatabaseManager();

            if (databaseManager != null) {
                HikariDataSource dataSource = databaseManager.getDataSource();

                if (dataSource != null) {
                    Connection connection = dataSource.getConnection();

                    PreparedStatement ps = connection.prepareStatement("INSERT IGNORE INTO `hardcore_bans` (uuid, " +
                            "username, message) VALUES (?, ?, ?)");
                    ps.setString(1, player.getUniqueId().toString());
                    ps.setString(2, player.getName());
                    ps.setString(3, message);
                    ps.execute();

                    ps.close();
                    connection.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unban(UUID uuid) {
        List<Ban> unbans = new ArrayList<>();

        for(Ban ban : bans) {
            if(uuid.equals(ban.getUuid())) unbans.add(ban);
        }

        for(Ban unban : unbans) {
            bans.remove(unban);
        }

        try {
            DatabaseManager databaseManager = Hardcore.getDatabaseManager();

            if (databaseManager != null) {
                HikariDataSource dataSource = databaseManager.getDataSource();

                if (dataSource != null) {
                    Connection connection = dataSource.getConnection();

                    PreparedStatement ps = connection.prepareStatement("DELETE FROM `hardcore_bans` WHERE uuid = ?");
                    ps.setString(1, uuid.toString());
                    ps.execute();

                    ps.close();
                    connection.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBans() {
        try {
            DatabaseManager databaseManager = Hardcore.getDatabaseManager();

            if (databaseManager != null) {
                HikariDataSource dataSource = databaseManager.getDataSource();

                if (dataSource != null) {
                    Connection connection = dataSource.getConnection();

                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM `hardcore_bans`");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        UUID uuid = UUID.fromString(rs.getString("uuid"));
                        String message = rs.getString("message");

                        bans.add(new Ban(uuid, message));
                    }

                    databaseManager.closeResources(rs, ps);
                    connection.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
