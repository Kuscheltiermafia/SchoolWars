package de.kuscheltiermafia.schoolwars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerCareerChangeEvent;

/**
 * Prevents villager NPCs from changing their professions.
 * <p>
 * Ensures teacher villagers maintain their configured appearance
 * by cancelling any profession change events.
 * </p>
 */
public class DisableProfessions implements Listener {

    @EventHandler
    public void onDisableProfessions(VillagerCareerChangeEvent event) {
        event.setCancelled(true);
    }

}
