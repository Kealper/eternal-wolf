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

    private EternalWolf plugin;

    public PlayerListener(EternalWolf instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getRightClicked();

        // If the player is giving a bone to a wolf
        if (player.getItemInHand().getType() == Material.BONE && 
                event.getRightClicked() instanceof Wolf) {

            Wolf wolf = (Wolf) target;

            // If the wolf is wild and the player already owns too many wolves,
            // do not allow them to tame another one
            if (!wolf.isTamed() && playerHasTooManyWolves(player)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You cannot tame more than " + plugin.maxWolves + " wolves!");
            } else if (wolf.isTamed()) {
                if (!player.getName().equals(EternalWolf.getWolfOwnerName(wolf)) && plugin.playerHasPermission(player, "eternalwolf.check_owner", true)) {
                    // If the wolf is owned by another player, get that player's name
                    player.sendMessage(ChatColor.RED + "That wolf belongs to " + EternalWolf.getWolfOwnerName(wolf));
                }
            }
        }
    }

    protected boolean playerHasTooManyWolves(Player player) {
        // If max_wolves is -1, allow each player unlimited wolves
        if (plugin.maxWolves == -1) return false;
        
        // Check if the player has permission to own many wolves
        if (plugin.playerHasPermission(player, "eternalwolf.many_wolves", false))
            return false;

        return (EternalWolf.getWolves(player).size() >= plugin.maxWolves);
    }
}
