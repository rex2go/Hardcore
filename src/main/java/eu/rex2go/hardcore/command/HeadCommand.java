package eu.rex2go.hardcore.command;

import eu.rex2go.hardcore.Hardcore;
import eu.rex2go.hardcore.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class HeadCommand implements CommandExecutor {

    public HeadCommand() {
        Bukkit.getPluginCommand("head").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("head")) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (!player.isOp()) return false;
            }

            if (args.length > 2) {
                String targetName = args[0];
                Player target = null;

                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.getName().equalsIgnoreCase(targetName)) {
                        target = all;
                        break;
                    }
                }

                if (target == null) {
                    commandSender.sendMessage("Spieler nicht gefunden");
                    return false;
                }

                try {
                    String username = args[1];
                    UUID uuid = UUID.fromString(args[2]);

                    ItemStack itemStack = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(uuid).build();

                    Hardcore.getInstance().setDataSkullItem(itemStack, PersistentDataType.STRING, "uuid",
                            uuid.toString());
                    Hardcore.getInstance().setDataSkullItem(itemStack, PersistentDataType.STRING, "name", username);

                    target.getInventory().addItem(itemStack);
                } catch (Exception exception) {
                    commandSender.sendMessage("UUID Fehler");
                }
            } else {
                commandSender.sendMessage("/head <player> <username> <uuid>");
            }
        }
        return false;
    }
}
