/*
 * ███╗   ███╗ █████╗ ██████╗ ███████╗    ██████╗ ██╗   ██╗
 * ████╗ ████║██╔══██╗██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝
 * ██╔████╔██║███████║██║  ██║█████╗      ██████╔╝ ╚████╔╝
 * ██║╚██╔╝██║██╔══██║██║  ██║██╔══╝      ██╔══██╗  ╚██╔╝
 * ██║ ╚═╝ ██║██║  ██║██████╔╝███████╗    ██████╔╝   ██║
 * ╚═╝     ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝    ╚═════╝    ╚═╝
 *
 * ██╗  ██╗██╗   ██╗███████╗ ██████╗██╗  ██╗███████╗██╗  ████████╗██╗███████╗██████╗ ███╗   ███╗ █████╗ ███████╗██╗ █████╗
 * ██║ ██╔╝██║   ██║██╔════╝██╔════╝██║  ██║██╔════╝██║  ╚══██╔══╝██║██╔════╝██╔══██╗████╗ ████║██╔══██╗██╔════╝██║██╔══██╗
 * █████╔╝ ██║   ██║███████╗██║     ███████║█████╗  ██║     ██║   ██║█████╗  ██████╔╝██╔████╔██║███████║█████╗  ██║███████║
 * ██╔═██╗ ██║   ██║╚════██║██║     ██╔══██║██╔══╝  ██║     ██║   ██║██╔══╝  ██╔══██╗██║╚██╔╝██║██╔══██║██╔══╝  ██║██╔══██║
 * ██║  ██╗╚██████╔╝███████║╚██████╗██║  ██║███████╗███████╗██║   ██║███████╗██║  ██║██║ ╚═╝ ██║██║  ██║██║     ██║██║  ██║
 * ╚═╝  ╚═╝ ╚═════╝ ╚══════╝ ╚═════╝╚═╝  ╚═╝╚══════╝╚══════╝╚═╝   ╚═╝╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝
 *
 * This is a plugin from Morgon and CrAzyA22 - Unless explicitly stated otherwise you are not permitted to use any of the given code!
 *
 */

package de.kuscheltiermafia.schoolwars.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Utility class for creating particle effects in the game world.
 * <p>
 * Provides methods for:
 * <ul>
 *   <li>Basic particle spawning at locations</li>
 *   <li>Circular particle patterns</li>
 *   <li>Line drawing between two points</li>
 * </ul>
 * </p>
 */
public class ParticleHandler {

    /**
     * Creates particles at a location visible to specified players.
     *
     * @param loc the location to spawn particles
     * @param particle the type of particle to spawn
     * @param count the number of particles
     * @param speed the speed/spread of particles
     * @param everyone if true, show to all online players; otherwise use the players list
     * @param players list of specific players to show particles to (if everyone is false)
     */
    public static void createParticles(Location loc, Particle particle, int count, double speed, boolean everyone, @Nullable ArrayList<Player> players) {
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

    /**
     * Creates a circle of particles around a center point.
     *
     * @param center the center location of the circle
     * @param particle the type of particle to spawn
     * @param radius the radius of the circle
     * @param points the number of particles to place around the circle
     */
    public static void createParticleCircle(Location center, Particle particle, double radius, int points) {
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            Location particleLoc = new Location(center.getWorld(), x, center.getY(), z);
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.spawnParticle(particle, particleLoc, 1, 0, 0, 0, 0);
            }
        }
    }

    /**
     * Draws a line of particles between two locations.
     *
     * @param start the starting location
     * @param end the ending location
     * @param particle the type of particle to spawn
     * @param spacing the distance between each particle
     */
    public static void drawParticleLine(Location start, Location end, Particle particle, double spacing) {
        double distance = start.distance(end);
        int points = (int) (distance / spacing);
        double dx = (end.getX() - start.getX()) / points;
        double dy = (end.getY() - start.getY()) / points;
        double dz = (end.getZ() - start.getZ()) / points;

        for (int i = 0; i <= points; i++) {
            Location point = start.clone().add(dx * i, dy * i, dz * i);
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.spawnParticle(particle, point, 1, 0, 0, 0, 0);
            }
        }
    }
}
