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
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import de.kuscheltiermafia.schoolwars.items.GenerateItems;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.Ranzen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import static de.kuscheltiermafia.schoolwars.events.RevivePlayer.deadPlayers;


public final class SchoolWars extends JavaPlugin {



    public static SchoolWars plugin;

    private static int playerCount = 0;
    static Scoreboard board;

    public static boolean gameStarted;

    public static String nameSportler = ChatColor.DARK_RED + "sportler";
    public static String nameSprachler = ChatColor.GOLD + "sprachler";
    public static String nameNaturwissenschaftler = ChatColor.GREEN + "naturwissenschaftler";


    @Override
    public void onEnable() {

        gameStarted = false;

        plugin = this;
        board = Bukkit.getScoreboardManager().getNewScoreboard();

        Items.initItems();
        SchulbuchLevels.initShelfLocations();
        GenerateItems.generateItemLocations();
        Ranzen.generateRanzenCounter();

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
