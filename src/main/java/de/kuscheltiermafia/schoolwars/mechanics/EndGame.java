package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.lehrer.Lehrer;
import de.kuscheltiermafia.schoolwars.lehrer.LehrerHandler;
import de.kuscheltiermafia.schoolwars.lehrer.Stundenplan;
import de.kuscheltiermafia.schoolwars.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class EndGame {

    public static void end(){
        SchoolWars.gameStarted = false;

        for (Player p : Bukkit.getOnlinePlayers()) {
            Teams.resetPlayer(p);
        }
        Teams.clearTeams();

        for (Villager lehrer : LehrerHandler.lehrerEntityList) {
            lehrer.remove();
        }
    }

}
