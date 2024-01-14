package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinEvent implements Listener {

    @EventHandler
    public void onPLayerJoin(PlayerJoinEvent e){

        Player p = e.getPlayer();

        p.teleport(new Location(p.getWorld(), -24, 80, 175));

        for (Player pl : Bukkit.getOnlinePlayers()){

            pl.spawnParticle(Particle.LAVA, p.getLocation(), 1, 0.01, 0.01, 0.01);
//            pl.spawnParticle(Particle.EXPLOSION_LARGE, p.getLocation(), 5, 1);
//            pl.spawnParticle(Particle.PORTAL, p.getLocation(), 200, 4);

        }


        p.sendMessage( ChatColor.YELLOW + "---------------SCHOOL WARS---------------");
        p.sendMessage("Willkommen in diesem grandiosen Spiel,");
        p.sendMessage("in dem du auch in deiner Freizeit");
        p.sendMessage("die " + ChatColor.RED + "Freuden der Schule" + ChatColor.WHITE + " erfahren kannst.");
        p.sendMessage( ChatColor.YELLOW + "-----------------------------------------");

    }

}
