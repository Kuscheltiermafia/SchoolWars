package de.kuscheltiermafia.schoolwars.gameprep;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Teams {

    static ScoreboardManager manager = Bukkit.getScoreboardManager();
    static Scoreboard board = manager.getNewScoreboard();

    static Team sport = board.registerNewTeam("Sport");
    static Team sprache = board.registerNewTeam("Sprache");
    static Team nws = board.registerNewTeam("NWS");

    public static void prep() {

        sport.setPrefix(ChatColor.DARK_RED + "[Sport]");
        sprache.setPrefix(ChatColor.GOLD + "[Sprache]");
        nws.setPrefix(ChatColor.GREEN + "[NWS]");

    }

    public void joinTeams(){

        int left = SchoolWars.getPlayerCount();

        for(Player p : Bukkit.getOnlinePlayers()){

            if (left % 3 == 0){
                sport.addPlayer(p);
            } else if (left % 4 == 1) {
                sprache.addPlayer(p);
            }else{
                nws.addPlayer(p);
            }

            left--;

        }
    }


}
