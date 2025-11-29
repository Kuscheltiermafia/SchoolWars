package de.kuscheltiermafia.schoolwars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Manages player hunger levels during gameplay.
 * <p>
 * Keeps food level constant at maximum to prevent hunger
 * mechanics from interfering with combat.
 * </p>
 */
public class ManageFoodLevel implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e){
        e.setFoodLevel(20);
        e.setCancelled(true);
    }

}
