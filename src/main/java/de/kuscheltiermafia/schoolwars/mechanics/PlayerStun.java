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

public class PlayerStun implements Listener {

    public static ArrayList<Player> stunned = new ArrayList<Player>();
    public static HashMap<Player, ItemStack[]> playerInvsave = new HashMap<>();


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
        }.runTaskLater(SchoolWars.getPlugin(), duration * 20);

    }


    @EventHandler
    public void onStunnedJump(PlayerMoveEvent e) {
        if(stunned.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onStunnedInteraction(PlayerInteractEvent e) {
        if(stunned.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onStunnedDrop(PlayerDropItemEvent e) {
        if(stunned.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onStunnedInv(InventoryClickEvent e) {
        if(stunned.contains(e.getWhoClicked())) {
            e.setCancelled(true);
        }
    }
}
