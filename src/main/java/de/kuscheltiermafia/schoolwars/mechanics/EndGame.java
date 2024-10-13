package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EndGame {

    public static void end(){
        SchoolWars.gameStarted = false;

        for (Player p : Bukkit.getOnlinePlayers()) {
            Teams.resetPlayer(p);
        }
        Teams.clearTeams();
    }

}
