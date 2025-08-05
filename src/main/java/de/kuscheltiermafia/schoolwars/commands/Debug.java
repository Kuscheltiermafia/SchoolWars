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
    @CommandCompletion("revive <player>")
    @Syntax("revive [<player>]")
    public static void onReviveCommand(CommandSender sender, String playerName){
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
    @CommandCompletion("rep <Lehrer> [<player>]")
    @Syntax("rep <Lehrer> [<player>]")
    public static void onRepCommand(CommandSender sender, String lehrerName, String playerName) {

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
        }else {
            p.sendMessage(debugPrefix + "Please use: /debug rep <Lehrer> (Case sensitive!)");
            p.sendMessage(debugPrefix + "Or use /debug rep <Player> <Lehrer> (Case sensitive!)");
        }
    }

    @Subcommand("lehrer")
    @CommandPermission("schoolwars.debug.lehrer")
    public class onLehrerCommand extends BaseCommand {

        @Subcommand("spawn")
        @CommandCompletion("lehrer <spawn|kill|position> <lehrerName>")
        public static void onLehrerSpawnCommand(CommandSender sender, String lehrerName) {
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
        @CommandCompletion("lehrer <spawn|kill|position> <lehrerName>")
        public static void onLehrerKillCommand(CommandSender sender, String lehrerName) {
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
        @CommandCompletion("lehrer <spawn|kill|position> <lehrerName>")
        public static void onLehrerPositionCommand() {
            Lehrer.updateLehrerPosition(true);
        }
    }

    @Subcommand("toggle")
    @CommandPermission("schoolwars.debug.toggle")
    public class onToggleCommand extends BaseCommand {

        @Subcommand("join")
        @CommandCompletion("toggle join|start")
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
        @CommandCompletion("toggle join|start")
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
    @CommandCompletion("stun <player> <duration in seconds> <in Ground>")
    @Syntax("stun <player> <duration in seconds> <in Ground>")
    public static void onStunCommand(CommandSender sender, String playerName, String duration, String inGround) {
        Player p = (Player) sender;
        PlayerStun.stunPlayer(Bukkit.getPlayer(playerName), Integer.parseInt(duration), Boolean.getBoolean(inGround));
        p.sendMessage(debugPrefix + "You stunned " + playerName + " for " + duration + " seconds.");
        p.sendMessage(debugPrefix + "inGround: " + inGround);
    }

    @Subcommand("verweise")
    @CommandPermission("schoolwars.debug.verweise")
    public class  onVerweiseCommand extends BaseCommand {

        @Subcommand("add")
        @CommandCompletion("verweise add <amount> [<player>]")
        public static void onVerweiseAddCommand(CommandSender sender, String amount, String playerName) {
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
        @CommandCompletion("verweise get [<player>]")
        public static void onVerweiseGetCommand(CommandSender sender, String playerName) {
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
        @CommandCompletion("stundenplan get|update")
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
        @CommandCompletion("stundenplan get|update")
        public static void onStundenplanUpdateCommand(CommandSender sender) {
            Player p = (Player) sender;
            if (SchoolWars.gameStarted) {
                Stundenplan.updateStundenplan(true);
                p.sendMessage(debugPrefix + "Sucessfully updated Stundenplan!");
            }else  {
                p.sendMessage(debugPrefix + "Game has not started yet!");
            }
        }
    }

    @Subcommand("intro")
    @CommandPermission("schoolwars.debug.intro")
    @CommandCompletion("intro")
    public static void onIntroCommand(CommandSender sender) {
        Intro.introScene((Player) sender);
        sender.sendMessage(debugPrefix + "Started intro sequence!");
    }

    @Subcommand("fly")
    @CommandCompletion("fly [<player>]")
    @CommandPermission("schoolwars.debug.fly")
    public static void onFlyCommand(CommandSender sender, String player) {

        Player target = Bukkit.getPlayer(player);
        target.setAllowFlight(!target.getAllowFlight());
    }


    @Subcommand("schulbuch")
    @CommandCompletion("schulbuch")
    @CommandPermission("schoolwars.debug.schulbuch")
    public static void onFlyCommand(CommandSender sender) {
            sender.sendMessage(debugPrefix + "Next book location: " + SchulbuchLevels.currentBookshelfLoc.getX() + ", " + SchulbuchLevels.currentBookshelfLoc.getY() + ", " + SchulbuchLevels.currentBookshelfLoc.getZ());
    }


//    @Override
//    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
//
//        if (sender instanceof Player && sender.isOp()) {
//
//            Player p = (Player) sender;
//
//            switch (args[0]) {
//                case "autism":
//                    Minasisierung.onMinasisierung(p);
//                    p.sendMessage(debugPrefix + "You now have autism!");
//                    return true;
//
//                case "revive":
//                    if (args.length == 1) {
//                        Player target = p;
//                        RevivePlayer.revivePlayer(p, target);
//                    }else if (args.length == 2) {
//                        Player target = Bukkit.getPlayer(args[1]);
//                        RevivePlayer.revivePlayer(p, target);
//                    }else {
//                        p.sendMessage(debugPrefix + "Please use /debug revive [<player>]");
//                    }
//                    return true;
//
//
//                case "getloc":
//                    TextComponent location = new TextComponent(debugPrefix + "Click to copy block location! " + p.getTargetBlockExact(4).getLocation().getX() + ", " + p.getTargetBlockExact(4).getLocation().getY() + ", " + p.getTargetBlockExact(4).getLocation().getZ());
//                    location.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "" + p.getTargetBlockExact(4).getLocation().getX() + ", " + p.getTargetBlockExact(4).getLocation().getY() + ", " + p.getTargetBlockExact(4).getLocation().getZ()));
//                    p.spigot().sendMessage(location);
//                    return true;
//
//                case "rep":
//
//                    if (args.length == 2) {
//                        try {
//                            p.sendMessage(debugPrefix + "Reputation: " + ChatColor.RED + playerMirror.get(p.getName()).getReputation(Lehrer.getLehrerByName(args[1])));
//                        } catch (Exception faultyInput) {
//                            p.sendMessage(debugPrefix + "Lehrer not found!");
//                        }
//                    } else if (args.length == 3) {
//                        try {
//                            Player target = Bukkit.getPlayer(args[1]);
//                            p.sendMessage(debugPrefix + "Reputation: " + ChatColor.RED + playerMirror.get(target.getName()).getReputation(Lehrer.getLehrerByName(args[2])));
//                        } catch (Exception faultyInput) {
//                            p.sendMessage(debugPrefix + "Lehrer not found!");
//                        }
//                    }else {
//                        p.sendMessage(debugPrefix + "Please use: /debug rep <Lehrer> (Case sensitive!)");
//                        p.sendMessage(debugPrefix + "Or use /debug rep <Lehrer> <Player> (Case sensitive!)");
//                    }
//                    return true;
//
//
//                case "lehrer":
//                    if (args[1].equals("spawn")) {
//                        for(Lehrer lehrer : Lehrer.values()) {
//                            Villager testLehrer = Lehrer.summonLehrer(p.getLocation(), lehrer);
//                            if(testLehrer.getCustomName().contains(args[2])) {
//                                Bukkit.broadcastMessage("Found lehrer");
//                                p.sendMessage(debugPrefix + "Spawned " + lehrer.name() + "!");
//                            }else{
//                                testLehrer.remove();
//                            }
//                        }
//                    } else if (args[1].equals("kill")) {
//                        for (Villager lehrer : lehrerEntityList) {
//                            try {
//                                if (lehrer.getCustomName().contains(args[2])) {
//                                    lehrer.remove();
//                                } else if (args[2].equals("all")) {
//                                    lehrer.remove();
//                                    lehrerEntityList.clear();
//                                    lehrerList.clear();
//                                }
//                            } catch (Exception ignored) {
//                            }
//                        }
//                    } else if (args[1].equals("position")) {
//
//                        Lehrer.updateLehrerPosition(true);
//
//                    } else {
//                        p.sendMessage(debugPrefix + "Please use /debug lehrer <spawn|kill|position>!");
//                    }
//                    return true;
//
//                case "toggle":
//                    if(args[1].equals("join")) {
//                        if (joinMsg.contains(p)) {
//                            p.sendMessage(debugPrefix + "You will no longer recieve join messages!");
//                            joinMsg.remove(p);
//                        } else {
//                            p.sendMessage(debugPrefix + "You will now recieve join messages!");
//                            joinMsg.add(p);
//                        }
//                    }else if(args[1].equals("start")) {
//                        if (startWorks) {
//                            p.sendMessage(debugPrefix + "/start will no longer work!");
//                            startWorks = false;
//                        } else {
//                            p.sendMessage(debugPrefix + "/start will now work again!");
//                            startWorks = true;
//                        }
//                    }
//                    return true;
//
//                case "stun":
//                    if (args.length == 4) {
//                        PlayerStun.stunPlayer(Bukkit.getPlayer(args[1]), Integer.parseInt(args[2]), Boolean.getBoolean(args[3]));
//                        p.sendMessage(debugPrefix + "You stunned " + args[1] + " for " + args[2] + " seconds.");
//                        p.sendMessage(debugPrefix + "inGround: " + args[3]);
//                    } else {
//                        p.sendMessage(debugPrefix + "Please use /debug stun <player> <duration in seconds> <in Ground>");
//                    }
//                    return true;
//
//                case "verweise":
//
//                    if (args[1].equals("add")) {
//                        if (args.length == 3) {
//                            playerMirror.get(p.getName()).addVerweis(Integer.parseInt(args[2]));
//                            p.sendMessage(debugPrefix + "Added Verweis: " + args[2]);
//                        } else if (args.length == 4) {
//                            playerMirror.get(args[2]).addVerweis(Integer.parseInt(args[3]));
//                            p.sendMessage(debugPrefix + "Added Verweis: " + args[3] + " to " + args[2]);
//                        }else {
//                            p.sendMessage(debugPrefix + "Please use /debug verweise <get>");
//                            p.sendMessage(debugPrefix + "or /debug verweise <get> <player>");
//                            p.sendMessage(debugPrefix + "or /debug verweise <add> <amount>");
//                            p.sendMessage(debugPrefix + "or /debug verweise <add> <player> <amount>!");
//                            p.sendMessage(debugPrefix + "You can use negative numbers to remove Verweise");
//                        }
//                    } else if (args[1].equals("get")) {
//                        if (args.length == 2) {
//                            p.sendMessage(debugPrefix + "Your current Verweise: " + playerMirror.get(p.getName()).getVerweise());
//                        } else if (args.length == 3) {
//                            p.sendMessage(debugPrefix + args[2] + "'s current Verweise: " + playerMirror.get(args[2]).getVerweise());
//                        }else {
//                            p.sendMessage(debugPrefix + "Please use /debug verweise <get>");
//                            p.sendMessage(debugPrefix + "or /debug verweise <get> <player>");
//                            p.sendMessage(debugPrefix + "or /debug verweise <add> <amount>");
//                            p.sendMessage(debugPrefix + "or /debug verweise <add> <player> <amount>!");
//                            p.sendMessage(debugPrefix + "You can use negative numbers to remove Verweise");
//                        }
//                    }else {
//                        p.sendMessage(debugPrefix + "Please use /debug verweise <get>");
//                        p.sendMessage(debugPrefix + "or /debug verweise <get> <player>");
//                        p.sendMessage(debugPrefix + "or /debug verweise <add> <amount>");
//                        p.sendMessage(debugPrefix + "or /debug verweise <add> <player> <amount>!");
//                        p.sendMessage(debugPrefix + "You can use negative numbers to remove Verweise");
//                    }
//                    return true;
//
//                case "stundenplan":
//                    if(SchoolWars.gameStarted) {
//                        if (args[1].equals("get")) {
//                            p.sendMessage(debugPrefix + "Current Stundenplan: ");
//                            for (Lehrer lehrer : Stundenplan.studenplan.keySet()) {
//                                p.sendMessage(debugPrefix + lehrer.name() + " -> " + Stundenplan.studenplan.get(lehrer).name());
//                            }
//                        } else if (args[1].equals("update")) {
//                            Stundenplan.updateStundenplan(true);
//                            p.sendMessage(debugPrefix + "Sucessfully updated Stundenplan!");
//                        }else{
//                            p.sendMessage(debugPrefix + "Please use /debug stundenplan <get|update>");
//                        }
//                    }else {
//                        p.sendMessage(debugPrefix + "Game has not started yet!");
//                    }
//                    return true;
//
//                case "intro":
//                    if(args.length == 1) {
//                        Intro.introScene(p);
//                        p.sendMessage(debugPrefix + "Started intro sequence!");
//                    } else if (args.length == 2) {
//                        if(args[1].equals("2")) {
//                            p.sendMessage(debugPrefix + "Started intro sequence at 2. stage!");
//                            Intro.secondSequence(p);
//                        }
//                        if(args[1].equals("3")) {
//                            p.sendMessage(debugPrefix + "Started intro sequence at 3. stage!");
//                            Intro.thirdSequence(p);
//                        }
//                    }else{
//                        p.sendMessage(debugPrefix + "Please use /debug intro [<phase>]!");
//                    }
//                    return true;
//
//                case "fly":
//                    if(args.length == 2) {
//                        Player target = Bukkit.getPlayer(args[1]);
//                        target.setAllowFlight(!target.getAllowFlight());
//                    }
//                    return true;
//
//                case "schulbuch":
//                    p.sendMessage(debugPrefix + "Next book location: " + SchulbuchLevels.currentBookshelfLoc.getX() + ", " + SchulbuchLevels.currentBookshelfLoc.getY() + ", " + SchulbuchLevels.currentBookshelfLoc.getZ());
//                    return true;
//            }
//        }else{
//            sender.sendMessage(debugPrefix + "You are not allowed to use this command!");
//        }
//
//        sender.sendMessage(debugPrefix + "This is not a valid argument!");
//        return false;
//
//    }
}