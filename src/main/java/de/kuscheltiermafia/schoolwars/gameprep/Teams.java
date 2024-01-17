package de.kuscheltiermafia.schoolwars.gameprep;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Teams {

    static Team sport;
    static Team sprache;
    static Team nws;

    public static void prep() {

        Scoreboard board = SchoolWars.getBoard();

        sport = board.registerNewTeam("Sport");
        sprache = board.registerNewTeam("Sprache");
        nws = board.registerNewTeam("NWS");

    }

    public static void joinTeams(){

        int left = SchoolWars.getPlayerCount() + 1;

        for(Player p : Bukkit.getOnlinePlayers()){

            if (left % 3 == 0){
                sport.addEntry(p.getName());
                p.sendMessage(ChatColor.WHITE + "Du bist im Team: " + ChatColor.DARK_RED + "Sport");

            } else if (left % 4 == 1) {
                sprache.addEntry(p.getName());
                p.sendMessage(ChatColor.WHITE + "Du bist im Team: " + ChatColor.GOLD + "Sprache");
            }else{
                nws.addEntry(p.getName());
                p.sendMessage(ChatColor.WHITE + "Du bist im Team: " + ChatColor.GREEN + "Naturwissenschaftler");
            }

            left--;

        }
    }

//    public static void clearTeams(){
//
//        for(int i = 0; i < nws.getSize() + 1; i++){
//            nws.removeEntry(nws.getEntries().stream().findFirst().toString());
//        }
//        for(int i = 0; i < sport.getSize() + 1; i++){
//            nws.removeEntry(sport.getEntries().stream().findFirst().toString());
//        }
//        for(int i = 0; i < sprache.getSize() + 1; i++){
//            nws.removeEntry(sprache.getEntries().stream().findFirst().toString());
//        }
//    }

}
