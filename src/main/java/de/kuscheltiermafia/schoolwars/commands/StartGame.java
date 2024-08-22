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
import de.kuscheltiermafia.schoolwars.events.RevivePlayer;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import de.kuscheltiermafia.schoolwars.items.GenerateItems;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.Ranzen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import sun.security.krb5.JavaxSecurityAuthKerberosAccess;

import javax.management.openmbean.OpenMBeanInfoSupport;

import static de.kuscheltiermafia.schoolwars.events.RevivePlayer.deadPlayers;

public class StartGame implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        SchoolWars.gameStarted = false;

//reset Map
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
        } catch (Exception ignored) {
        }

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
        GenerateItems.summonItems();

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

//Teleport Players to their respective spawnpoints
        for (String playerName : Teams.sprachler) {

            Player p = Bukkit.getPlayer(playerName);

            Teams.configurePlayer(playerName, "sprachler");
            p.teleport(new Location(p.getWorld(), -20.5, 89, 146.5, -90, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1, false, false, false));

        }
        for (String playerName : Teams.naturwissenschaftler) {

            Player p = Bukkit.getPlayer(playerName);

            Teams.configurePlayer(playerName, "naturwissenschaftler");
            p.teleport(new Location(p.getWorld(), 5, 81, 192, 90, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1, false, false, false));
        }
        for (String playerName : Teams.sportler) {

            Player p = Bukkit.getPlayer(playerName);

            Teams.configurePlayer(playerName, "sportler");
            p.teleport(new Location(p.getWorld(), 70.5, 81, 167.5, 90, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1, false, false, false));
        }

        return false;
    }
}
