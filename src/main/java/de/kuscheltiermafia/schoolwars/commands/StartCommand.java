/*
 * ███╗   ███╗ █████╗ ██████╗ ███████╗    ██████╗ ██╗   ██╗
 * ████╗ ████║██╔══██╗██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝
 * ██╔████╔██║███████║██║  ██║█████╗      ██████╔╝ ╚████╔╝
 * ██║╚██╔╝██║██╔══██║██║  ██║██╔══╝      ██╔══██╗  ╚██╔╝
 * ██║ ╚═╝ ██║██║  ██║██████╔╝███████╗    ██████╔╝   ██║
 * ╚═╝     ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝    ╚═════╝    ╚═╝
 *
 * ██╗  ██╗██╗   ██╗███████╗ ██████╗██╗  ██╗███████╗██╗  ████████╗██╗███████╗██████╗ ███╗   ███╗ █████╗ ███████╗██╗ █████╗
 * ██║ ██╔╝██║   ██║██╔════╝██╔════╝██║  ██║██╔════╝██║  ╚══██╔══╝██║██╔════╝██╔══██╗████╗ ████║██╔══██╗██╔════╝██║██╔══██╗
 * █████╔╝ ██║   ██║███████╗██║     ███████║█████╗  ██║     ██║   ██║█████╗  ██████╔╝██╔████╔██║███████║█████╗  ██║███████║
 * ██╔═██╗ ██║   ██║╚════██║██║     ██╔══██║██╔══╝  ██║     ██║   ██║██╔══╝  ██╔══██╗██║╚██╔╝██║██╔══██║██╔══╝  ██║██╔══██║
 * ██║  ██╗╚██████╔╝███████║╚██████╗██║  ██║███████╗███████╗██║   ██║███████╗██║  ██║██║ ╚═╝ ██║██║  ██║██║     ██║██║  ██║
 * ╚═╝  ╚═╝ ╚═════╝ ╚══════╝ ╚═════╝╚═╝  ╚═╝╚══════╝╚══════╝╚═╝   ╚═╝╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝
 *
 * This is a plugin from Morgon and CrAzyA22 - Unless explicitly stated otherwise you are not permitted to use any of the given code!
 *
 */

package de.kuscheltiermafia.schoolwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import de.kuscheltiermafia.schoolwars.mechanics.StartGame;
import io.github.realMorgon.sunriseLib.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static de.kuscheltiermafia.schoolwars.SchoolWars.WORLD;

//@CommandAlias("start")
//public class StartCommand extends BaseCommand {
//
//    @Default
//    public void onStartCommand(CommandSender sender, @Nullable Player target) {
//
//        if (!(sender instanceof Player) && target == null) {
//            sender.sendMessage("Non Players must specify a player!");
//            return;
//        }
//        if (target == null) target = (Player) sender;
//
//        StartGame.openGUI(target, false);
//    }
//}


//Has to work with command blocks
public class StartCommand implements CommandExecutor{

    Location commandBlockLocation = new Location(WORLD, -32.0, 81.0, 181.0);
    Location playerLocation = new Location(WORLD, -32.0, 80.0, 178.0);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player target = null;
        if (sender instanceof Player) {
            target = (Player) sender;
        }else if (sender instanceof BlockCommandSender) {
            if (((BlockCommandSender) sender).getBlock().getLocation().equals(commandBlockLocation)) {
                target = playerLocation.getNearbyPlayers(3).stream().findFirst().orElse(null);
            }else{
                Message.sendToAllPlayers(ChatColor.YELLOW + "[SchoolWars] Please update the command block location for the start command! Contact the plugin authors to do this");
                return false;
            }
        }else{
            sender.sendMessage("Non Players and non Command Blocks must specify a player!");
            return false;
        }

        StartGame.openGUI(target, false);

        return true;
    }
}
