package de.kuscheltiermafia.schoolwars;

import de.kuscheltiermafia.schoolwars.events.JoinEvent;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import jdk.tools.jmod.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SchoolWars extends JavaPlugin {

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

}
