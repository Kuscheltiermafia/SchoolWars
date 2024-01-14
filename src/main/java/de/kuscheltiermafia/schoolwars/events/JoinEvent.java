package de.kuscheltiermafia.schoolwars.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onPLayerJoin(PlayerJoinEvent e){

        Player p = e.getPlayer();

        p.teleport(new Location(p.getWorld(), -24, 80, 175));

        for (Player pl : Bukkit.getOnlinePlayers()){
            pl.spawnParticle(Particle.SCULK_SOUL, p.getLocation(), 100, 0.1, 0.1, 0.1);
        }

        p.sendMessage( ChatColor.YELLOW + "---------------SCHOOL WARS---------------");
        p.sendMessage("Willkommen in diesem grandiosen Spiel,");
        p.sendMessage("in dem du auch in deiner Freizeit");
        p.sendMessage("die " + ChatColor.RED + "Freuden der Schule" + ChatColor.WHITE + " erfahren kannst.");
        p.sendMessage( ChatColor.YELLOW + "-----------------------------------------");

    }

}
