package de.kuscheltiermafia.schoolwars.commands;

import de.kuscheltiermafia.schoolwars.mechanics.EndGame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EndCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        EndGame.end();
        return true;
    }
}
