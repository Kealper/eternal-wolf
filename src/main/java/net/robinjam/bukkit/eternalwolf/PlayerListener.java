package net.robinjam.bukkit.eternalwolf;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * 
 * @author robinjam
 */
class PlayerListener implements Listener {

	private EternalWolf plugin;

	public PlayerListener(EternalWolf instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity target = event.getRightClicked();

		// If the player is giving a bone to a wolf
		if (player.getItemInHand().getType() == Material.BONE
				&& event.getRightClicked() instanceof Wolf) {

			Wolf wolf = (Wolf) target;

			// If the wolf is wild and the player already owns too many wolves,
			// do not allow them to tame another one
			if (!wolf.isTamed() && playerHasTooManyWolves(player)) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You cannot tame more than "
						+ plugin.getConfig().getInt("max_wolves") + " wolves!");
			} else if (wolf.isTamed()) {
				if (!player.equals(wolf.getOwner())
						&& player.hasPermission("eternalwolf.check_owner")) {
					// If the wolf is owned by another player, get that player's
					// name and UUID
					OfflinePlayer owner = (OfflinePlayer) wolf.getOwner();
					player.sendMessage(ChatColor.RED + "That wolf belongs to "
						+ owner.getName() + "(" + owner.getUniqueId() + ")");
				}
			}
		}
	}

	protected boolean playerHasTooManyWolves(Player player) {
		int maxWolves = plugin.getConfig().getInt("max_wolves");

		// If max_wolves is -1, allow each player unlimited wolves
		if (maxWolves == -1)
			return false;

		// Check if the player has permission to own many wolves
		if (player.hasPermission("eternalwolf.many_wolves"))
			return false;

		return (EternalWolf.getWolves(player).size() >= maxWolves);
	}
}
