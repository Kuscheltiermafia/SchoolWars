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

import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.lehrer.Area;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Manages the schoolbook leveling and collection system.
 * <p>
 * Players can find and collect knowledge from bookshelves around the school,
 * with locations cycling randomly to encourage exploration. Collecting books
 * upgrades the player's schoolbook weapon.
 * </p>
 */
public class SchulbuchLevels implements Listener {

    /** Tracks how much knowledge each player has collected. */
    public static HashMap<Player, Integer> knowledgeAquiered = new HashMap<Player, Integer>();
    
    /** List of all possible bookshelf locations. */
    public static ArrayList<Location> bookshelfLoc = new ArrayList<Location>();
    
    /** Random index for current active bookshelf. */
    public static int random;

    /** Currently active bookshelf location where knowledge can be collected. */
    public static Location currentBookshelfLoc;

    /**
     * Initializes all bookshelf locations in the school.
     * Call this once during plugin startup.
     */
    public static void initShelfLocations() {
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), 23, 89, 190));
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), 43, 89, 191));
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), -52, 91, 175));
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), -54.0, 81.0, 194.0));
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), -50.0, 89.0, 177.0));
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), -54.0, 83.0, 172.0));
    }

    /**
     * Handles clicking on bookshelves to collect knowledge.
     * Only works when player is holding a schoolbook and clicks the active bookshelf.
     */
    @EventHandler
    public void onBookshelfInteraction(PlayerInteractEvent e) {
        // Check if player is holding any level of schoolbook
        if (Items.isSpecificItem(e.getPlayer().getInventory().getItemInMainHand(), "schulbuch1") || Items.isSpecificItem(e.getPlayer().getInventory().getItemInMainHand(), "schulbuch2") || Items.isSpecificItem(e.getPlayer().getInventory().getItemInMainHand(), "schulbuch3") || Items.isSpecificItem(e.getPlayer().getInventory().getItemInMainHand(), "schulbuch4") || Items.isSpecificItem(e.getPlayer().getInventory().getItemInMainHand(), "schulbuch5")) {
            e.setCancelled(true);
            Player p = e.getPlayer();
            
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                // ========== Check if this is the active bookshelf ==========
                for(int i = 0; i < bookshelfLoc.size(); i++) {
                    if(e.getClickedBlock().getLocation().equals(currentBookshelfLoc)) {
                        // ========== Increment knowledge counter ==========
                                        if(knowledgeAquiered.containsKey(p)) {
                                            knowledgeAquiered.put(p, knowledgeAquiered.get(p) + 1);
                                            checkSchulbuchUpgrade(p, knowledgeAquiered.get(p));
                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Schulbücher gesammelt: " + ChatColor.RED + knowledgeAquiered.get(p) + ChatColor.GRAY + " --"));
                                            resetBookshelf();
                                        } else {
                                            // First book collected by this player
                                            knowledgeAquiered.put(p, 1);
                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Schulbücher gesammelt: " + ChatColor.RED + knowledgeAquiered.get(p) + ChatColor.GRAY + " --"));
                                            resetBookshelf();
                                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * Checks if player has collected enough books to upgrade their schoolbook.
     * Upgrades occur at 5, 10, 15, and 20 books collected.
     *
     * @param p the player to check
     * @param b the current book count
     */
    public static void checkSchulbuchUpgrade(Player p, int b) {
        if(b % 5 == 0) {
            // ========== Upgrade based on threshold ==========
                if(b == 5) {
                    ItemStack newBook = Items.getItem("schulbuch2");
                    if (newBook != null) p.getInventory().setItemInMainHand(newBook);
                    ItemStack oldBook = Items.getItem("schulbuch1");
                    ItemStack nextBook = Items.getItem("schulbuch2");
                    p.sendMessage(ChatColor.DARK_GRAY + "Dein " + ChatColor.BLUE + (oldBook != null ? oldBook.getItemMeta().getDisplayName() : "Buch") + ChatColor.DARK_GRAY + " wurde zu einem " + ChatColor.DARK_BLUE + (nextBook != null ? nextBook.getItemMeta().getDisplayName() : "Buch") + ChatColor.DARK_GRAY + ".");
                }
                if(b == 10) {
                    ItemStack newBook = Items.getItem("schulbuch3");
                    if (newBook != null) p.getInventory().setItemInMainHand(newBook);
                    ItemStack prev = Items.getItem("schulbuch2");
                    ItemStack next = Items.getItem("schulbuch3");
                    p.sendMessage(ChatColor.DARK_GRAY + "Dein " + ChatColor.DARK_BLUE + (prev != null ? prev.getItemMeta().getDisplayName() : "Buch") + ChatColor.DARK_GRAY + " wurde zu einem " + ChatColor.RED + (next != null ? next.getItemMeta().getDisplayName() : "Buch") + ChatColor.DARK_GRAY + ".");
                }
                if(b == 15) {
                    ItemStack newBook = Items.getItem("schulbuch4");
                    if (newBook != null) p.getInventory().setItemInMainHand(newBook);
                    ItemStack prev = Items.getItem("schulbuch3");
                    ItemStack next = Items.getItem("schulbuch4");
                    p.sendMessage(ChatColor.DARK_GRAY + "Dein " + ChatColor.RED + (prev != null ? prev.getItemMeta().getDisplayName() : "Buch") + ChatColor.DARK_GRAY + " wurde zu einem " + ChatColor.GREEN + (next != null ? next.getItemMeta().getDisplayName() : "Buch") + ChatColor.DARK_GRAY + ".");
                }
                if(b == 20) {
                    ItemStack newBook = Items.getItem("schulbuch5");
                    if (newBook != null) p.getInventory().setItemInMainHand(newBook);
                    ItemStack prev = Items.getItem("schulbuch4");
                    ItemStack next = Items.getItem("schulbuch5");
                    p.sendMessage(ChatColor.DARK_GRAY + "Dein " + ChatColor.GREEN + (prev != null ? prev.getItemMeta().getDisplayName() : "Buch") + ChatColor.DARK_GRAY + " wurde zu einem " + ChatColor.DARK_RED + (next != null ? next.getItemMeta().getDisplayName() : "Buch") + ChatColor.DARK_GRAY + ".");
                }
        }
    }

    /**
     * Handles using the library card to find the next book location.
     */
    @EventHandler
    public void onAusleiheUse(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Items.isSpecificItem(e.getPlayer().getInventory().getItemInMainHand(), "ausleihschein")) {
                // Show the area name where the active bookshelf is located
                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Nächstes Schulbuch befindet sich in: " + ChatColor.RED + Area.getAreaByLocation(currentBookshelfLoc).getName() + ChatColor.GRAY + " --"));
            }
        }
    }

    /**
     * Selects a new random bookshelf as the active location.
     * Called after each successful book collection.
     */
    public static void resetBookshelf() {
        Random rand = new Random();
        random = rand.nextInt(bookshelfLoc.size());
        currentBookshelfLoc = bookshelfLoc.get(random);
    }
}
