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
import de.kuscheltiermafia.schoolwars.win_conditions.events_atombombe.AtombombeEvents;
import de.kuscheltiermafia.schoolwars.win_conditions.BossWaves;
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
 * Main plugin class for SchoolWars - A school-themed survival game.
 * 
 * SchoolWars is a Minecraft plugin that transforms the game into a school simulation
 * where players navigate various challenges, interact with teacher NPCs, complete quests,
 * and engage with unique mechanics like item crafting, dialogue systems, and team-based gameplay.
 * 
 * Key features include:
 * - Teacher NPC interactions with dialogue systems
 * - Complex item crafting and cooking mechanics  
 * - Player reputation system with teachers
 * - Team-based gameplay with win conditions
 * - Special effects like player scaling and particle systems
 * - Debug commands for administrators
 * 
 * @author Morgon and CrAzyA22
 */
public final class SchoolWars extends JavaPlugin {

    /** Static reference to the plugin instance for access across classes */
    public static SchoolWars plugin;

    /** Current number of players online, used for game state management */
    private static int playerCount = 0;

    /** Flag indicating whether a game session is currently active */
    public static boolean gameStarted;

    /** Reference to the main game world where SchoolWars takes place */
    public static final World WORLD = Bukkit.getWorld("schoolwars");

    /**
     * Plugin initialization method called when the plugin is enabled.
     * Sets up all game systems, registers event handlers, initializes data structures,
     * and prepares the plugin for player interactions.
     */
    @Override
    public void onEnable() {

        // Initialize SunriseLib dependency
        SunriseLib.setPlugin(this);

        // Reset game state
        gameStarted = false;
        plugin = this;

        // Initialize probability configuration system
        ProbabilityConfig.initialize(this);

        // Initialize player mirrors for all currently online players
        for (Player p : Bukkit.getOnlinePlayers()) {
            try {
                playerMirror.put(p.getName(), new PlayerMirror(p.getName()));
            }catch (Exception ignored){}
            playerCount++;
        }

        // Initialize game systems
        Items.initItems();                          // Initialize custom items
        SchulbuchLevels.initShelfLocations();      // Set up bookshelf locations
        SchulbuchLevels.resetBookshelf();          // Reset bookshelf states
        Ranzen.generateRanzenCounter();            // Initialize backpack system
        //LehrerQuests.initLehrerAlgorithm();      // Disabled - teacher quest algorithm
        LehrerQuests.initLehrerQuests();           // Initialize teacher quests
        DialogueHandler.initDialogues();           // Set up NPC dialogue content

        // Register all event listeners
        // Register all event listeners
        PluginManager pluginManager = Bukkit.getPluginManager();

        // Core player events
        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new LeaveEvent(), this);
        pluginManager.registerEvents(new InteractionEvent(), this);
        pluginManager.registerEvents(new PlayerDeath(), this);
        pluginManager.registerEvents(new RevivePlayerEvent(), this);
        
        // Item and mechanic events
        pluginManager.registerEvents(new HandleKuehlpack(), this);
        pluginManager.registerEvents(new PickupDrops(), this);
        pluginManager.registerEvents(new KarlElixier(), this);
        pluginManager.registerEvents(new Rolator(), this);
        pluginManager.registerEvents(new Minasisierung(), this);
        pluginManager.registerEvents(new VornbergerEvents(), this);
        pluginManager.registerEvents(new FischersSpielzeug(), this);
        
        // School-themed events
        pluginManager.registerEvents(new SchulbuchLevels(), this);
        pluginManager.registerEvents(new LehrerQuests(), this);
        pluginManager.registerEvents(new SekretariatStundenplan(), this);
        pluginManager.registerEvents(new FachraumSchluessel(), this);
        pluginManager.registerEvents(new Generalschluessel(), this);
        
        // Game mechanics
        pluginManager.registerEvents(new PlayerStun(), this);
        pluginManager.registerEvents(new DialogueHandler(), this);
        pluginManager.registerEvents(new Ranzen(), this);
        pluginManager.registerEvents(new DisableProfessions(), this);
        
        // Win condition events
        pluginManager.registerEvents(new AtombombeEvents(), this);
        pluginManager.registerEvents(new BossWaves(), this);
        pluginManager.registerEvents(new Fluor(), this);
        pluginManager.registerEvents(new Zentrifuge(), this);
        pluginManager.registerEvents(new BaarsKaffee(), this);
        pluginManager.registerEvents(new Vapes(), this);

        // Register command handlers
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new Debug());           // Debug utilities
        manager.registerCommand(new EndCommand());      // End game command
        manager.registerCommand(new ItemList());        // Item list GUI
        manager.registerCommand(new SläschRechtsklick()); // Special right-click handler
        manager.registerCommand(new StartCommand());    // Start game command

        // Reset game state for fresh start
        Team.clearTeams();       // Clear all existing teams
        Ranzen.clearRanzen();    // Clear backpack data

        // Reset all currently online players
        for (Player p : Bukkit.getOnlinePlayers()) {
            Team.resetPlayer(p);
        }

        // Clean up any existing villager NPCs from previous sessions
        for (Entity entity : WORLD.getEntities()) {
            if (entity instanceof Villager){
                entity.remove();
            }
        }

        getLogger().info("SchoolWars has been enabled!");

    }

    /**
     * Plugin cleanup method called when the plugin is disabled.
     * Ensures proper game state cleanup and removes any remaining NPCs.
     */
    @Override
    public void onDisable() {
        // End any active game session
        EndGame.end();
        
        // Clean up any remaining villager NPCs
        for (Entity entity : WORLD.getEntities()) {
            if (entity instanceof Villager){
                entity.remove();
            }
        }
    }

    /**
     * Gets the static plugin instance.
     * 
     * @return The SchoolWars plugin instance
     */
    public static SchoolWars getPlugin(){
        return plugin;
    }

    /**
     * Sets the current player count.
     * Used to track how many players are currently online for game state management.
     * 
     * @param playerCount The new player count
     */
    public static void setPlayerCount(int playerCount) {
        SchoolWars.playerCount = playerCount;
    }

    /**
     * Gets the current player count.
     * 
     * @return The number of players currently online
     */
    public static int getPlayerCount() {
        return playerCount;
    }
}
