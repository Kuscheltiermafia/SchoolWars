package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.PlayerData;
import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.config.TimeConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class PlayerFightEvent implements Listener {

    @EventHandler
    public void onPlayerFight(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            PlayerData.playerData.get(event.getEntity().getName()).setCombat(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    PlayerData.playerData.get(event.getEntity().getName()).setCombat(false);
                }
            }.runTaskLater(SchoolWars.getPlugin(), TimeConfig.getTicks("combatCooldown", 200));
        }
        if (event.getDamager() instanceof Player) {
            PlayerData.playerData.get(event.getDamager().getName()).setCombat(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    PlayerData.playerData.get(event.getDamager().getName()).setCombat(false);
                }
            }.runTaskLater(SchoolWars.getPlugin(), TimeConfig.getTicks("combatCooldown", 200));
        }

    }

}
