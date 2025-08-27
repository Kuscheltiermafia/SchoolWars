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

package de.kuscheltiermafia.schoolwars;

import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.kuscheltiermafia.schoolwars.win_conditions.Ranzen.ranzenAmount;
import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

public enum Team {
    SPORTLER(ChatColor.DARK_RED + "sportler", ChatColor.DARK_RED + "[Sport] ", ChatColor.DARK_RED + "Sportler", new Location(Bukkit.getWorld("schoolwars"), 68.5, 80.0, 167.0, 90, 0)),
    SPRACHLER(ChatColor.GOLD + "sprachler", ChatColor.GOLD + "[Sprache] ", ChatColor.GOLD + "Sprachler", new Location(Bukkit.getWorld("schoolwars"), -21.5, 88.0, 146.0, -90, 0)),
    NWS(ChatColor.GREEN + "naturwissenschaftler", ChatColor.GREEN + "[NWS] ", ChatColor.GREEN + "Naturwissenschaftler", new Location(Bukkit.getWorld("schoolwars"), 4.5, 81.0, 191.5, 90, 0));

    public static int teamCount = 2;
    public static boolean randomTeams = true;

    public final String teamName;
    public final String prefix;
    public final String joinMessage;
    public final Location spawn;

    public ItemStack ranzen;

    public final ArrayList<String> mitglieder = new ArrayList<>();
    public double sekiRisk;

    Team(String teamName, String prefix, String joinMessage, Location spawn) {
        this.teamName = teamName;
        this.prefix = prefix;
        this.joinMessage = joinMessage;
        this.spawn = spawn;
    }


    public static void joinTeams(){

        int i = Bukkit.getOnlinePlayers().size();

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(players);

        List<Integer> teamChoices = new ArrayList<>();
        if(randomTeams) {   //Logic for random team choice; note that this is not the player assigning but the choice which teams will be played
            for (int j = 0; j < Team.values().length; j++) {
                teamChoices.add(j);
            }
            Collections.shuffle(teamChoices);
        }else{}  //TODO: Logic for manual team choice


        for (Player p : players) {
            Team.values()[teamChoices.get(i % teamCount)].mitglieder.add(p.getName());
            i--;
        }


    }

    public static void prepare(){
        for(Team team : Team.values()) {
            for (String playerName : team.mitglieder) {
                Player player = Bukkit.getPlayer(playerName);
                team.readyPlayer(player);
            }
            team.sekiRisk = 0.0;
        }
    }

    public void readyPlayer(Player p){
        p.sendMessage(ChatColor.YELLOW + "[SchoolWars] Du bist ein " + joinMessage + ", " + ChatColor.YELLOW + p.getName() + "!");

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        org.bukkit.scoreboard.Team team = scoreboard.getTeam(this.teamName);
        if (team == null) team = scoreboard.registerNewTeam(this.teamName);
        team.setPrefix(ChatColor.DARK_RED + this.prefix);
        team.addEntry(p.getName());
        p.setScoreboard(scoreboard);

        playerMirror.get(p.getName()).setTeam(this);

        if (!SchoolWars.gameStarted) {
            p.getInventory().addItem(ranzen);
            ranzenAmount.put(this, ranzenAmount.get(this) + 1);
            p.getInventory().addItem(Items.schulbuch1);
        }


        p.teleport(spawn);
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1, false, false, false));
    }


    public static void clearTeams(){
        if (SPRACHLER.mitglieder != null) {
            SPRACHLER.mitglieder.clear();
            NWS.mitglieder.clear();
            SPORTLER.mitglieder.clear();
        }
    }

    public static void resetPlayer(Player p){
        p.setDisplayName(p.getName());
        p.setPlayerListName(p.getName());
    }

    public static Team getByName(String name){
        for(Team team : Team.values()){
            if(team.teamName.equals(name)){
                return team;
            }
        }
        return null;
    }

}

