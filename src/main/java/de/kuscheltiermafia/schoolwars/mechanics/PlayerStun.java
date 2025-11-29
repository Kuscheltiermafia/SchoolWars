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
import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handles player stun mechanics that temporarily immobilize players.
 * <p>
 * When stunned, a player:
 * <ul>
 *   <li>Cannot move</li>
 *   <li>Cannot interact with items or the world</li>
 *   <li>Has their hotbar replaced with "rope" items</li>
 *   <li>Can optionally be sunk into the ground</li>
 * </ul>
 * </p>
 */
public class PlayerStun implements Listener {

    /** List of currently stunned players. */
    public static ArrayList<Player> stunned = new ArrayList<>();
    
    /** Backup of player hotbar items during stun. */
    public static HashMap<Player, ItemStack[]> playerInvsave = new HashMap<>();

    /**
     * Stuns a player for a specified duration.
     *
     * @param p the player to stun
     * @param duration the stun duration in seconds
     * @param inFloor if true, sinks the player 1.4 blocks into the ground
     */
    public static void stunPlayer(Player p, int duration, boolean inFloor) {
        stunned.add(p);
        ItemStack[] hotbarItems = new ItemStack[]{p.getInventory().getItem(0), p.getInventory().getItem(1), p.getInventory().getItem(2), p.getInventory().getItem(3), p.getInventory().getItem(4), p.getInventory().getItem(5), p.getInventory().getItem(6), p.getInventory().getItem(7), p.getInventory().getItem(8)};
        for(int i = 0; i < 9; i++) {
            p.getInventory().setItem(i, Items.strick);
        }

        playerInvsave.put(p, hotbarItems);

        for(int i = 0; i < duration * 20; i++) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.getInventory().setHeldItemSlot(4);
                }
            }.runTaskLater(SchoolWars.getPlugin(), i);
        }

        if(inFloor) {
            p.teleport(p.getLocation().subtract(0, 1.4, 0));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                stunned.remove(p);
                p.getInventory().remove(Items.strick);

                for(int i = 0; i < 9; i++) {
                    ItemStack[] hotbarStacks = playerInvsave.get(p);
                    p.getInventory().setItem(i, hotbarStacks[i]);
                }

                if(inFloor) {
                    p.teleport(p.getLocation().add(0, 2, 0));
                }
            }
        }.runTaskLater(SchoolWars.getPlugin(), duration * 20L);

    }


    /**
     * Prevents stunned players from moving.
     *
     * @param e the player move event
     */
    @EventHandler
    public void onStunnedJump(PlayerMoveEvent e) {
        if(stunned.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    
    /**
     * Prevents stunned players from interacting with the world.
     *
     * @param e the player interact event
     */
    @EventHandler
    public void onStunnedInteraction(PlayerInteractEvent e) {
        if(stunned.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    
    /**
     * Prevents stunned players from dropping items.
     *
     * @param e the player drop item event
     */
    @EventHandler
    public void onStunnedDrop(PlayerDropItemEvent e) {
        if(stunned.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    
    /**
     * Prevents stunned players from interacting with their inventory.
     *
     * @param e the inventory click event
     */
    @EventHandler
    public void onStunnedInv(InventoryClickEvent e) {
        if(stunned.contains(e.getWhoClicked())) {
            e.setCancelled(true);
        }
    }
}
