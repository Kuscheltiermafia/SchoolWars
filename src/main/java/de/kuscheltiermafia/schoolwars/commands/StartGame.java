package de.kuscheltiermafia.schoolwars.commands;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        SchoolWars.gameStarted = true;

        Teams.joinTeams();

        for(String playerName : Teams.sprachler){
            Teams.configurePlayer(playerName, "sprachler");
        }
        for (String playerName : Teams.naturwissenschaftler){
            Teams.configurePlayer(playerName, "naturwissenschaftler");
        }
        for (String playerName : Teams.sportler){
            Teams.configurePlayer(playerName, "sportler");
        }

        return false;
    }
}
