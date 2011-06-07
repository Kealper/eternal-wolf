package net.robinjam.bukkit.eternalwolf;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    PluginDescriptionFile pdf;
    static final Logger log = Logger.getLogger("Minecraft");

    int maxWolves = 5;

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

        log.log(Level.INFO, String.format("%s version %s is enabled!", pdf.getName(), pdf.getVersion()));
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
            log.log(Level.INFO, String.format("[%s] Created default configuration file", pdf.getName()));
        } else {
            config.load();
        }

        maxWolves = config.getInt("max_wolves", maxWolves);
    }
}
