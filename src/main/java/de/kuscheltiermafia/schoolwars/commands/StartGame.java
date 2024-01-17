package de.kuscheltiermafia.schoolwars.commands;

import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Teams.joinTeams();

        return false;
    }
}
