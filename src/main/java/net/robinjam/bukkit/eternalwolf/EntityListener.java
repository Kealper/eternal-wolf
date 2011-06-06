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

                    System.out.println(damageEvent.getDamager());
                    System.out.println(wolf.getOwner());

                    // If the wolf was damaged by its owner using a bone
                    if (damageEvent.getDamager().equals(owner) && owner.getItemInHand().getType() == Material.BONE) {
                        // Release the wolf
                        wolf.setOwner(null);
                        wolf.setSitting(false);
                        owner.sendMessage(ChatColor.RED + "You have released your wolf!");
                    }
                }

                event.setCancelled(true);
            }
        }

        // The following is a bugfix to prevent tamed wolves becoming agressive
        // if their owner hits them with an arrow at point-blank
        
        // If the entity that was damaged is a player
        if (event.getEntity() instanceof Player && event instanceof EntityDamageByEntityEvent) {
            Player player = (Player) event.getEntity();
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;

            // ...and the attacker is their own wolf
            if (damageEvent.getDamager() instanceof Wolf) {
                Wolf wolf = (Wolf) damageEvent.getDamager();

                if (wolf.isTamed() && wolf.getOwner().equals(player)) {
                    wolf.setTarget(null);
                    event.setCancelled(true);
                }
            }

            // ...or they're attacking themself (how is that even possible?)
            if (damageEvent.getDamager().equals(damageEvent.getEntity()))
                event.setCancelled(true);
        }
    }
    
}
