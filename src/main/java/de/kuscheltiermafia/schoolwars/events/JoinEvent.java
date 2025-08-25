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

package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.commands.Debug;
import de.kuscheltiermafia.schoolwars.PlayerMirror;
import de.kuscheltiermafia.schoolwars.Team;
import io.github.realMorgon.sunriseLib.Particles;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

/**
 * Handles player join events in the SchoolWars game.
 * Manages player initialization, team assignment, visual effects,
 * and welcome messages when players connect to the server.
 */
public class JoinEvent implements Listener {

    /**
     * Handles when a player joins the server.
     * Initializes player data, sets spawn location, applies effects,
     * displays welcome message, and handles team assignment based on game state.
     * 
     * @param e The PlayerJoinEvent containing information about the joining player
     */
    @EventHandler
    public void onPLayerJoin(PlayerJoinEvent e){

        // Increment player count when someone joins
        SchoolWars.setPlayerCount(SchoolWars.getPlayerCount() + 1);

        Player p = e.getPlayer();

        // Initialize player mirror if it doesn't exist (first time joining)
        if(!playerMirror.containsKey(p.getName())) playerMirror.put(p.getName(), new PlayerMirror(p.getName()));

        // Teleport player to spawn location and set respawn point
        p.teleport(new Location(p.getWorld(), -24, 80.5, 176, 90, 0));
        p.setRespawnLocation(new Location(p.getWorld(), -33.5, 88, 145.5, -90, 0));
        
        // Give infinite saturation effect to prevent hunger
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "effect give " + p.getName() + " minecraft:saturation infinite 255 true");

        // Show dramatic particle effects to all players when someone joins
        Particles.showForAll(Particle.LAVA, p.getLocation(), 40, 0, 0.2, 0, 0, 0);
        Particles.showForAll(Particle.EXPLOSION, p.getLocation(), 10, 0, 0.2, 0, 0, 0);
        Particles.showForAll(Particle.PORTAL, p.getLocation(), 100, 0, 0.2, 0, 0, 0);

        // Send welcome message to the joining player
        p.sendMessage( ChatColor.YELLOW + "---------------SCHOOL WARS---------------");
        p.sendMessage("Willkommen in diesem grandiosen Spiel,");
        p.sendMessage("in dem du auch in deiner Freizeit");
        p.sendMessage("die " + ChatColor.RED + "Freuden der Schule" + ChatColor.WHITE + " erfahren kannst.");
        p.sendMessage( ChatColor.YELLOW + "-----------------------------------------");

        // Handle player state based on whether the game has started
        if (SchoolWars.gameStarted){
            // Game is active - restore player to their team or make them spectator
            p.sendMessage("§e[SchoolWars] Das Spiel hat bereits begonnen.");

            if (playerMirror.get(p.getName()).getTeam() != null){
                // Player has a team - restore them to active play
                playerMirror.get(p.getName()).getTeam().readyPlayer(p);
                p.setGameMode(GameMode.SURVIVAL);
            }else{
                // Player has no team - make them spectator
                p.sendMessage("§e[SchoolWars] Du bist keinem Team mehr zugeordnet.");
                p.setGameMode(GameMode.SPECTATOR);
            }

        }else {
            // Game hasn't started - normal join procedure
            e.getPlayer().sendMessage(ChatColor.YELLOW + "[SchoolWars] Das Spiel hat noch nicht begonnen.");
            p.setGameMode(GameMode.SURVIVAL);

            // Reset player state for new game
            Team.resetPlayer(p);
        }

        // Remove default join message to prevent spam
        e.setJoinMessage("");

        // Send custom join message to players subscribed to join/leave notifications
        for(Player a : Debug.joinMsg) {
            a.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.YELLOW + "" + e.getPlayer().getName() + ChatColor.DARK_GRAY + " joined SchoolWars.");
        }

    }
}
