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

import de.kuscheltiermafia.schoolwars.config.ProbabilityConfig;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.win_conditions.Ranzen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.kuscheltiermafia.schoolwars.win_conditions.Ranzen.ranzenAmount;
import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

/**
 * Represents the available teams in SchoolWars.
 * <p>
 * Teams are based on different academic focuses (Sports, Languages, Natural Sciences).
 * Each team has a unique color, spawn location, and backpack (Ranzen) item that must be protected.
 * </p>
 * <p>
 * Teams are responsible for:
 * <ul>
 *   <li>Managing their member list</li>
 *   <li>Tracking secretariat risk levels</li>
 *   <li>Preparing players for battle</li>
 *   <li>Managing team-specific items like backpacks</li>
 * </ul>
 * </p>
 */
public enum Team {
    /** Sports team (Sportler) - Red colored team focused on athletics. */
    SPORTLER(ChatColor.DARK_RED + "sportler", ChatColor.DARK_RED + "[Sport] ", ChatColor.DARK_RED + "Sportler", new Location(Bukkit.getWorld("schoolwars"), 68.5, 80.0, 167.0, 90, 0), Items.sport_ranzen, Ranzen.SPORTLER),
    
    /** Language team (Sprachler) - Gold/Yellow colored team focused on languages. */
    SPRACHLER(ChatColor.GOLD + "sprachler", ChatColor.GOLD + "[Sprache] ", ChatColor.GOLD + "Sprachler", new Location(Bukkit.getWorld("schoolwars"), -21.5, 88.0, 146.0, -90, 0), Items.sprach_ranzen, Ranzen.SPRACHLER),
    
    /** Natural Sciences team (NWS) - Green colored team focused on science subjects. */
    NWS(ChatColor.GREEN + "naturwissenschaftler", ChatColor.GREEN + "[NWS] ", ChatColor.GREEN + "Naturwissenschaftler", new Location(Bukkit.getWorld("schoolwars"), 4.5, 81.0, 191.5, 90, 0), Items.nws_ranzen, Ranzen.NWS);

    /** Number of teams participating in the current game. */
    public static int teamCount = 2;
    
    /** Whether teams should be randomly assigned or manually chosen. */
    public static boolean randomTeams = true;

    /** Internal team name identifier. */
    public final String teamName;
    
    /** Chat prefix displayed before player names. */
    public final String prefix;
    
    /** Message shown when a player joins this team. */
    public final String joinMessage;
    
    /** Spawn location for team members. */
    public final Location spawn;
    
    /** The backpack item associated with this team. */
    public final ItemStack ranzen_item;
    
    /** The Ranzen enum value for this team's backpack. */
    public final Ranzen ranzen;

    /** List of player names that are members of this team. */
    public final ArrayList<String> mitglieder = new ArrayList<>();
    
    /** Risk level for secretariat interactions. */
    public double sekiRisk;

    Team(String teamName, String prefix, String joinMessage, Location spawn, ItemStack ranzen_item, Ranzen ranzen) {
        this.teamName = teamName;
        this.prefix = prefix;
        this.joinMessage = joinMessage;
        this.spawn = spawn;
        this.ranzen_item = ranzen_item;
        this.ranzen = ranzen;
    }

    /**
     * Distributes all online players into teams.
     * <p>
     * Players are shuffled and assigned to teams in a round-robin fashion.
     * The number of active teams is determined by {@link #teamCount}.
     * If {@link #randomTeams} is true, teams are randomly selected; otherwise,
     * manual selection logic applies (not yet implemented).
     * </p>
     */
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

    /**
     * Prepares all teams and their members for the game.
     * <p>
     * Iterates through all teams and calls {@link #readyPlayer(Player)} for each member.
     * Also resets the secretariat risk for each team.
     * </p>
     */
    public static void prepare(){
        for(Team team : Team.values()) {
            for (String playerName : team.mitglieder) {
                Player player = Bukkit.getPlayer(playerName);
                team.readyPlayer(player);
            }
            team.sekiRisk = 0.0;
        }
    }

    /**
     * Prepares a player to join this team.
     * <p>
     * This method:
     * <ul>
     *   <li>Sends a welcome message to the player</li>
     *   <li>Sets up the player's scoreboard with team prefix</li>
     *   <li>Associates the player with this team in their PlayerMirror</li>
     *   <li>Gives the player their team's backpack and starting equipment (if game not started)</li>
     *   <li>Teleports the player to the team spawn</li>
     *   <li>Applies a brief blindness effect for dramatic effect</li>
     * </ul>
     * </p>
     *
     * @param p the player to prepare for this team
     */
    public void readyPlayer(Player p){

        if (Math.random() == ProbabilityConfig.getProbability("message.wizard_harry", 0.05)){
            p.sendMessage(ChatColor.YELLOW + "[SchoolWars] Du bist ein " + ChatColor.LIGHT_PURPLE + "Zauberer" + ChatColor.YELLOW + ", Harry");
            p.sendMessage(ChatColor.YELLOW + "[SchoolWars] Ne, warte... Falscher Text...");
        }

        p.sendMessage(ChatColor.YELLOW + "[SchoolWars] Du bist ein " + joinMessage + ", " + ChatColor.YELLOW + p.getName() + "!");

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        org.bukkit.scoreboard.Team team = scoreboard.getTeam(this.teamName);
        if (team == null) team = scoreboard.registerNewTeam(this.teamName);
        team.setPrefix(ChatColor.DARK_RED + this.prefix);
        team.addEntry(p.getName());
        p.setScoreboard(scoreboard);

        playerMirror.get(p.getName()).setTeam(this);

        if (!SchoolWars.gameStarted) {
            p.getInventory().addItem(ranzen_item);
            ranzenAmount.put(this, ranzenAmount.get(this) + 1);
            p.getInventory().addItem(Items.schulbuch1);
        }


        p.teleport(spawn);
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1, false, false, false));
    }


    /**
     * Clears all team member lists and resets secretariat risk.
     * <p>
     * Should be called when ending a game or resetting the game state.
     * </p>
     */
    public static void clearTeams(){
        for (Team team : Team.values()) {
            team.mitglieder.clear();
            team.sekiRisk = 0.0;
        }
    }

    /**
     * Resets a player's display name to their default name.
     * <p>
     * Removes any team prefixes or formatting from the player's name.
     * </p>
     *
     * @param p the player to reset
     */
    public static void resetPlayer(Player p){
        p.setDisplayName(p.getName());
        p.setPlayerListName(p.getName());
    }

    /**
     * Finds a team by its internal name.
     *
     * @param name the team name to search for
     * @return the matching Team, or null if not found
     */
    public static Team getByName(String name){
        for(Team team : Team.values()){
            if(team.teamName.equals(name)){
                return team;
            }
        }
        return null;
    }

}

