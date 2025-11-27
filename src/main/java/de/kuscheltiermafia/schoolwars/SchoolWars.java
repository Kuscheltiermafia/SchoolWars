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

import co.aikar.commands.PaperCommandManager;
import de.kuscheltiermafia.schoolwars.commands.*;
import de.kuscheltiermafia.schoolwars.config.ProbabilityConfig;
import de.kuscheltiermafia.schoolwars.events.*;
import de.kuscheltiermafia.schoolwars.mechanics.*;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.lehrer.LehrerQuests;
import de.kuscheltiermafia.schoolwars.lehrer.SekretariatStundenplan;
import de.kuscheltiermafia.schoolwars.win_conditions.AtombombeBossfight;
import de.kuscheltiermafia.schoolwars.win_conditions.events_atombombe.AtombombeEvents;
import de.kuscheltiermafia.schoolwars.win_conditions.Ranzen;
import de.kuscheltiermafia.schoolwars.win_conditions.events_atombombe.BaarsKaffee;
import de.kuscheltiermafia.schoolwars.win_conditions.events_atombombe.Fluor;
import de.kuscheltiermafia.schoolwars.win_conditions.events_atombombe.Zentrifuge;
import io.github.realMorgon.sunriseLib.SunriseLib;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

/**
 * Main plugin class for SchoolWars - a Minecraft minigame plugin.
 * <p>
 * SchoolWars is a team-based game where players compete in a school-themed environment.
 * Teams must protect their backpacks (Ranzen) while trying to destroy enemy backpacks
 * or complete objectives like building an atomic bomb.
 * </p>
 * <p>
 * This class handles plugin initialization, event registration, command registration,
 * and manages the global game state.
 * </p>
 *
 * @author Morgon
 * @author CrAzyA22
 * @version 0.1.0
 */
public final class SchoolWars extends JavaPlugin {

    /** Singleton instance of the plugin for global access. */
    public static SchoolWars plugin;

    /** Current number of players on the server. */
    private static int playerCount = 0;

    /** Flag indicating whether a game is currently in progress. */
    public static boolean gameStarted;

    /** Reference to the main game world ("schoolwars"). */
    public static World WORLD;


    /**
     * Called when the plugin is enabled.
     * <p>
     * Initializes all game components including:
     * <ul>
     *   <li>Game world reference</li>
     *   <li>Probability configuration</li>
     *   <li>Player mirrors for all online players</li>
     *   <li>Game items and mechanics</li>
     *   <li>Event listeners</li>
     *   <li>Commands</li>
     * </ul>
     * </p>
     */
    @Override
    public void onEnable() {

        SunriseLib.setPlugin(this);

        WORLD  = Bukkit.getWorld("schoolwars");

        gameStarted = false;

        plugin = this;

        // Initialize probability configuration
        ProbabilityConfig.initialize(this);

        for (Player p : Bukkit.getOnlinePlayers()) {
            try {
                playerMirror.put(p.getName(), new PlayerMirror(p.getName()));
            }catch (Exception ignored){}
            playerCount++;
        }

        Items.initItems();
        SchulbuchLevels.initShelfLocations();
        SchulbuchLevels.resetBookshelf();
        Ranzen.initRanzenCounter();
        //LehrerQuests.initLehrerAlgorithm();
        LehrerQuests.initLehrerQuests();
        DialogueHandler.initDialogues();

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new LeaveEvent(), this);
        pluginManager.registerEvents(new InteractionEvent(), this);
        pluginManager.registerEvents(new PlayerDeath(), this);
        pluginManager.registerEvents(new RevivePlayerEvent(), this);
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
        pluginManager.registerEvents(new DisableProfessions(), this);
        pluginManager.registerEvents(new FachraumSchluessel(), this);
        pluginManager.registerEvents(new VornbergerEvents(), this);
        pluginManager.registerEvents(new Fluor(), this);
        pluginManager.registerEvents(new Zentrifuge(), this);
        pluginManager.registerEvents(new BaarsKaffee(), this);
        pluginManager.registerEvents(new Vapes(), this);
        pluginManager.registerEvents(new StartGame(), this);
        pluginManager.registerEvents(new ManageFoodLevel(), this);
        pluginManager.registerEvents(new AtombombeBossfight(), this);
        pluginManager.registerEvents(new RanzenEvents(), this);

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new Debug());
        manager.registerCommand(new EndCommand());
        manager.registerCommand(new ItemList());
        manager.registerCommand(new SläschRechtsklick());
//        manager.registerCommand(new StartCommand());

        //This needs to work with command blocks
        Bukkit.getPluginCommand("start").setExecutor(new StartCommand());

        Team.clearTeams();
        Ranzen.clearRanzen();


        for (Player p : Bukkit.getOnlinePlayers()) {
            Team.resetPlayer(p);
        }

        for (Entity entity : WORLD.getEntities()) {
            if (entity instanceof Villager){
                entity.remove();
            }
        }

        getLogger().info("SchoolWars has been enabled!");

    }

    /**
     * Called when the plugin is disabled.
     * <p>
     * Cleans up by ending any active game and removing all spawned villager entities.
     * </p>
     */
    @Override
    public void onDisable() {
        EndGame.end();
        for (Entity entity : WORLD.getEntities()) {
            if (entity instanceof Villager){
                entity.remove();
            }
        }
    }

    /**
     * Gets the singleton instance of the plugin.
     *
     * @return the SchoolWars plugin instance
     */
    public static SchoolWars getPlugin(){
        return plugin;
    }

    /**
     * Sets the current player count.
     *
     * @param playerCount the new player count
     */
    public static void setPlayerCount(int playerCount) {
        SchoolWars.playerCount = playerCount;
    }

    /**
     * Gets the current player count.
     *
     * @return the number of players currently tracked
     */
    public static int getPlayerCount() {
        return playerCount;
    }
}
