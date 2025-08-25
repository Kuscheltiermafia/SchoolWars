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
import de.kuscheltiermafia.schoolwars.mechanics.EndGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

/**
 * Handles player leave events in the SchoolWars game.
 * Manages player count tracking and game state when players disconnect.
 */
public class LeaveEvent implements Listener {

    /**
     * Handles when a player leaves the server.
     * Updates player count, sends custom leave messages to debug subscribers,
     * and triggers game end if no players remain during an active game.
     * 
     * @param e The PlayerQuitEvent containing information about the leaving player
     */
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        // Decrease the player count when someone leaves
        SchoolWars.setPlayerCount(SchoolWars.getPlayerCount() - 1);
        
        // Remove default quit message to prevent spam
        e.setQuitMessage("");
        
        // Send custom leave message to players subscribed to join/leave notifications
        for(Player a : Debug.joinMsg) {
            a.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.YELLOW + "" + e.getPlayer().getName() + ChatColor.DARK_GRAY + " left SchoolWars.");
        }

        // End the game if no players remain during an active game
        // TODO: This does not work - needs investigation
        if(SchoolWars.getPlayerCount() <= 0 && SchoolWars.gameStarted) {
            EndGame.end();
        }

    }

}
