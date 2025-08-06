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


public final class SchoolWars extends JavaPlugin {

    public static SchoolWars plugin;

    private static int playerCount = 0;

    public static boolean gameStarted;

    public static final World WORLD = Bukkit.getWorld("schoolwars");


    @Override
    public void onEnable() {

        SunriseLib.setPlugin(this);

        gameStarted = false;

        plugin = this;

        for (Player p : Bukkit.getOnlinePlayers()) {
            try {
                playerMirror.put(p.getName(), new PlayerMirror(p.getName()));
            }catch (Exception ignored){}
            playerCount++;
        }

        Items.initItems();
        SchulbuchLevels.initShelfLocations();
        SchulbuchLevels.resetBookshelf();
        Ranzen.generateRanzenCounter();
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
        pluginManager.registerEvents(new BossWaves(), this);
        pluginManager.registerEvents(new Ranzen(), this);
        pluginManager.registerEvents(new DisableProfessions(), this);
        pluginManager.registerEvents(new FachraumSchluessel(), this);
        pluginManager.registerEvents(new VornbergerEvents(), this);
        pluginManager.registerEvents(new Fluor(), this);
        pluginManager.registerEvents(new Zentrifuge(), this);
        pluginManager.registerEvents(new BaarsKaffee(), this);
        pluginManager.registerEvents(new Vapes(), this);

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new Debug());
        manager.registerCommand(new EndCommand());
        manager.registerCommand(new ItemList());
        manager.registerCommand(new SläschRechtsklick());
        manager.registerCommand(new StartCommand());


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

    @Override
    public void onDisable() {
        EndGame.end();
        for (Entity entity : WORLD.getEntities()) {
            if (entity instanceof Villager){
                entity.remove();
            }
        }
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
