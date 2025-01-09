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
import de.kuscheltiermafia.schoolwars.mechanics.DialogueHandler;
import de.kuscheltiermafia.schoolwars.teams.Teams;
import de.kuscheltiermafia.schoolwars.items.GenerateItems;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.lehrer.LehrerQuests;
import de.kuscheltiermafia.schoolwars.lehrer.LehrerHandler;
import de.kuscheltiermafia.schoolwars.lehrer.SekretariatStundenplan;
import de.kuscheltiermafia.schoolwars.mechanics.PlayerStun;
import de.kuscheltiermafia.schoolwars.mechanics.Ranzen;
import de.kuscheltiermafia.schoolwars.player_mirror.PlayerMirror;
import de.kuscheltiermafia.schoolwars.win_conditions.AtombombeEvents;
import io.github.realMorgon.sunriseLib.SunriseLib;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

import static de.kuscheltiermafia.schoolwars.lehrer.LehrerHandler.lehrerEntityList;
import static de.kuscheltiermafia.schoolwars.mechanics.RevivePlayer.revivePlayer;


public final class SchoolWars extends JavaPlugin {

    public static SchoolWars plugin;

    private static int playerCount = 0;
    static Scoreboard board;

    public static boolean gameStarted;

    public static HashMap<String, PlayerMirror> playerMirror = new HashMap<>();

    public static World world = Bukkit.getWorld("schoolwars");


    @Override
    public void onEnable() {

        SunriseLib.setPlugin(this);

        gameStarted = false;

        plugin = this;
        board = Bukkit.getScoreboardManager().getNewScoreboard();

        for (Player p : Bukkit.getOnlinePlayers()) {
            try {
                playerMirror.put(p.getName(), new PlayerMirror(p.getName()));
            }catch (Exception ignored){}
        }

        Items.initItems();
        SchulbuchLevels.initShelfLocations();
        SchulbuchLevels.resetBookshelf();
        GenerateItems.generateItemLocations();
        Ranzen.generateRanzenCounter();
        //LehrerQuests.initLehrerAlgorithm();
        LehrerHandler.initLehrerQuests();
        DialogueHandler.initDialogues();

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new LeaveEvent(), this);
        pluginManager.registerEvents(new InteractionEvent(), this);
        pluginManager.registerEvents(new DeathEvent(), this);
        pluginManager.registerEvents(new RevivePlayerEvent(), this);
        pluginManager.registerEvents(new RanzenEvents(), this);
        pluginManager.registerEvents(new HandleKuehlpack(), this);
        pluginManager.registerEvents(new PickupDrops(), this);
        pluginManager.registerEvents(new AtombombeEvents(), this);
        pluginManager.registerEvents(new KarlElixier(), this);
        pluginManager.registerEvents(new Rolator(), this);
        pluginManager.registerEvents(new Minasisierung(), this);
        pluginManager.registerEvents(new SchulbuchLevels(), this);
        pluginManager.registerEvents(new Generalschluessel(), this);
        pluginManager.registerEvents(new LehrerQuests(), this);
        pluginManager.registerEvents(new PlayerStun(), this);
        pluginManager.registerEvents(new SekretariatStundenplan(), this);
        pluginManager.registerEvents(new FischersSpielzeug(), this);
        pluginManager.registerEvents(new DialogueHandler(), this);

        getCommand("start").setExecutor(new StartGame());
        getCommand("end").setExecutor(new EndCommand());
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
            if(!playerMirror.get(p.getName()).isAlive()) {
                revivePlayer(p, p);
            }
        }

        for(BlockDisplay ranzen : Ranzen.placedRanzen.keySet()) {
            Ranzen.placedRanzen.get(ranzen).remove();
            ranzen.remove();
        }

        LehrerHandler.removeLehrer();
        playerMirror.clear();
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
