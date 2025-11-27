package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.commands.Debug;
import de.kuscheltiermafia.schoolwars.mechanics.EndGame;
import de.kuscheltiermafia.schoolwars.mechanics.StartGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

/**
 * Handles player leave events in SchoolWars.
 * <p>
 * When a player leaves, this class:
 * <ul>
 *   <li>Updates the player count</li>
 *   <li>Notifies debug message subscribers</li>
 *   <li>Ends the game if all players have left during an active game</li>
 *   <li>Updates the start menu if it's open</li>
 * </ul>
 * </p>
 */
public class LeaveEvent implements Listener {

    /**
     * Handles a player leaving the server.
     *
     * @param e the player quit event
     */
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        SchoolWars.setPlayerCount(SchoolWars.getPlayerCount() - 1);
        e.setQuitMessage("");
        for(Player a : Debug.joinMsg) {
            a.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.YELLOW + "" + e.getPlayer().getName() + ChatColor.DARK_GRAY + " left SchoolWars.");
        }


        if(SchoolWars.getPlayerCount() <= 0 && SchoolWars.gameStarted) {
            EndGame.end();
        }

        if(StartGame.menuOpen){
            StartGame.openGUI(StartGame.menuOpener, true);
            StartGame.menuOpen = true;
        }

    }

}
