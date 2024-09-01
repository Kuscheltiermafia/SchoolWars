/**
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

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.gameprep.NWS;
import de.kuscheltiermafia.schoolwars.gameprep.Sportler;
import de.kuscheltiermafia.schoolwars.gameprep.Sprachler;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import de.kuscheltiermafia.schoolwars.items.GenerateItems;
import de.kuscheltiermafia.schoolwars.reputation.PlayerRepModel;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffectType;

import static de.kuscheltiermafia.schoolwars.SchoolWars.*;
import static de.kuscheltiermafia.schoolwars.events.RevivePlayer.deadPlayers;

public class StartGame implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        SchoolWars.gameStarted = false;

//reset
        playerReputation.clear();

        try {
            for (Item item : Bukkit.getWorld("schoolwars").getEntitiesByClass(Item.class)) {
                item.remove();
            }
            for (Interaction interaction : Bukkit.getWorld("schoolwars").getEntitiesByClass(Interaction.class)) {
                interaction.remove();
            }
            for (BlockDisplay blockdisplay : Bukkit.getWorld("schoolwars").getEntitiesByClass(BlockDisplay.class)) {
                blockdisplay.remove();
            }
        } catch (Exception ignored) {}

//Set Playernames and ready them for battle
        Teams.clearTeams();
        for (Player p : Bukkit.getOnlinePlayers()) {
            Teams.resetPlayer(p);
            p.getInventory().clear();
            p.setGameMode(GameMode.SURVIVAL);
            p.setHealth(20);
            p.getActivePotionEffects().clear();
        }

        SchoolWars.gameStarted = true;

        Teams.joinTeams();
        nws.prepare();
        sportler.prepare();
        sprachler.prepare();

        GenerateItems.summonItems();

//prepare reputation
        playerReputation.clear();
        for (Player p : Bukkit.getOnlinePlayers()) {
            playerReputation.put(p.getName(), new PlayerRepModel(p.getName()));
        }

//revive Player
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (deadPlayers.containsKey(p.getName())) {
                Bukkit.getEntity(deadPlayers.get(p.getName())).remove();
                p.teleport(p.getLocation().add(0, 1, 0));
                p.removePotionEffect(PotionEffectType.SLOWNESS);
                p.removePotionEffect(PotionEffectType.RESISTANCE);
                p.setHealth(20);

                deadPlayers.remove(p.getName());
            }
        }

        return false;
    }
}
