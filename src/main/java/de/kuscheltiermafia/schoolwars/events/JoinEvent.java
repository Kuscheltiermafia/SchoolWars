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
import de.kuscheltiermafia.schoolwars.mechanics.StartGame;
import io.github.realMorgon.sunriseLib.Particles;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

/**
 * Handles player join events in SchoolWars.
 * <p>
 * When a player joins, this class:
 * <ul>
 *   <li>Creates or retrieves their PlayerMirror</li>
 *   <li>Teleports them to the spawn location</li>
 *   <li>Displays welcome particles and messages</li>
 *   <li>Handles game state (rejoining during active game)</li>
 *   <li>Updates the start menu if it's open</li>
 * </ul>
 * </p>
 */
public class JoinEvent implements Listener {

    /**
     * Handles a player joining the server.
     *
     * @param e the player join event
     */
    @EventHandler
    public void onPLayerJoin(PlayerJoinEvent e){

        SchoolWars.setPlayerCount(SchoolWars.getPlayerCount() + 1);

        Player p = e.getPlayer();

        if(!playerMirror.containsKey(p.getName())) playerMirror.put(p.getName(), new PlayerMirror(p.getName()));

        p.teleport(new Location(p.getWorld(), -24, 80.5, 176, 90, 0));
        p.setRespawnLocation(new Location(p.getWorld(), -33.5, 88, 145.5, -90, 0));
        p.setFoodLevel(20);
        p.setSaturation(0);

//Spawn particles

        Particles.showForAll(Particle.LAVA, p.getLocation(), 40, 0, 0.2, 0, 0, 0);
        Particles.showForAll(Particle.EXPLOSION, p.getLocation(), 10, 0, 0.2, 0, 0, 0);
        Particles.showForAll(Particle.PORTAL, p.getLocation(), 100, 0, 0.2, 0, 0, 0);

//Send Welcome Message
        p.sendMessage( ChatColor.YELLOW + "---------------SCHOOL WARS---------------");
        p.sendMessage("Willkommen in diesem grandiosen Spiel,");
        p.sendMessage("in dem du auch in deiner Freizeit");
        p.sendMessage("die " + ChatColor.RED + "Freuden der Schule" + ChatColor.WHITE + " erfahren kannst.");
        p.sendMessage( ChatColor.YELLOW + "-----------------------------------------");

//Put Player back in Team after Disconnect
        if (SchoolWars.gameStarted){

            p.sendMessage("§e[SchoolWars] Das Spiel hat bereits begonnen.");

            if (playerMirror.get(p.getName()).getTeam() != null){
                playerMirror.get(p.getName()).getTeam().readyPlayer(p);
                p.setGameMode(GameMode.SURVIVAL);
            }else{
                p.sendMessage("§e[SchoolWars] Du bist keinem Team mehr zugeordnet.");
                p.setGameMode(GameMode.SPECTATOR);
            }

        }else {
            e.getPlayer().sendMessage(ChatColor.YELLOW + "[SchoolWars] Das Spiel hat noch nicht begonnen.");
            p.setGameMode(GameMode.SURVIVAL);

            Team.resetPlayer(p);
        }
//Custom Join Message for selected Players
        e.setJoinMessage("");

        for(Player a : Debug.joinMsg) {
            a.sendMessage( ChatColor.YELLOW + "" + e.getPlayer().getName() + ChatColor.DARK_GRAY + " joined SchoolWars.");
        }

//update game start menu
        if(StartGame.menuOpen){
            StartGame.openGUI(StartGame.menuOpener, false);
            StartGame.menuOpen = true;
        }

    }
}
