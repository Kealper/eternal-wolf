package net.robinjam.bukkit.util;

import org.bukkit.craftbukkit.entity.CraftWolf;
import org.bukkit.entity.Wolf;

/**
 *
 * @author robinjam
 */
public class WolfUtil {

    public static String getWolfOwnerName(Wolf w) {
        CraftWolf wolf = (CraftWolf) w;
        return wolf.getHandle().x();
    }

}
