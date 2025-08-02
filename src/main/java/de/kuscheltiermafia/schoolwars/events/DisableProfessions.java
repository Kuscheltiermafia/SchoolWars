package de.kuscheltiermafia.schoolwars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerCareerChangeEvent;

public class DisableProfessions implements Listener {

    @EventHandler
    public void onDisableProfessions(VillagerCareerChangeEvent event) {
        event.setCancelled(true);
    }

}
