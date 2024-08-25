package de.kuscheltiermafia.schoolwars.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class ParticleHandler {

    public static void createParticles(Location loc, Particle particle, int count, int speed, boolean everyone, @Nullable ArrayList<Player> players) {
        if(everyone) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spawnParticle(particle, loc, count, 0, 0, 0, speed);
            }
        } else if(players != null) {
            for (Player p : players) {
                p.spawnParticle(particle, loc, count, 0, 0, 0, speed);
            }
        }
    }
}
