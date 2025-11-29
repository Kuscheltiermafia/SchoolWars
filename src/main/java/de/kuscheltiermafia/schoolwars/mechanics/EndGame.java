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

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.Team;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.lehrer.Lehrer;
import de.kuscheltiermafia.schoolwars.win_conditions.Ranzen;
import io.github.realMorgon.sunriseLib.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import static de.kuscheltiermafia.schoolwars.mechanics.RevivePlayer.revivePlayer;
import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

/**
 * Handles the cleanup and reset when a SchoolWars game ends.
 * <p>
 * This class is responsible for:
 * <ul>
 *   <li>Reviving all downed players</li>
 *   <li>Resetting player display names</li>
 *   <li>Removing all game entities (teachers, backpacks, items)</li>
 *   <li>Clearing team assignments and scoreboards</li>
 * </ul>
 * </p>
 */
public class EndGame {

    /**
     * Ends the current game and resets all game state.
     * <p>
     * Does nothing if no game is currently active.
     * </p>
     */
    public static void end(){

        if(!SchoolWars.gameStarted) {
            return;
        }

        SchoolWars.gameStarted = false;

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(!playerMirror.get(p.getName()).isAlive()) {
                revivePlayer(p, p);
            }
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            Team.resetPlayer(p);
        }
        Team.clearTeams();

        for (Villager lehrer : Lehrer.lehrerEntityList) {
            lehrer.remove();
        }

        for(BlockDisplay ranzen : Ranzen.placedRanzen.keySet()) {
            Ranzen.placedRanzen.get(ranzen).remove();
            ranzen.remove();
        }

        Items.clearSpawnedItems();

        Lehrer.removeAllLehrer();
        playerMirror.clear();

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for (org.bukkit.scoreboard.Team team  : scoreboard.getTeams()) {
            team.unregister();
        }

        Message.sendToAllPlayers("§e[SchoolWars] Das Spiel wurde beendet!");
    }
}
