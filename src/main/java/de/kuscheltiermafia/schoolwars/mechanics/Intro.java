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

package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Intro {

    public static void introScene(Player p) {
        p.setGameMode(GameMode.SPECTATOR);
        ArmorStand cam = p.getWorld().spawn(new Location(SchoolWars.WORLD, -100.0, 73.0, 145.0, -51.4f, -9.6f), ArmorStand.class);
        cam.setInvisible(true);
        cam.setGravity(false);

        p.setSpectatorTarget(cam);
        Location loc = cam.getLocation();

        p.sendTitle(ChatColor.DARK_RED + "SCHOOL WARS", ChatColor.YELLOW + "", 2 * 20, 8 * 20, 2 * 20);

        for(int i = 0; i < 20 * 10; i++) {
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    loc.add(loc.getDirection().getX() / 4, loc.getDirection().getY() / 4, loc.getDirection().getZ() / 4);
                    p.setSpectatorTarget(cam);
                    cam.teleport(loc);
                    if(finalI == 20 * 10 - 1) {
                        cam.remove();
                        secondSequence(p);
                    }
                }
            }.runTaskLater(SchoolWars.getPlugin(), i);
        }
    }

    public static void secondSequence(Player p) {
        ArmorStand cam = p.getWorld().spawn(new Location(SchoolWars.WORLD, -13.4, 80.0, 176.0, 90f, 9.6f), ArmorStand.class);
        cam.setInvisible(true);
        cam.setGravity(false);
        Location loc = cam.getLocation();
        p.sendTitle(ChatColor.DARK_RED + " ", ChatColor.YELLOW + "Created by: ", 1 * 20, 3 * 20, 1 * 20);

        for(int i = 0; i < 20 * 10; i++) {
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    loc.subtract(loc.getDirection().getX() / 4.3, loc.getDirection().getY() / 4.3, loc.getDirection().getZ() / 4.3);
                    p.setSpectatorTarget(cam);
                    cam.teleport(loc);

                    if (finalI == 20) {
                        p.sendTitle(ChatColor.DARK_RED + " ", ChatColor.YELLOW + "Anton", 1 * 20, 2 * 20, 1 * 20);
                    }
                    if (finalI == 20 * 4) {
                        p.sendTitle(ChatColor.DARK_RED + " ", ChatColor.YELLOW + "Moritz", 1 * 20, 2 * 20, 1 * 20);
                    }

                    if (finalI == 20 * 10 - 1) {
                        cam.remove();
                        thirdSequence(p);
                    }
                }
            }.runTaskLater(SchoolWars.getPlugin(), i);
        }
    }

    public static void thirdSequence(Player p) {
        ArmorStand cam = p.getWorld().spawn(new Location(SchoolWars.WORLD, 27.0, 81.0, 146.0, -90f, 0f), ArmorStand.class);
        cam.setInvisible(true);
        cam.setGravity(false);
        Location loc = cam.getLocation();
        p.sendTitle(ChatColor.DARK_RED + " ", ChatColor.YELLOW + "Vielen vielen Dank an...", 1 * 20, 2 * 20, 1 * 20);
        for(int i = 0; i < 20 * 12; i++) {
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    loc.subtract(loc.getDirection().getX() * 0.27, loc.getDirection().getY() * 0.27, loc.getDirection().getZ() * 0.27);
                    p.setSpectatorTarget(cam);
                    cam.teleport(loc);

                    if (finalI == 20 * 4) {
                        p.sendTitle(ChatColor.DARK_RED + " ", ChatColor.YELLOW + "die Leute die uns beim Bauen und coden geholfen haben...", 1 * 20, 2 * 20, 1 * 20);
                    }
                    if (finalI == 20 * 8) {
                        p.sendTitle(ChatColor.DARK_RED + " ", ChatColor.YELLOW + "und alle Lehrer die in irgendeiner Weise geholfen haben", 1 * 20, 2 * 20, 1 * 20);
                    }
                    if(finalI == 20 * 12 -1) {
                        cam.remove();
                        fourthSequence(p);
                    }
                }
            }.runTaskLater(SchoolWars.getPlugin(), i);
        }
    }

    public static void fourthSequence(Player p) {
        ArmorStand cam = p.getWorld().spawn(new Location(SchoolWars.WORLD, -65, 80.0, 178.0, -90f, 0f), ArmorStand.class);
        cam.setInvisible(true);
        cam.setGravity(false);
        Location loc = cam.getLocation();
        p.sendTitle(ChatColor.DARK_RED + " ", ChatColor.YELLOW + "Viel Spaß!", 1 * 20, 2 * 20, 1 * 20);
        for(int i = 0; i < 20 * 4 + 10; i++) {
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.setSpectatorTarget(cam);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED + "Weiter Informationen gibt es mit /infos (noch nicht)"));
                    if(finalI == 20 * 4 + 9) {
                        cam.remove();
                        p.sendTitle(" ", " ", 1, 1, 1);
                        p.setGameMode(GameMode.SURVIVAL);
                    }
                }
            }.runTaskLater(SchoolWars.getPlugin(), i);
        }
    }
}
