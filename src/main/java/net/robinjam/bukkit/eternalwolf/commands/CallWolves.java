package net.robinjam.bukkit.eternalwolf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

/**
 *
 * @author james
 */
public class CallWolves implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
        if (!(sender instanceof Player))
            return false;

        Player player = (Player) sender;
        for (LivingEntity entity : player.getWorld().getLivingEntities()) {
            if (entity instanceof Wolf) {
                Wolf wolf = (Wolf) entity;

                if (wolf.isTamed() && wolf.getOwner().equals(player)) {
                    wolf.setSitting(false);
                    wolf.teleport(player);
                }
            }
        }
        return true;
    }

}
