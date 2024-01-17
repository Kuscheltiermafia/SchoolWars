package de.kuscheltiermafia.schoolwars;

import de.kuscheltiermafia.schoolwars.commands.ClearTeams;
import de.kuscheltiermafia.schoolwars.commands.StartGame;
import de.kuscheltiermafia.schoolwars.events.JoinEvent;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

public final class SchoolWars extends JavaPlugin {

    private static int playerCount = 0;
    static Scoreboard board;

    public static SchoolWars plugin;

    @Override
    public void onEnable() {

        plugin = this;
        board = Bukkit.getScoreboardManager().getNewScoreboard();

        Teams.prep();

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), this);

        getCommand("start").setExecutor(new StartGame());
        getCommand("clearTeams").setExecutor(new ClearTeams());

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
