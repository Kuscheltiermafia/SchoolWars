package de.kuscheltiermafia.schoolwars.commands;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import de.kuscheltiermafia.schoolwars.items.GenerateItems;
import de.kuscheltiermafia.schoolwars.mechanics.Ranzen;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StartGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        SchoolWars.gameStarted = false;

        Teams.clearTeams();
        for (Player p : Bukkit.getOnlinePlayers()) {
            Teams.resetPlayer(p);
        }

        SchoolWars.gameStarted = true;

        Teams.joinTeams();
        GenerateItems.summonItems();

        for(String playerName : Teams.sprachler){

            Player p = Bukkit.getPlayer(playerName);

            Teams.configurePlayer(playerName, "sprachler");
            p.teleport(new Location(p.getWorld(), -20.5, 89, 146.5, -90, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1, 1, false, false, false));


        }
        for (String playerName : Teams.naturwissenschaftler){

            Player p = Bukkit.getPlayer(playerName);

            Teams.configurePlayer(playerName, "naturwissenschaftler");
            p.teleport(new Location(p.getWorld(), 5, 81, 192, 90, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1, 1, false, false, false));
        }
        for (String playerName : Teams.sportler){

            Player p = Bukkit.getPlayer(playerName);

            Teams.configurePlayer(playerName, "sportler");
            p.teleport(new Location(p.getWorld(), 70.5, 81, 167.5, 90, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1, 1, false, false, false));
        }

        return false;
    }
}
