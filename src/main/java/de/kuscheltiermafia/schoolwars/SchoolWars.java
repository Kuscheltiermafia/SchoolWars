package de.kuscheltiermafia.schoolwars;

import de.kuscheltiermafia.schoolwars.commands.ClearTeams;
import de.kuscheltiermafia.schoolwars.commands.StartGame;
import de.kuscheltiermafia.schoolwars.commands.TeamList;
import de.kuscheltiermafia.schoolwars.commands.adminItems;
import de.kuscheltiermafia.schoolwars.events.*;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import de.kuscheltiermafia.schoolwars.items.GenerateItems;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.Ranzen;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;


public final class SchoolWars extends JavaPlugin {

    public static SchoolWars plugin;

    private static int playerCount = 0;
    static Scoreboard board;

    public static boolean gameStarted;


    @Override
    public void onEnable() {

        gameStarted = false;

        plugin = this;
        board = Bukkit.getScoreboardManager().getNewScoreboard();

        Items.initItems();
        GenerateItems.generateItemLocations();

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new interactionCancel(), this);
        pluginManager.registerEvents(new DeathEvent(), this);
        pluginManager.registerEvents(new RevivePlayer(), this);

        getCommand("start").setExecutor(new StartGame());
        getCommand("clearTeams").setExecutor(new ClearTeams());
        getCommand("itemlist").setExecutor(new adminItems());
        getCommand("teamlist").setExecutor(new TeamList());

        Teams.clearTeams();
        Ranzen.clearRanzen();

        for (Player p :Bukkit.getOnlinePlayers()) {
            Teams.resetPlayer(p);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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

    static public Scoreboard getBoard() {
        return board;
    }
}
