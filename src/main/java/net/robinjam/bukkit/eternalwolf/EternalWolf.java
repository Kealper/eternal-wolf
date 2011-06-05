package net.robinjam.bukkit.eternalwolf;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author robinjam
 */
public class EternalWolf extends JavaPlugin {

    EntityListener entityListener = new EntityListener();

    public void onEnable() {
        // Register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.Normal, this);

        // Read plugin description file
        PluginDescriptionFile pdf = this.getDescription();
        
        System.out.println(pdf.getName() + " version " + pdf.getVersion() + " is enabled!");
    }

    public void onDisable() {
        // Do nothing
    }
}
