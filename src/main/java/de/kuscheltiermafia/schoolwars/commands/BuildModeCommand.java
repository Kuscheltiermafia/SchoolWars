package de.kuscheltiermafia.schoolwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.entity.Player;

@CommandAlias("buildmode")
@CommandPermission("schoolwars.buildmode")
@Description("Schaltet den Build Mode ein oder aus.")
public class BuildModeCommand extends BaseCommand {

    @Default
    public static void toggleGamemode(Player player) {
        if (player.getGameMode() == org.bukkit.GameMode.CREATIVE) {
            player.setGameMode(org.bukkit.GameMode.SURVIVAL);
            player.sendMessage("§aBuild Mode deaktiviert. Du bist nun im Spielmodus.");
        } else {
            player.setGameMode(org.bukkit.GameMode.CREATIVE);
            player.sendMessage("§aBuild Mode aktiviert. Du bist nun im Kreativmodus.");
        }
    }

}
