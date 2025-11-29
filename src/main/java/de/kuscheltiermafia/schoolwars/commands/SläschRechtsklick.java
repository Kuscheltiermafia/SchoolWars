package de.kuscheltiermafia.schoolwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Easter egg command that gives the player a specific player head.
 * <p>
 * This command gives the sender a player head with the skin of "TaktischerHase".
 * The command name is intentionally unusual (German for "slash right-click").
 * </p>
 */
@CommandAlias("rechtsklick")
public class SläschRechtsklick extends BaseCommand {

    /**
     * Gives the player a specific player head when executed.
     *
     * @param sender the command sender (must be a player)
     */
    @Default
    public void onCommand(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.performCommand("give @s player_head[profile={name:\"TaktischerHase\"}] 1");
        }
    }
}
