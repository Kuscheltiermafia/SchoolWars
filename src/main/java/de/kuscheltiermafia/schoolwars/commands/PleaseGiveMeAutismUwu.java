package de.kuscheltiermafia.schoolwars.commands;

import de.kuscheltiermafia.schoolwars.events.Minasisierung;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PleaseGiveMeAutismUwu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player) {

            Player p = (Player) sender;
            Minasisierung.onMinasisierung(p);
        }
        return false;
    }
}
