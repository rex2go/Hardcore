package eu.rex2go.hardcore.listener;

import eu.rex2go.hardcore.Hardcore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener extends AbstractListener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(!Hardcore.getStartManager().isStarted());
    }
}
