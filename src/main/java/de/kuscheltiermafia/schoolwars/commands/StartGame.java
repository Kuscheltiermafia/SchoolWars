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
import de.kuscheltiermafia.schoolwars.teams.Team;
import de.kuscheltiermafia.schoolwars.teams.Teams;
import de.kuscheltiermafia.schoolwars.items.GenerateItems;
import de.kuscheltiermafia.schoolwars.lehrer.Stundenplan;
import de.kuscheltiermafia.schoolwars.mechanics.RevivePlayer;
import de.kuscheltiermafia.schoolwars.player_mirror.PlayerMirror;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import static de.kuscheltiermafia.schoolwars.SchoolWars.*;

public class StartGame implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (Debug.startWorks) {
            SchoolWars.gameStarted = false;

//reset
            playerMirror.clear();

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

//prepare game

            Stundenplan.updateStundenplan(true);
            Stundenplan.updateStundenplan(false);

//Set Playernames and ready them for battle
            Teams.clearTeams();
            for (Player p : Bukkit.getOnlinePlayers()) {
                Teams.resetPlayer(p);
                p.getInventory().clear();
                p.setGameMode(GameMode.SURVIVAL);
                p.setHealth(20);
                p.getActivePotionEffects().clear();
            }

            GenerateItems.summonItems();

//prepare player Mirrors
            playerMirror.clear();
            for (Player p : Bukkit.getOnlinePlayers()) {
                playerMirror.put(p.getName(), new PlayerMirror(p.getName()));
            }

//prepare Teams
            Teams.joinTeams();
            Team.NWS.prepare();
            Team.SPORTLER.prepare();
            Team.SPRACHLER.prepare();

//revive Player
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!playerMirror.get(p.getName()).isAlive()) {
                    RevivePlayer.revivePlayer(p, p);
                }
            }

            SchoolWars.gameStarted = true;
        }
        return false;
    }
}
