package de.kuscheltiermafia.schoolwars.commands;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.events.Minasisierung;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static de.kuscheltiermafia.schoolwars.SchoolWars.playerReputation;
import static de.kuscheltiermafia.schoolwars.events.RevivePlayer.deadPlayers;

public class Debug implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player && ((Player) sender).isOp()) {

            Player p = (Player) sender;

            switch (args[0]) {
                case "autismcheck":
                    Minasisierung.onMinasisierung(p);
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
                    TextComponent location = new TextComponent("[DEBUG] Click to copy block location! " + p.getTargetBlockExact(4).getLocation().getX() + ", " + p.getTargetBlockExact(4).getLocation().getY() + ", " + p.getTargetBlockExact(4).getLocation().getZ());
                    location.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "" + p.getTargetBlockExact(4).getLocation().getX() + ", " + p.getTargetBlockExact(4).getLocation().getY() + ", " + p.getTargetBlockExact(4).getLocation().getZ()));
                    p.spigot().sendMessage(location);
                    break;

                case "rep":

                    if (args.length == 1) {
                        p.sendMessage("Please use: /debug rep <Lehrer> (Groß- und Kleinschreibung beachten!)");
                        p.sendMessage("Or use /debug rep <Player> <Lehrer> (Groß- und Kleinschreibung beachten!)");
                    } else if (args.length == 2) {
                        try {
                            p.sendMessage(ChatColor.GRAY + "Reputation: " + ChatColor.RED + playerReputation.get(p.getName()).getReputation(args[1]));
                        } catch (Exception faultyInput) {
                            p.sendMessage(ChatColor.RED + "Lehrer not found!");
                        }
                    } else if (args.length == 3) {
                        try {
                            Player target = Bukkit.getPlayer(args[1]);
                            p.sendMessage(ChatColor.GRAY + "Reputation: " + ChatColor.RED + playerReputation.get(target.getName()).getReputation(args[2]));
                        } catch (Exception faultyInput) {
                            p.sendMessage(ChatColor.RED + "Lehrer not found!");
                        }
                    }
                    break;
                }
            }
            return false;
        }
    }