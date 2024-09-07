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
import de.kuscheltiermafia.schoolwars.events.Minasisierung;
import de.kuscheltiermafia.schoolwars.lehrer.*;
import de.kuscheltiermafia.schoolwars.mechanics.PlayerStun;
import de.kuscheltiermafia.schoolwars.mechanics.RevivePlayer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static de.kuscheltiermafia.schoolwars.SchoolWars.playerMirror;

public class Debug implements CommandExecutor {

    public static ArrayList<Player> joinMsg = new ArrayList<>();
    public static ArrayList<Player> debugMode = new ArrayList<>();
    public String debugPrefix = ChatColor.DARK_RED + "[!] " + ChatColor.DARK_GRAY;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player && ((Player) sender).isOp()) {

            Player p = (Player) sender;

            switch (args[0]) {
                case "autism":
                    Minasisierung.onMinasisierung(p);
                    p.sendMessage(debugPrefix + "You now have autism!");
                    break;

                case "revive":
                    if (args.length == 1) {
                        Player target = p;

                        RevivePlayer.revivePlayer(p, target);
                    }

                    if (args.length == 2) {
                        Player target = (Player) Bukkit.getPlayer(args[1]);

                        RevivePlayer.revivePlayer(p, target);
                    }
                    break;

                case "scale":
                    if (args.length == 2) {
                        Player target = (Player) sender;

                        target.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(Double.parseDouble(args[1]));
                    }

                    if (args.length == 3) {
                        Player target = (Player) Bukkit.getPlayer(args[2]);

                        target.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(Double.parseDouble(args[1]));
                    }
                    break;

                case "getloc":
                    TextComponent location = new TextComponent(debugPrefix + "Click to copy block location! " + p.getTargetBlockExact(4).getLocation().getX() + ", " + p.getTargetBlockExact(4).getLocation().getY() + ", " + p.getTargetBlockExact(4).getLocation().getZ());
                    location.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "" + p.getTargetBlockExact(4).getLocation().getX() + ", " + p.getTargetBlockExact(4).getLocation().getY() + ", " + p.getTargetBlockExact(4).getLocation().getZ()));
                    p.spigot().sendMessage(location);
                    break;

                case "rep":

                    if (args.length == 1) {
                        p.sendMessage(debugPrefix + "Please use: /debug rep <Lehrer> (Groß- und Kleinschreibung beachten!)");
                        p.sendMessage(debugPrefix + "Or use /debug rep <Player> <Lehrer> (Groß- und Kleinschreibung beachten!)");
                    } else if (args.length == 2) {
                        try {
                            p.sendMessage(debugPrefix + "Reputation: " + ChatColor.RED + playerMirror.get(p.getName()).getReputation(args[1]));
                        } catch (Exception faultyInput) {
                            p.sendMessage(debugPrefix + "Lehrer not found!");
                        }
                    } else if (args.length == 3) {
                        try {
                            Player target = Bukkit.getPlayer(args[1]);
                            p.sendMessage(debugPrefix + "Reputation: " + ChatColor.RED + playerMirror.get(target.getName()).getReputation(args[2]));
                        } catch (Exception faultyInput) {
                            p.sendMessage(debugPrefix + "Lehrer not found!");
                        }
                    }
                    break;
                case "lehrer":
                    if (args[1].equals("spawn")) {

                        for(Lehrer lehrer : Lehrer.values()) {
                            if (args[2] == lehrer.name()){
                                LehrerHandler.summonLehrer(p.getLocation(), lehrer);
                                p.sendMessage(debugPrefix + "Spawned Lehrer: " + lehrer.name());
                                return false;
                            }
                        }
                        p.sendMessage(debugPrefix + "Lehrer not found!");

                    }
                    break;
                case "toggle":
                    if(args[1].equals("join")) {
                        if (joinMsg.contains(p)) {
                            p.sendMessage(debugPrefix + "You will no longer recieve join messages!");
                            joinMsg.remove(p);
                        } else {
                            p.sendMessage(debugPrefix + "You will now recieve join messages!");
                            joinMsg.add(p);
                        }
                    }else if(args[1].equals("debug")) {
                        if (debugMode.contains(p)) {
                            p.sendMessage(debugPrefix + "You are now affected by /start again!!");
                            debugMode.remove(p);
                        } else {
                            p.sendMessage(debugPrefix + "You will no longer be affected by /start!");
                            debugMode.add(p);
                        }
                    }
                    break;
                case "stun":
                    if (args.length == 4) {
                        PlayerStun.stunPlayer(Bukkit.getPlayer(args[1]), Integer.parseInt(args[2]), Boolean.getBoolean(args[3]));
                        p.sendMessage(debugPrefix + "You stunned " + args[1] + " for " + args[2] + " seconds.");
                        p.sendMessage(debugPrefix + "inGround: " + args[3]);
                    } else {
                        p.sendMessage(debugPrefix + "Please use /debug stun <player> <duration in seconds>");
                    }
                    break;
                case "area":
                    if (args[1].equals("check")) {
                        if (args.length == 2) {
                            p.sendMessage(debugPrefix + "The players: ");
                        }
                    }
                    break;
                case "verweise":
                    try {
                        if (args[1].equals("add")) {
                            if (args.length == 3) {
                                playerMirror.get(p.getName()).addVerweis(Integer.parseInt(args[2]));
                                p.sendMessage(debugPrefix + "Added Verweis: " + args[2]);
                            } else if (args.length == 4) {
                                playerMirror.get(args[2]).addVerweis(Integer.parseInt(args[3]));
                                p.sendMessage(debugPrefix + "Added Verweis: " + args[3] + " to " + args[2]);
                            }
                        } else if (args[1].equals("get")) {
                            if (args.length == 2) {
                                p.sendMessage(debugPrefix + "Your current Verweise: " + playerMirror.get(p.getName()).getVerweise());
                            } else if (args.length == 3) {
                                p.sendMessage(debugPrefix + args[2] + "'s current Verweise: " + playerMirror.get(args[2]).getVerweise());
                            }
                        }
                    } catch (Exception e) {
                        p.sendMessage(debugPrefix + "Please use /debug verweise <get>");
                        p.sendMessage(debugPrefix + "or /debug verweise <get> <player>");
                        p.sendMessage(debugPrefix + "or /debug verweise <add> <amount>");
                        p.sendMessage(debugPrefix + "or /debug verweise <add> <player> <amount>!");
                        p.sendMessage(debugPrefix + "You can use negative numbers to remove Verweise");
                    }
                    break;
                case "stundenplan":
                    if(SchoolWars.gameStarted) {
                        if (args[1].equals("get")) {
                            p.sendMessage(debugPrefix + "Current Stundenplan: ");
                            for (Lehrer lehrer : Stundenplan.studenplan.keySet()) {
                                p.sendMessage(debugPrefix + lehrer.name() + " -> " + Stundenplan.studenplan.get(lehrer).name());
                            }
                        } else if (args[1].equals("reset")) {
                            p.sendMessage(debugPrefix + "Sucessfully resetted Stundenplan!");
                            Stundenplan.updateStundenplan(true);
                        }else{
                            p.sendMessage(debugPrefix + "Please use /debug stundenplan <get|reset>");
                        }
                    }else {
                        p.sendMessage(debugPrefix + "Game has not started yet!");
                    }
                    break;
            }
        }

        return false;

    }
}