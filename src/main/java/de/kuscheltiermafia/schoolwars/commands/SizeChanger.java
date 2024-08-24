package de.kuscheltiermafia.schoolwars.commands;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static de.kuscheltiermafia.schoolwars.events.RevivePlayer.deadPlayers;

public class SizeChanger implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(args.length == 1 && sender instanceof Player && ((Player) sender).isOp()) {
            Player target = (Player) sender;

            target.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(Double.parseDouble(args[0]));
        }

        if(args.length == 2 && ((Player) sender).isOp()) {
            Player target = (Player) Bukkit.getPlayer(args[1]);

            target.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(Double.parseDouble(args[0]));
        }

        return false;
    }
}
