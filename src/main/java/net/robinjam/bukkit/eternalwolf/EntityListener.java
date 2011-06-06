package net.robinjam.bukkit.eternalwolf;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author robinjam
 */
public class EntityListener extends org.bukkit.event.entity.EntityListener {

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        // If the entity that was damaged is a wolf
        if (event.getEntity() instanceof Wolf) {
            Wolf wolf = (Wolf) event.getEntity();

            // ...and the wolf has an owner
            if (wolf.isTamed() && wolf.getOwner() instanceof Player) {

                // If the wolf was damaged by another entity
                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;

                    Player owner = (Player) wolf.getOwner();

                    // If the wolf was damaged by its owner using a bone
                    if (damageEvent.getDamager() == owner && owner.getItemInHand().getType() == Material.BONE) {
                        // Release the wolf
                        wolf.setOwner(null);
                        wolf.setSitting(false);
                        owner.sendMessage(ChatColor.RED + "You have released your wolf!");
                    }
                }

                event.setCancelled(true);
            }
        }
    }
}
