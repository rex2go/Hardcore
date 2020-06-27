package eu.rex2go.hardcore.listener;

import eu.rex2go.hardcore.Hardcore;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

abstract class AbstractListener implements Listener {

    AbstractListener() {
        Hardcore hardcore = Hardcore.getInstance();
        PluginManager pluginManager = hardcore.getServer().getPluginManager();

        pluginManager.registerEvents(this, hardcore);
    }
}
