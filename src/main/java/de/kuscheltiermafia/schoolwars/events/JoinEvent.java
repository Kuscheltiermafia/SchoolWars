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
import de.kuscheltiermafia.schoolwars.player_mirror.PlayerMirror;
import de.kuscheltiermafia.schoolwars.Team;
import io.github.realMorgon.sunriseLib.Particles;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static de.kuscheltiermafia.schoolwars.player_mirror.PlayerMirror.playerMirror;

public class JoinEvent implements Listener {

    @EventHandler
    public void onPLayerJoin(PlayerJoinEvent e){

        SchoolWars.setPlayerCount(SchoolWars.getPlayerCount() + 1);

        Player p = e.getPlayer();

        playerMirror.put(p.getName(), new PlayerMirror(p.getName()));

        p.teleport(new Location(p.getWorld(), -24, 80.5, 176, 90, 0));
        p.setRespawnLocation(new Location(p.getWorld(), -33.5, 88, 145.5, -90, 0));
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "effect give " + p.getName() + " minecraft:saturation infinite 255 true");

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

            p.sendMessage("Das Spiel hat bereits begonnen.");

            if (playerMirror.get(p.getName()).getTeam() != null){
                playerMirror.get(p.getName()).getTeam().readyPlayer(p);
                p.setGameMode(GameMode.SURVIVAL);
            }else p.sendMessage("Du bist keinem Team mehr zugeordnet.");

        }else {
            e.getPlayer().sendMessage(ChatColor.YELLOW + "[SchoolWars] Das Spiel hat noch nicht begonnen.");
            p.setGameMode(GameMode.SURVIVAL);

            Team.resetPlayer(p);
        }

        e.setJoinMessage("");

        for(Player a : Debug.joinMsg) {
            a.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.YELLOW + "" + e.getPlayer().getName() + ChatColor.DARK_GRAY + " joined SchoolWars.");
        }

    }
}
