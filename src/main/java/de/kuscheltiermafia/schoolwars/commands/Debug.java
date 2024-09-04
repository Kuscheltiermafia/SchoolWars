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
import de.kuscheltiermafia.schoolwars.mechanics.Bereiche;
import de.kuscheltiermafia.schoolwars.mechanics.LehrerHandler;
import de.kuscheltiermafia.schoolwars.mechanics.PlayerStun;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

import static de.kuscheltiermafia.schoolwars.SchoolWars.playerReputation;
import static de.kuscheltiermafia.schoolwars.events.RevivePlayer.deadPlayers;

public class Debug implements CommandExecutor {

    public static ArrayList<Player> joinMsg = new ArrayList<>();
    public String debugPrefix = ChatColor.DARK_RED + "[!] " + ChatColor.DARK_GRAY;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player && ((Player) sender).isOp()) {

            Player p = (Player) sender;

            switch (args[0]) {
                case "autism":
                    Minasisierung.onMinasisierung(p);
                    p.sendMessage( debugPrefix + "You now have autism!");
                    break;

                case "revive":
                    if (args.length == 1) {
                        Player target = p;

                        Bukkit.getEntity(deadPlayers.get(target.getName())).remove();
                        target.teleport(target.getLocation().add(0, 1, 0));
                        target.removePotionEffect(PotionEffectType.SLOWNESS);
                        target.removePotionEffect(PotionEffectType.RESISTANCE);
                        target.setHealth(20);
                        target.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 255, true, true, true));

                        deadPlayers.remove(target.getName());
                    }

                    if (args.length == 2) {
                        Player target = (Player) Bukkit.getPlayer(args[1]);

                        Bukkit.getEntity(deadPlayers.get(target.getName())).remove();
                        target.teleport(target.getLocation().add(0, 1, 0));
                        target.removePotionEffect(PotionEffectType.SLOWNESS);
                        target.removePotionEffect(PotionEffectType.RESISTANCE);
                        target.setHealth(20);
                        target.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 255, true, true, true));

                        deadPlayers.remove(target.getName());
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
                            p.sendMessage(debugPrefix + "Reputation: " + ChatColor.RED + playerReputation.get(p.getName()).getReputation(args[1]));
                        } catch (Exception faultyInput) {
                            p.sendMessage(debugPrefix + "Lehrer not found!");
                        }
                    } else if (args.length == 3) {
                        try {
                            Player target = Bukkit.getPlayer(args[1]);
                            p.sendMessage(debugPrefix + "Reputation: " + ChatColor.RED + playerReputation.get(target.getName()).getReputation(args[2]));
                        } catch (Exception faultyInput) {
                            p.sendMessage(debugPrefix + "Lehrer not found!");
                        }
                    }
                    break;
                case "lehrer":
                    if(args[1].equals("spawn")) {
                        switch(args[2]) {
                            case "schneider":
                                LehrerHandler.createFrauSchneider(p.getLocation());
                                p.sendMessage(debugPrefix + "Successfully spawned Frau Schneider at your location!");
                                break;
                            case "fischer":
                                LehrerHandler.createHerrFischer(p.getLocation());
                                p.sendMessage(debugPrefix + "Successfully spawned Herr Fischer at your location!");
                                break;
                        }
                    }
                    break;
                case "togglejoin":
                    if(joinMsg.contains(p)) {
                        p.sendMessage(debugPrefix + "You will no longer recieve join messages!");
                        joinMsg.remove(p);
                    }else{
                        p.sendMessage(debugPrefix + "You will now recieve join messages!");
                        joinMsg.add(p);
                    }
                    break;
                case "stun":
                    if(args.length == 4) {
                        PlayerStun.stunPlayer(Bukkit.getPlayer(args[1]), Integer.parseInt(args[2]), Boolean.getBoolean(args[3]));
                        p.sendMessage(debugPrefix + "You stunned " + args[1] + " for " + args[2] + " seconds.");
                        p.sendMessage(debugPrefix + "inGround: " + args[3]);
                    }else{
                        p.sendMessage(debugPrefix + "Please use /debug stun <player> <duration in seconds>");
                    }
                    break;
                case "area":
                    if(args[1].equals("check")) {
                        if(args.length == 2) {
                            p.sendMessage(debugPrefix + "Your current area is: " + Bereiche.checkArea(p));
                        }
                    }
                }
            }
            return false;
        }
    }