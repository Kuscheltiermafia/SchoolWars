package de.kuscheltiermafia.schoolwars.commands;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearTeams implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Teams.clearTeams();

        for (Player p : Bukkit.getOnlinePlayers()) {
            Teams.resetPlayer(p);
        }

        SchoolWars.gameStarted = false;

        return false;
    }
}
