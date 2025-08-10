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
import co.aikar.commands.annotation.*;
import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.events.Minasisierung;
import de.kuscheltiermafia.schoolwars.events.SchulbuchLevels;
import de.kuscheltiermafia.schoolwars.lehrer.*;
import de.kuscheltiermafia.schoolwars.mechanics.Intro;
import de.kuscheltiermafia.schoolwars.mechanics.PlayerStun;
import de.kuscheltiermafia.schoolwars.mechanics.RevivePlayer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;

import static de.kuscheltiermafia.schoolwars.lehrer.Lehrer.lehrerEntityList;
import static de.kuscheltiermafia.schoolwars.lehrer.Lehrer.lehrerList;
import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

@CommandAlias("debug")
public class Debug extends BaseCommand {

    public static ArrayList<Player> joinMsg = new ArrayList<>();
    public static Boolean startWorks = true;
    public static String debugPrefix = ChatColor.DARK_RED + "[!] " + ChatColor.GRAY;

    @Subcommand("autism")
    @CommandPermission("schoolwars.debug.autism")
    @CommandCompletion("autism")
    public static void onAutismCommand(CommandSender sender){
        Minasisierung.onMinasisierung((Player) sender);
        sender.sendMessage(debugPrefix + "You now have autism!");
    }

    @Subcommand("revive")
    @CommandPermission("schoolwars.debug.revive")
    public static void onReviveCommand(CommandSender sender, @Name("player") @Optional String playerName){
        if (playerName == null || playerName.isEmpty()) {
            Player target = (Player) sender;
            RevivePlayer.revivePlayer((Player) sender, target);
        }else if (playerName != null && !playerName.isEmpty()) {
            Player target = Bukkit.getPlayer(playerName);
            RevivePlayer.revivePlayer((Player) sender, target);
        }else {
            sender.sendMessage(debugPrefix + "Please use /debug revive [<player>]");
        }
    }

