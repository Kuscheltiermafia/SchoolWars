package de.kuscheltiermafia.schoolwars.gameprep;

import de.kuscheltiermafia.schoolwars.mechanics.Ranzen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Teams {

    public static ArrayList<String> sprachler = new ArrayList<String>();
    public static ArrayList<String> naturwissenschaftler = new ArrayList<String>();
    public static ArrayList<String> sportler = new ArrayList<String>();


    public static void joinTeams(){

        int i = Bukkit.getOnlinePlayers().size();

        for(Player p : Bukkit.getOnlinePlayers()){

            if(i % 3 == 0){
                sprachler.add(p.getName()); ;
            } else if(i % 3 == 1){
                naturwissenschaftler.add(p.getName());
            } else {
                sportler.add(p.getName());
            }

            i--;


        }

    }

    public static void configurePlayer(String playerName, String team){

        Player p = Bukkit.getPlayer(playerName);

        if(team.equals("sprachler")){
            p.sendMessage(ChatColor.YELLOW + "[SchoolWars] Du bist ein " + ChatColor.GOLD + "Sprachler");
            p.setDisplayName(ChatColor.GOLD + "[Sprache] " + p.getName());
            p.setPlayerListName(ChatColor.GOLD + "[Sprache] " + p.getName());
            p.setCustomName(ChatColor.GOLD + "[Sprache] " + p.getName());
            Ranzen.giveRanzen("sprachler", p);
        }else if(team.equals("naturwissenschaftler")){
            p.sendMessage(ChatColor.YELLOW + "[SchoolWars] Du bist ein " + ChatColor.GREEN + "Naturwissenschaftler");
            p.setDisplayName(ChatColor.GREEN + "[NWS] " + p.getName());
            p.setPlayerListName(ChatColor.GREEN + "[NWS] " + p.getName());
            p.setCustomName(ChatColor.GREEN + "[NWS] " + p.getName());
            Ranzen.giveRanzen("naturwissenschaftler", p);
        }else if(team.equals("sportler")){
            p.sendMessage( ChatColor.YELLOW + "[SchoolWars] Du bist ein " + ChatColor.DARK_RED + "Sportler");
            p.setDisplayName(ChatColor.DARK_RED + "[Sport] " + p.getName());
            p.setPlayerListName(ChatColor.DARK_RED + "[Sport] " + p.getName());
            p.setCustomName(ChatColor.DARK_RED + "[Sport] " + p.getName());
            Ranzen.giveRanzen("sportler", p);
        }

    }

    public static void clearTeams(){
        sprachler.clear();
        naturwissenschaftler.clear();
        sportler.clear();
    }

    public static void resetPlayer(Player p){
        p.setDisplayName(p.getName());
        p.setPlayerListName(p.getName());
    }

}
