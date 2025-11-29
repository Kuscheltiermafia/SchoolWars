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


/**
 * Handles Karl's Elixir item effects.
 * <p>
 * When consumed, the elixir causes the player to shrink over time,
 * enabling access to smaller spaces and alternate routes.
 * </p>
 */
public class KarlElixier implements Listener {

    /** Duration of the shrinking effect in ticks (60 seconds). */
    public int karlDauer = 60 * 20;
    
    /** Tracks whether each player is currently in the size increase phase. */
    static HashMap<String, Boolean> increase = new HashMap<>();

    /**
     * Handles using the Karl's Elixir item to shrink the player.
     */
    @EventHandler
    public void onKarlEvent(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player p = e.getPlayer();
            
            // ========== Validate elixir usage ==========
            // Only work if player is at normal size and holding the elixir
            if(e.getPlayer().getInventory().getItemInMainHand().equals(Items.karls_elexier) && p.getAttribute(Attribute.GENERIC_SCALE).getBaseValue() > 0.95) {
                increase.put(p.getName(), true);
                p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                
                // ========== Apply shrinking effect ==========
                decreaseSize(p);
                
                // ========== Duration timer with progress bar ==========
                for(int i = 0; i < karlDauer; i++) {
                    int finalI = i;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            // Show progress bar while shrunk
                            if (p.getAttribute(Attribute.GENERIC_SCALE).getBaseValue() < 1) {
                                double calcProgress = (double) finalI / karlDauer * 100;
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Dauer: " + ProgressBarHandler.progressBarsUpdate(calcProgress, ChatColor.DARK_GREEN) + ChatColor.GRAY + " --"));
                            }
                        }
                    }.runTaskLater(SchoolWars.getPlugin(), i);

                    // ========== Handle size restoration after duration ==========
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for(int i = 1; i < 5 * 20 + 1; i++) {
                                int finalI = i;
                                new BukkitRunnable() {
                                    public void run() {
                                        // Warn if player is stuck in a tight space
                                        if(p.getLocation().add(0, 1, 0).getBlock().getType() != Material.AIR && p.getAttribute(Attribute.GENERIC_SCALE).getBaseValue() < 1 && finalI % 5 == 0) {
                                            warn(finalI, p);
                                        }
                                        // Restore size if player has room
                                        if (p.getLocation().add(0, 1, 0).getBlock().getType() == Material.AIR && p.getAttribute(Attribute.GENERIC_SCALE).getValue() < 0.55) {
                                            increaseSize(p);
                                            increase.put(p.getName(), false);
                                        }

                                        // Kill player if they can't restore size in time
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


    /**
     * Gradually decreases a player's size over time with drinking sounds.
     *
     * @param p the player to shrink
     */
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

    /**
     * Gradually increases a player's size back to normal with sounds.
     *
     * @param p the player to restore to normal size
     */
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

    /**
     * Shows a flashing warning to player when stuck in a tight space.
     *
     * @param timer current warning tick for alternating colors
     * @param p the player to warn
     */
    public static void warn(int timer, Player p) {
        // Alternate between red and gray for flashing effect
        if (timer % 2 == 0) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Dauer: " + ProgressBarHandler.progressBarsUpdate(100, ChatColor.DARK_RED) + ChatColor.GRAY + " --"));
        } else if (timer % 2 != 0) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Dauer: " + ProgressBarHandler.progressBarsUpdate(100, ChatColor.DARK_GRAY) + ChatColor.GRAY + " --"));
        }
    }

}
