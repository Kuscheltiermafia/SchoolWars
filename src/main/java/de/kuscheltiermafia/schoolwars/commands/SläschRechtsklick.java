package de.kuscheltiermafia.schoolwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("rechtsklick")
public class SläschRechtsklick extends BaseCommand {

    @Default
    public void onCommand(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.performCommand("give @s player_head[profile={name:\"TaktischerHase\"}] 1");
        }
    }
}
