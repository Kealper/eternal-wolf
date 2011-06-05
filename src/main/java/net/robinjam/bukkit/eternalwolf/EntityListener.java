package net.robinjam.bukkit.eternalwolf;

import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author robinjam
 */
public class EntityListener extends org.bukkit.event.entity.EntityListener {

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Wolf) {
            Wolf wolf = (Wolf)event.getEntity();
            if (wolf.isTamed())
                event.setCancelled(true);
        }
    }

}
