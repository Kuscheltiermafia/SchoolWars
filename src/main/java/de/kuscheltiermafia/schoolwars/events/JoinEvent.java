/**
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
import de.kuscheltiermafia.schoolwars.gameprep.NWS;
import de.kuscheltiermafia.schoolwars.gameprep.Sportler;
import de.kuscheltiermafia.schoolwars.gameprep.Sprachler;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.reputation.PlayerRepModel;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import static de.kuscheltiermafia.schoolwars.SchoolWars.*;

public class JoinEvent implements Listener {

    @EventHandler
    public void onPLayerJoin(PlayerJoinEvent e){

        SchoolWars.setPlayerCount(SchoolWars.getPlayerCount() + 1);

        Player p = e.getPlayer();

        try {
            playerReputation.put(p.getName(), new PlayerRepModel(p.getName()));
        }catch (Exception ignored){}

        p.teleport(new Location(p.getWorld(), -24, 80.5, 176, 90, 0));
        p.setRespawnLocation(new Location(p.getWorld(), -33.5, 88, 145.5, -90, 0));

//Spawn particles
        for (Player pl : Bukkit.getOnlinePlayers()){

            pl.spawnParticle(Particle.LAVA, p.getLocation(), 40, 0, 0.2, 0);
            pl.spawnParticle(Particle.EXPLOSION, p.getLocation(), 10, 0, 0.2, 0);
            pl.spawnParticle(Particle.PORTAL, p.getLocation(), 100, 0, 0.2, 0);

        }

//Send Welcome Message
        p.sendMessage( ChatColor.YELLOW + "---------------SCHOOL WARS---------------");
        p.sendMessage("Willkommen in diesem grandiosen Spiel,");
        p.sendMessage("in dem du auch in deiner Freizeit");
        p.sendMessage("die " + ChatColor.RED + "Freuden der Schule" + ChatColor.WHITE + " erfahren kannst.");
        p.sendMessage( ChatColor.YELLOW + "-----------------------------------------");

//Put Player back in Team after Disconnect
        if (SchoolWars.gameStarted){

            p.sendMessage("Das Spiel hat bereits begonnen.");

            if (nws.mitglieder.contains(p.getName()) ){
                nws.readyPlayer(p);
                p.setGameMode(GameMode.SURVIVAL);
            } else if (sportler.mitglieder.contains(p.getName())){
                sportler.readyPlayer(p);
                p.setGameMode(GameMode.SURVIVAL);
            } else if (sprachler.mitglieder.contains(p.getName())){
                sprachler.readyPlayer(p);
                p.setGameMode(GameMode.SURVIVAL);
            } else {
                p.sendMessage(ChatColor.YELLOW + "[SchoolWars] Du bist keinem Team zugeordnet.");
                p.setGameMode(GameMode.SPECTATOR);
            }

        }else {
            e.getPlayer().sendMessage(ChatColor.YELLOW + "[SchoolWars] Das Spiel hat noch nicht begonnen.");
            p.setGameMode(GameMode.SURVIVAL);
        }

        e.setJoinMessage("");

        for(Player a : Debug.joinMsg) {
            a.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.YELLOW + "" + e.getPlayer().getName() + ChatColor.DARK_GRAY + " joined SchoolWars.");
        }

    }

}