    @Subcommand("getloc")
    @CommandCompletion("getloc")
    public static void onGetLocCommand(CommandSender sender){
        Player p = (Player) sender;

        TextComponent location = new TextComponent(debugPrefix + "Click to copy block location! " + p.getTargetBlockExact(4).getLocation().getX() + ", " + p.getTargetBlockExact(4).getLocation().getY() + ", " + p.getTargetBlockExact(4).getLocation().getZ());
        location.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "" + p.getTargetBlockExact(4).getLocation().getX() + ", " + p.getTargetBlockExact(4).getLocation().getY() + ", " + p.getTargetBlockExact(4).getLocation().getZ()));
        p.spigot().sendMessage(location);
    }

    @Subcommand("rep")
    @CommandPermission("schoolwars.debug.rep")
    public static void onRepCommand(CommandSender sender, @Name("lehrer") String lehrerName, @Name("player") @Optional String playerName) {

        Player p = (Player) sender;

        if (playerName == null || playerName.isEmpty()) {
            try {
                p.sendMessage(debugPrefix + "Reputation: " + ChatColor.RED + playerMirror.get(p.getName()).getReputation(Lehrer.getLehrerByName(lehrerName)));
            } catch (Exception faultyInput) {
                p.sendMessage(debugPrefix + "Lehrer not found!");
            }
        } else if (playerName != null && !playerName.isEmpty()) {
            try {
                Player target = Bukkit.getPlayer(playerName);
                p.sendMessage(debugPrefix + "Reputation: " + ChatColor.RED + playerMirror.get(target.getName()).getReputation(Lehrer.getLehrerByName(lehrerName)));
            } catch (Exception faultyInput) {
                p.sendMessage(debugPrefix + "Lehrer not found!");
            }
        }
    }

    @Subcommand("lehrer")
    @CommandPermission("schoolwars.debug.lehrer")
    public class onLehrerCommand extends BaseCommand {

        @Subcommand("spawn")
        public static void onLehrerSpawnCommand(CommandSender sender, @Name("lehrer") String lehrerName) {
            Player p = (Player) sender;
            for(Lehrer lehrer : Lehrer.values()) {
                Villager testLehrer = Lehrer.summonLehrer(p.getLocation(), lehrer);
                if(testLehrer.getCustomName().contains(lehrerName)) {
                    Bukkit.broadcastMessage("Found lehrer");
                    p.sendMessage(debugPrefix + "Spawned " + lehrer.name() + "!");
                }else{
                    testLehrer.remove();
                }
            }
        }

        @Subcommand("kill")
        public static void onLehrerKillCommand(CommandSender sender, @Name("lehrer") String lehrerName) {
            Player p = (Player) sender;
            for (Villager lehrer : lehrerEntityList) {
                try {
                    if (lehrer.getCustomName().contains(lehrerName)) {
                        lehrer.remove();
                    } else if (lehrerName.equals("all")) {
                        lehrer.remove();
                        lehrerEntityList.clear();
                        lehrerList.clear();
                    }
                } catch (Exception ignored) {
                }
            }
        }

        @Subcommand("position")
        public static void onLehrerPositionCommand() {
            Lehrer.updateLehrerPosition(true);
        }
    }

    @Subcommand("toggle")
    @CommandPermission("schoolwars.debug.toggle")
    public class onToggleCommand extends BaseCommand {

        @Subcommand("join")
        public static void onToggleJoinCommand(CommandSender sender) {
            Player p = (Player) sender;
            if (joinMsg.contains(p)) {
                p.sendMessage(debugPrefix + "You will no longer recieve join messages!");
                joinMsg.remove(p);
            } else {
                p.sendMessage(debugPrefix + "You will now recieve join messages!");
                joinMsg.add(p);
            }
        }

        @Subcommand("start")
        public static void onToggleStartCommand(CommandSender sender) {
            Player p = (Player) sender;
            if (startWorks) {
                p.sendMessage(debugPrefix + "/start will no longer work!");
                startWorks = false;
            } else {
                p.sendMessage(debugPrefix + "/start will now work again!");
                startWorks = true;
            }
        }
    }

    @Subcommand("stun")
    @CommandPermission("schoolwars.debug.stun")
    public static void onStunCommand(CommandSender sender, @Name("player") String playerName, @Name("duration") String duration, @Name("in-ground") String inGround) {
        Player p = (Player) sender;
        PlayerStun.stunPlayer(Bukkit.getPlayer(playerName), Integer.parseInt(duration), Boolean.getBoolean(inGround));
        p.sendMessage(debugPrefix + "You stunned " + playerName + " for " + duration + " seconds.");
        p.sendMessage(debugPrefix + "inGround: " + inGround);
    }

    @Subcommand("verweise")
    @CommandPermission("schoolwars.debug.verweise")
    public class  onVerweiseCommand extends BaseCommand {

        @Subcommand("add")
        public static void onVerweiseAddCommand(CommandSender sender, @Name("amount") String amount, @Name("player") @Optional String playerName) {
            Player p = (Player) sender;
            if (playerName == null || playerName.isEmpty()) {
                playerMirror.get(p.getName()).addVerweis(Integer.parseInt(amount));
                p.sendMessage(debugPrefix + "Added Verweis: " + amount);
            } else if (playerName != null && !playerName.isEmpty()) {
                playerMirror.get(playerName).addVerweis(Integer.parseInt(amount));
                p.sendMessage(debugPrefix + "Added Verweis: " + amount + " to " + playerName);
            }else {
                p.sendMessage(debugPrefix + "Please use /debug verweise <get>");
                p.sendMessage(debugPrefix + "or /debug verweise <get> <player>");
                p.sendMessage(debugPrefix + "or /debug verweise <add> <amount>");
                p.sendMessage(debugPrefix + "or /debug verweise <add> <amount> <player>!");
                p.sendMessage(debugPrefix + "You can use negative numbers to remove Verweise");
            }
        }

        @Subcommand("get")
        public static void onVerweiseGetCommand(CommandSender sender, @Name("player") @Optional String playerName) {
            Player p = (Player) sender;
            if (playerName == null || playerName.isEmpty()) {
                p.sendMessage(debugPrefix + "Your current Verweise: " + playerMirror.get(p.getName()).getVerweise());
            } else if (playerName != null && !playerName.isEmpty()) {
                p.sendMessage(debugPrefix + playerName + "'s current Verweise: " + playerMirror.get(playerName).getVerweise());
            }else {
                p.sendMessage(debugPrefix + "Please use /debug verweise <get>");
                p.sendMessage(debugPrefix + "or /debug verweise <get> <player>");
                p.sendMessage(debugPrefix + "or /debug verweise <add> <amount>");
                p.sendMessage(debugPrefix + "or /debug verweise <add> <player> <amount>!");
                p.sendMessage(debugPrefix + "You can use negative numbers to remove Verweise");
            }
        }
    }

    @Subcommand("stundenplan")
    @CommandPermission("schoolwars.debug.stundenplan")
    public class onStundenplanCommand extends BaseCommand {

        @Subcommand("get")
        public static void onStundenplanGetCommand(CommandSender sender) {
            Player p = (Player) sender;
            if (SchoolWars.gameStarted) {
                p.sendMessage(debugPrefix + "Current Stundenplan: ");
                for (Lehrer lehrer : Stundenplan.studenplan.keySet()) {
                    p.sendMessage(debugPrefix + lehrer.name() + " -> " + Stundenplan.studenplan.get(lehrer).name());
                }
            }else {
                p.sendMessage(debugPrefix + "Game has not started yet!");
            }
        }

        @Subcommand("update")
        public static void onStundenplanUpdateCommand(CommandSender sender) {
            Player p = (Player) sender;
            if (SchoolWars.gameStarted) {
                Stundenplan.updateStundenplan(true);
                p.sendMessage(debugPrefix + "Sucessfully updated Stundenplan!");
            }else  {
                p.sendMessage(debugPrefix + "Game has not started yet!");
            }
        }

        @Subcommand("view")
        public static void onStundenplanViewCommand(CommandSender sender) {
            Player p = (Player) sender;
            if (SchoolWars.gameStarted) {
                p.openInventory(SekretariatStundenplan.createStundenplan(0));
                p.sendMessage(debugPrefix + "Opened Stundenplan!");
            }else  {
                p.sendMessage(debugPrefix + "Game has not started yet!");
            }
        }
    }

    @Subcommand("intro")
    @CommandPermission("schoolwars.debug.intro")
    public static void onIntroCommand(CommandSender sender) {
        Intro.introScene((Player) sender);
        sender.sendMessage(debugPrefix + "Started intro sequence!");
    }

    @Subcommand("fly")
    @CommandPermission("schoolwars.debug.fly")
    public static void onFlyCommand(CommandSender sender, @Name("player") @Optional String player) {

        if (player == null || player.isEmpty()) {
            Player target = (Player) sender;
            target.setAllowFlight(!target.getAllowFlight());
        }else {
            Player target = Bukkit.getPlayer(player);
            target.setAllowFlight(!target.getAllowFlight());
        }
    }

    @Subcommand("schulbuch")
    @CommandPermission("schoolwars.debug.schulbuch")
    public static void onFlyCommand(CommandSender sender) {
            sender.sendMessage(debugPrefix + "Next book location: " + SchulbuchLevels.currentBookshelfLoc.getX() + ", " + SchulbuchLevels.currentBookshelfLoc.getY() + ", " + SchulbuchLevels.currentBookshelfLoc.getZ());
    }

}