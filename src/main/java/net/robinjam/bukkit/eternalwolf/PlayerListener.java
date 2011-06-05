package net.robinjam.bukkit.eternalwolf;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 *
 * @author robinjam
 */
class PlayerListener extends org.bukkit.event.player.PlayerListener {

    EternalWolf plugin;

    public PlayerListener(EternalWolf instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        event.setCancelled(false);
        Player player = event.getPlayer();
        Entity target = event.getRightClicked();

        // If the player is giving a bone to a wolf
        if (player.getItemInHand().getType() == Material.BONE && 
                event.getRightClicked() instanceof Wolf) {

            // If the wolf is wild and the player already owns too many wolves,
            // do not allow them to tame another one
            if (!((Wolf)target).isTamed() && playerHasTooManyWolves(player)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You cannot tame more than " + plugin.maxWolves + " wolves!");
            }
        }
    }

    protected boolean playerHasTooManyWolves(Player player) {
        if (plugin.maxWolves == -1) return false;

        int numWolves = 0;
        for (Entity entity : player.getWorld().getEntities()) {
            if (entity instanceof Wolf) {
                Wolf wolf = (Wolf) entity;

                if (wolf.getOwner() == player) {
                    numWolves++;
                }
            }
        }

        return (numWolves >= plugin.maxWolves);
    }
}
