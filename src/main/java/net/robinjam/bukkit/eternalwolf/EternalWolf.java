package net.robinjam.bukkit.eternalwolf;

import java.io.File;
import net.robinjam.bukkit.eternalwolf.commands.CallWolves;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

/**
 *
 * @author robinjam
 */
public class EternalWolf extends JavaPlugin {

    EntityListener entityListener = new EntityListener();
    PlayerListener playerListener = new PlayerListener(this);
    int maxWolves = 5;
    PluginDescriptionFile pdf;

    public void onEnable() {
        // Register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, playerListener, Event.Priority.Normal, this);

        // Register commands
        this.getCommand("call-wolves").setExecutor(new CallWolves());

        // Read plugin description file
        pdf = this.getDescription();

        // Load config.yml
        loadConfiguration();
        
        System.out.println(pdf.getName() + " version " + pdf.getVersion() + " is enabled!");
    }

    public void onDisable() {
        // Do nothing
    }

    protected void loadConfiguration() {
        File configFile = new File(getDataFolder(), "config.yml");
        Configuration config = new Configuration(configFile);

        if (!configFile.exists()) {
            config.setProperty("max_wolves", maxWolves);
            config.save();
            System.out.println("[" + pdf.getName() + "] Created default configuration file");
        } else {
            config.load();
        }

        maxWolves = config.getInt("max_wolves", maxWolves);
    }
}
