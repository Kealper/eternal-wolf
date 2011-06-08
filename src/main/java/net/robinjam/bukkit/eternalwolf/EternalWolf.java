package net.robinjam.bukkit.eternalwolf;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.robinjam.bukkit.eternalwolf.commands.CallWolves;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

/**
 *
 * @author robinjam
 */
public class EternalWolf extends JavaPlugin {

    private EntityListener entityListener = new EntityListener(this);
    private PlayerListener playerListener = new PlayerListener(this);
    private PluginDescriptionFile pdf;
    private PermissionHandler permissionHandler;

    public static final Logger log = Logger.getLogger("Minecraft");

    public int maxWolves = 5;

    public void onEnable() {
        // Register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, playerListener, Event.Priority.Normal, this);

        // Register commands
        this.getCommand("call-wolves").setExecutor(new CallWolves(this));

        // Read plugin description file
        pdf = this.getDescription();

        // Load config.yml
        loadConfiguration();

        // Set up permissions
        setupPermissions();

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

    private void setupPermissions() {
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (permissionHandler == null) {
            if (permissionsPlugin != null) {
                permissionHandler = ((Permissions) permissionsPlugin).getHandler();
            } else {
                log.info(String.format("[%s] Permissions plugin not detected - using default permissions instead", pdf.getName()));
            }
        }
    }
    
    public boolean playerHasPermission(Player player, String nodes, boolean default_) {
        if (this.permissionHandler != null)
            return this.permissionHandler.has(player, nodes);
        else
            return default_;
    }
}
