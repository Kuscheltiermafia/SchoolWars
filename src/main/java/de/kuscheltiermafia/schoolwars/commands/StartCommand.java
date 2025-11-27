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

/**
 * Command to open the game start GUI.
 * <p>
 * This command can be executed by:
 * <ul>
 *   <li>Players - opens the start menu directly</li>
 *   <li>Command blocks - finds nearby players and opens the menu for them</li>
 * </ul>
 * </p>
 * <p>
 * Note: This uses CommandExecutor instead of ACF annotations because
 * it needs to work with command blocks.
 * </p>
 */
public class StartCommand implements CommandExecutor{

    /** Expected location of the start command block. */
    Location commandBlockLocation = new Location(WORLD, -32.0, 81.0, 181.0);
    
    /** Location to search for players near the command block. */
    Location playerLocation = new Location(WORLD, -32.0, 80.0, 178.0);

    /**
     * Handles the start command execution.
     *
     * @param sender the command sender (player or command block)
     * @param command the command that was executed
     * @param s the alias used
     * @param strings command arguments
     * @return true if the command was handled successfully
     */
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
