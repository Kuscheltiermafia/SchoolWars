package de.kuscheltiermafia.schoolwars;

import de.kuscheltiermafia.schoolwars.events.JoinEvent;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SchoolWars extends JavaPlugin {

    private static int playerCount = 0;

    public static SchoolWars plugin;

    @Override
    public void onEnable() {

        plugin = this;

        Teams.prep();

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), this);

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
}
