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

package de.kuscheltiermafia.schoolwars;

import de.kuscheltiermafia.schoolwars.commands.*;
import de.kuscheltiermafia.schoolwars.events.*;
import de.kuscheltiermafia.schoolwars.gameprep.NWS;
import de.kuscheltiermafia.schoolwars.gameprep.Sportler;
import de.kuscheltiermafia.schoolwars.gameprep.Sprachler;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import de.kuscheltiermafia.schoolwars.items.GenerateItems;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.LehrerHandler;
import de.kuscheltiermafia.schoolwars.mechanics.Ranzen;
import de.kuscheltiermafia.schoolwars.reputation.PlayerRepModel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

import static de.kuscheltiermafia.schoolwars.events.RevivePlayer.deadPlayers;


public final class SchoolWars extends JavaPlugin {



    public static SchoolWars plugin;

    private static int playerCount = 0;
    static Scoreboard board;

    public static boolean gameStarted;

    public static NWS nws = new NWS();
    public static Sprachler sprachler = new Sprachler();
    public static Sportler sportler = new Sportler();

    public static HashMap<String, PlayerRepModel> playerReputation = new HashMap<>();


    @Override
    public void onEnable() {

        gameStarted = false;

        plugin = this;
        board = Bukkit.getScoreboardManager().getNewScoreboard();

        for (Player p : Bukkit.getOnlinePlayers()) {
            try {
            playerReputation.put(p.getName(), new PlayerRepModel(p.getName()));
            }catch (Exception ignored){}
        }

        Items.initItems();
        SchulbuchLevels.initShelfLocations();
        SchulbuchLevels.resetBookshelf();
        GenerateItems.generateItemLocations();
        Ranzen.generateRanzenCounter();
        Lehrer.initLehrerAlgorithm();
        LehrerHandler.initLehrerQuests();
        LehrerHandler.initLehrer(4, 87, 190);

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new LeaveEvent(), this);
        pluginManager.registerEvents(new InteractionEvent(), this);
        pluginManager.registerEvents(new DeathEvent(), this);
        pluginManager.registerEvents(new RevivePlayer(), this);
        pluginManager.registerEvents(new RanzenEvents(), this);
        pluginManager.registerEvents(new HandleKuehlpack(), this);
        pluginManager.registerEvents(new PickupDrops(), this);
        pluginManager.registerEvents(new AtombombeEvents(), this);
        pluginManager.registerEvents(new KarlElixier(), this);
        pluginManager.registerEvents(new Rolator(), this);
        pluginManager.registerEvents(new Minasisierung(), this);
        pluginManager.registerEvents(new SchulbuchLevels(), this);
        pluginManager.registerEvents(new Generalschluessel(), this);
        pluginManager.registerEvents(new Lehrer(), this);

        getCommand("start").setExecutor(new StartGame());
        getCommand("clearTeams").setExecutor(new ClearTeams());
        getCommand("itemlist").setExecutor(new ItemList());
        getCommand("teamlist").setExecutor(new TeamList());
        getCommand("debug").setExecutor(new Debug());

        Teams.clearTeams();
        Ranzen.clearRanzen();

        for (Player p :Bukkit.getOnlinePlayers()) {
            Teams.resetPlayer(p);
        }

    }

    @Override
    public void onDisable() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(deadPlayers.containsKey(p.getName())) {
                Bukkit.getEntity(deadPlayers.get(p.getName())).remove();
                p.teleport(p.getLocation().add(0, 1, 0));
                p.removePotionEffect(PotionEffectType.SLOWNESS);
                p.removePotionEffect(PotionEffectType.RESISTANCE);
                p.setHealth(20);
                p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 255, true, true, true));
                deadPlayers.remove(p.getName());
            }
        }

        LehrerHandler.removeLehrer();
        playerReputation.clear();
    }

    public static SchoolWars getPlugin(){
        return plugin;
    }

    public static void setPlayerCount(int playerCount) {
        SchoolWars.playerCount = playerCount;
    }

    public static int getPlayerCount() {
        return playerCount;
    }
}
