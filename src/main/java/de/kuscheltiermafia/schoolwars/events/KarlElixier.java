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

package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.ProgressBarHandler;
import io.github.realMorgon.sunriseLib.Sounds;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;


public class KarlElixier implements Listener {

    public int karlDauer = 60 * 20;
    static HashMap<String, Boolean> increase = new HashMap<>();

    @EventHandler
    public void onKarlEvent(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player p = e.getPlayer();
            if(e.getPlayer().getInventory().getItemInMainHand().equals(Items.karls_elexier) && p.getAttribute(Attribute.GENERIC_SCALE).getBaseValue() > 0.95) {
                increase.put(p.getName(), true);
                p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                decreaseSize(p);
                for(int i = 0; i < karlDauer; i++) {
                    int finalI = i;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (p.getAttribute(Attribute.GENERIC_SCALE).getBaseValue() < 1) {
                                double calcProgress = (double) finalI / karlDauer * 100;
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Dauer: " + ProgressBarHandler.progressBarsUpdate(calcProgress, ChatColor.DARK_GREEN) + ChatColor.GRAY + " --"));
                            }
                        }
                    }.runTaskLater(SchoolWars.getPlugin(), i);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for(int i = 1; i < 5 * 20 + 1; i++) {
                                int finalI = i;
                                new BukkitRunnable() {
                                    public void run() {
                                        if(p.getLocation().add(0, 1, 0).getBlock().getType() != Material.AIR && p.getAttribute(Attribute.GENERIC_SCALE).getBaseValue() < 1 && finalI % 5 == 0) {
                                            warn(finalI, p);
                                        }
                                        if (p.getLocation().add(0, 1, 0).getBlock().getType() == Material.AIR && p.getAttribute(Attribute.GENERIC_SCALE).getValue() < 0.55) {
                                            increaseSize(p);
                                            increase.put(p.getName(), false);
                                        }

                                        if (finalI == 5 * 20 - 1 && p.getAttribute(Attribute.GENERIC_SCALE).getValue() < 1){
                                            p.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1);
                                            p.damage(100);
                                        }
                                    }
                                }.runTaskLater(SchoolWars.getPlugin(), i);
                            }
                        }
                    }.runTaskLater(SchoolWars.getPlugin(), karlDauer);
                }
            }
        }
    }


    public static void decreaseSize(Player p) {
        for(int i = 0; i < 5; i++) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(p.getAttribute(Attribute.GENERIC_SCALE).getBaseValue() - 0.1);
                    Sounds.playInArea(org.bukkit.Sound.ENTITY_GENERIC_DRINK, 20, 1, 1, 10, p.getLocation(), 3, 3, 3);
                }
            }.runTaskLater(SchoolWars.getPlugin(), i * 10);
        }
    }

    public static void increaseSize(Player p) {
        if (increase.get(p.getName())) {
            for (int i = 0; i < 5; i++) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(p.getAttribute(Attribute.GENERIC_SCALE).getBaseValue() + 0.1);
                        Sounds.playInArea(Sound.BLOCK_SPONGE_ABSORB, 20, 1, 1, 10, p.getLocation(), 3, 3, 3);
                    }
                }.runTaskLater(SchoolWars.getPlugin(), i * 10);
            }
        }
    }

    public static void warn(int timer, Player p) {
        if (timer % 2 == 0) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Dauer: " + ProgressBarHandler.progressBarsUpdate(100, ChatColor.DARK_RED) + ChatColor.GRAY + " --"));
        } else if (timer % 2 != 0) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Dauer: " + ProgressBarHandler.progressBarsUpdate(100, ChatColor.DARK_GRAY) + ChatColor.GRAY + " --"));
        }
    }

}
