package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.config.ProbabilityConfig;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.PlayerStun;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Random;

/**
 * Handles Vornberger-themed events in SchoolWars.
 * Manages onion cutting mechanics with success/failure chances,
 * onion crate interactions, and inventory management for the cooking system.
 */
public class VornbergerEvents implements Listener {

    /**
     * Handles the onion cutting mechanic when players use a machete on an onion.
     * Players must hold a machete in their main hand and an onion in their off hand.
     * Success turns the onion into a cut onion, failure causes damage and stunning.
     * 
     * @param event The PlayerInteractEvent triggered when interacting with items
     */
    @EventHandler
    public void onVornbergerEvent(PlayerInteractEvent event) {
        // Check if player is right-clicking (either in air or on a block)
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            // Verify player has machete in main hand and onion in off hand
            if( event.getPlayer().getInventory().getItemInMainHand().equals(Items.machete) && event.getPlayer().getInventory().getItemInOffHand().equals(Items.zwiebel)) {
                
                // Attempt to cut the onion based on configured probability (default 33% success)
                if (Math.random() < ProbabilityConfig.getProbability("vornberger.onion_cutting_success_chance", 0.33)) {
                    // Success: Transform onion into cut onion
                    event.getPlayer().getInventory().setItemInOffHand(Items.geschnittene_zwiebel);
                } else {
                    // Failure: Player cuts themselves, takes damage and gets stunned
                    PlayerStun.stunPlayer(event.getPlayer(), 4, false);
                    event.getPlayer().setHealth(event.getPlayer().getHealth() - 4);
                    event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Du hast dir fett in den Finger geschnitten  --"));
                }
            }
        }
    }

    /**
     * Handles interaction with onion crates at specific locations.
     * Opens an inventory GUI with a mix of good and moldy onions for players to choose from.
     * 
     * @param e The PlayerInteractEvent triggered when right-clicking on onion crate blocks
     */
    @EventHandler
    public void onZwiebelKrate(PlayerInteractEvent e) {
        // Check if player right-clicked on one of the onion crate locations
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getClickedBlock().getLocation().equals(new Location(SchoolWars.WORLD, -60.0, 80.0, 196.0)) || e.getClickedBlock().getLocation().equals(new Location(SchoolWars.WORLD, -58.0, 80.0, 197.0))) {
                Player p = e.getPlayer();

                // Create onion crate inventory GUI (3 rows, 27 slots)
                Inventory zwiebelKiste = Bukkit.createInventory(null, 9 * 3, ChatColor.DARK_RED + "Zwiebelkiste");
                
                // Place good onions randomly in the inventory (configurable amount, default 3)
                for(int i = 0; i < ProbabilityConfig.getInteger("vornberger.zwiebel_count", 3); i++) {
                    Random rand = new Random();
                    int random = rand.nextInt(27); // Random slot in the 27-slot inventory
                    zwiebelKiste.setItem(random, Items.zwiebel);
                }
                
                // Fill remaining empty slots with moldy onions
                for(int i = 0; i < zwiebelKiste.getSize(); i++) {
                    if(zwiebelKiste.getItem(i) == null) {
                        zwiebelKiste.setItem(i, Items.geschimmelte_zwiebel);
                    }
                }

                // Open the onion crate inventory for the player
                p.openInventory(zwiebelKiste);
            }
        }
    }

    /**
     * Handles clicks within the onion crate inventory.
     * Players can take good onions but are disappointed by moldy ones.
     * 
     * @param e The InventoryClickEvent triggered when clicking in the onion crate GUI
     */
    @EventHandler
    public void onZwiebelkisteKlick(InventoryClickEvent e) {
        // Check if click is in the onion crate inventory
        if( e.getView().getTitle().equals(ChatColor.DARK_RED + "Zwiebelkiste")) {
            e.setCancelled(true); // Prevent normal inventory interactions
            Player p = (Player) e.getWhoClicked();
            
            if (e.getCurrentItem() != null && e.getCurrentItem().equals(Items.zwiebel)) {
                // Player clicked on a good onion - give it to them
                p.getInventory().addItem(Items.zwiebel);
                p.sendMessage(ChatColor.GREEN + "Du hast eine Zwiebel erhalten!");
                p.closeInventory();
            } else if (e.getCurrentItem() != null && e.getCurrentItem().equals(Items.geschimmelte_zwiebel)) {
                // Player clicked on a moldy onion - show disappointment message
                p.sendMessage(ChatColor.RED + "Tja, die war verschimmelt...");
                p.closeInventory();
            }
        }
    }
}
