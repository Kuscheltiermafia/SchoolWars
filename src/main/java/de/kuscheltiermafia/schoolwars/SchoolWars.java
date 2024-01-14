package de.kuscheltiermafia.schoolwars;

import de.kuscheltiermafia.schoolwars.events.JoinEvent;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SchoolWars extends JavaPlugin {

    @Override
    public void onEnable() {

        Teams.prep();

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
