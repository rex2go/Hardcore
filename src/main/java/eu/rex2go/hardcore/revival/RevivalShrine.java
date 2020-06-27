package eu.rex2go.hardcore.revival;

import eu.rex2go.hardcore.Hardcore;
import eu.rex2go.hardcore.util.ItemBuilder;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;

@Data
public class RevivalShrine {

    private Location center;

    private UUID revived;

    private Player reviver;

    private boolean active = false;

    private ArmorStand model;

    public RevivalShrine(Location center, UUID revived, Player reviver) {
        this.center = center;
        this.revived = revived;
        this.reviver = reviver;
    }

    public void startRevival() {
        Collection<Entity> entities = center.getWorld().getNearbyEntities(center, 8, 8, 8);
        RevivalShrine revivalShrine = this;

        for (Entity entity : entities) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                player.sendMessage("§7§oDas hört sich nicht gut an..");
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
            }
        }

        active = true;

        new BukkitRunnable() {
            int iterations = 0;

            @Override
            public void run() {
                if (iterations++ < 30 * 5) {
                    if (iterations == 1) {
                        center.getWorld().createExplosion(center, 5);
                        center.clone().add(0, 1, 0).getBlock().setType(Material.AIR);

                        model = (ArmorStand) center.getWorld().spawnEntity(center, EntityType.ARMOR_STAND);
                        model.setSmall(false);
                        model.setInvulnerable(true);
                        model.setArms(true);
                        model.setBasePlate(false);
                        model.setGravity(false);

                        ItemStack head =
                                new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Bukkit.getOfflinePlayer(revived)).build();
                        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
                        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
                        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

                        model.getEquipment().setHelmet(head);
                        model.getEquipment().setChestplate(chest);
                        model.getEquipment().setLeggings(legs);
                        model.getEquipment().setBoots(boots);
                    }

                    model.teleport(model.getLocation().clone().add(0, iterations * 0.005, 0));
                    model.setRotation(iterations * 3, 0);

                    if (iterations % 5 == 0) {
                        Random random = new Random();

                        int x = random.nextInt((5) + 1);
                        int z = random.nextInt((5) + 1);
                        boolean dX = random.nextInt((1) + 1) == 1;
                        boolean dZ = random.nextInt((1) + 1) == 1;

                        x = dX ? x : -x;
                        z = dZ ? z : -z;

                        center.getWorld().strikeLightning(center.clone().add(x, 0, z));
                    }
                } else {
                    center.getWorld().createExplosion(model.getLocation(), 5);
                    center.getWorld().setThundering(true);

                    reviver.sendMessage("§7§oSein Geist ist nun wieder frei..");

                    model.remove();
                    Hardcore.getRevivalManager().getRevivalShrines().remove(revivalShrine);
                    Hardcore.getBanManager().unban(revived);
                    cancel();
                }
            }
        }.runTaskTimer(Hardcore.getInstance(), 60, 1);
    }

    public boolean equals(RevivalShrine revivalShrine) {
        return revivalShrine.getCenter().equals(center) && revivalShrine.getRevived() == revived;
    }
}
