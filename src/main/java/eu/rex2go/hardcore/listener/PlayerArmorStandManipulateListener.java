package eu.rex2go.hardcore.listener;

import eu.rex2go.hardcore.Hardcore;
import eu.rex2go.hardcore.revival.RevivalShrine;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class PlayerArmorStandManipulateListener extends AbstractListener {

    @EventHandler
    public void onManipulate(PlayerArmorStandManipulateEvent event) {
        Entity entity = event.getRightClicked();

        for(RevivalShrine revivalShrine : Hardcore.getRevivalManager().getRevivalShrines()) {
            if(revivalShrine.getModel() != null && revivalShrine.getModel().getEntityId() == entity.getEntityId()) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
