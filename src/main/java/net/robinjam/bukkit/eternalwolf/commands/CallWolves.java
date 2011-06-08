package net.robinjam.bukkit.eternalwolf.commands;

import net.robinjam.bukkit.util.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

/**
 *
 * @author robinjam
 */
public class CallWolves implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
        if (!(sender instanceof Player))
            return false;

        Player player = (Player) sender;

        for (Wolf wolf : PlayerUtil.getWolves(player)) {
            wolf.setSitting(false);
            wolf.teleport(player);
        }
        
        return true;
    }

}
