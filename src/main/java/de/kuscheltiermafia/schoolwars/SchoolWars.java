package de.kuscheltiermafia.schoolwars;

//import de.kuscheltiermafia.schoolwars.commands.ClearTeams;
import de.kuscheltiermafia.schoolwars.commands.ClearTeams;
import de.kuscheltiermafia.schoolwars.commands.StartGame;
import de.kuscheltiermafia.schoolwars.commands.adminItems;
import de.kuscheltiermafia.schoolwars.events.JoinEvent;
import de.kuscheltiermafia.schoolwars.events.interactionCancel;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import de.kuscheltiermafia.schoolwars.items.Items;
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

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new interactionCancel(), this);

        getCommand("start").setExecutor(new StartGame());
        getCommand("clearTeams").setExecutor(new ClearTeams());
        getCommand("itemlist").setExecutor(new adminItems());
        getCommand("teamlist").setExecutor(new de.kuscheltiermafia.schoolwars.commands.TeamList());

        Teams.clearTeams();
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
