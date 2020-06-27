package eu.rex2go.hardcore.manager;

import eu.rex2go.hardcore.Hardcore;
import eu.rex2go.hardcore.ban.Ban;
import eu.rex2go.hardcore.revival.RevivalShrine;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.*;

public class RevivalManager {

    @Getter
    public List<RevivalShrine> revivalShrines = new ArrayList<>();

    private Map<Vector, Material> expectedBlocks = new HashMap<>();

    public RevivalManager() {
        expectedBlocks.put(new Vector(0, 1, 0), Material.PLAYER_HEAD);
        expectedBlocks.put(new Vector(0, 0, 0), Material.GOLD_BLOCK);
        expectedBlocks.put(new Vector(1, -1, 0), Material.SOUL_SAND);
        expectedBlocks.put(new Vector(-1, -1, 0), Material.SOUL_SAND);
        expectedBlocks.put(new Vector(0, -1, 1), Material.SOUL_SAND);
        expectedBlocks.put(new Vector(0, -1, -1), Material.SOUL_SAND);
        expectedBlocks.put(new Vector(0, -1, 0), Material.SOUL_SAND);
        expectedBlocks.put(new Vector(1, 0, 1), Material.REDSTONE_TORCH);
        expectedBlocks.put(new Vector(1, 0, -1), Material.REDSTONE_TORCH);
        expectedBlocks.put(new Vector(-1, 0, 1), Material.REDSTONE_TORCH);
        expectedBlocks.put(new Vector(-1, 0, -1), Material.REDSTONE_TORCH);
    }

    public RevivalShrine getRevivalShrine(Location center, Player reviver) {
        long time = center.getWorld().getTime();
        if (time < 13000 || time > 23000) {
            reviver.sendMessage("§7§oScheint so, als würde es nur in der Nacht funktionieren..");
            return null;
        } else {
            int days = (int) center.getWorld().getFullTime() / 24000;
            int phase = days % 8;
            if(phase != 0) {
                reviver.sendMessage("§7§oScheint so, als bräuchte man den Vollmond für dieses Ritual..");
                return null;
            }
        }

        for (Map.Entry<Vector, Material> entry : expectedBlocks.entrySet()) {
            Vector offset = entry.getKey();
            Material expectedMaterial = entry.getValue();

            if (center.clone().add(offset).getBlock().getType() != expectedMaterial) return null;
        }

        BlockState state = center.clone().add(0, 1, 0).getBlock().getState();
        Skull skull = (Skull) state;
        UUID revived = UUID.fromString((String) Hardcore.getInstance().getDataSkull(skull,
                PersistentDataType.STRING, "uuid"));

        RevivalShrine revivalShrine = new RevivalShrine(center, revived, reviver);

        if (containsRevivalShrine(revivalShrine)) return null;

        boolean contains = false;
        for(Ban ban : Hardcore.getBanManager().getBans()) {
            if(ban.getUuid().equals(revived)) contains = true;
        }

        if(!contains) {
            reviver.sendMessage("§7§oDer Spieler ist nicht gebannt.");
            return null;
        }

        return revivalShrine;
    }

    public boolean containsRevivalShrine(RevivalShrine revivalShrine) {
        for (RevivalShrine revivalShrine1 : revivalShrines) {
            if (revivalShrine.equals(revivalShrine1)) return true;
        }

        return false;
    }
}
