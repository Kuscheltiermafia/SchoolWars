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

public class VornbergerEvents implements Listener {

    @EventHandler
    public void onVornbergerEvent(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if( event.getPlayer().getInventory().getItemInMainHand().equals(Items.machete) && event.getPlayer().getInventory().getItemInOffHand().equals(Items.zwiebel)) {
                
                // Check for success based on probability (33% chance)
                if (Math.random() < ProbabilityConfig.getProbability("vornberger.onion_cutting_success_chance", 0.33)) {
                    event.getPlayer().getInventory().setItemInOffHand(Items.geschnittene_zwiebel);
                } else {
                    PlayerStun.stunPlayer(event.getPlayer(), 4, false);
                    event.getPlayer().setHealth(event.getPlayer().getHealth() - 4);
                    event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Du hast dir fett in den Finger geschnitten  --"));
                }
            }
        }
    }

    @EventHandler
    public void onZwiebelCrate(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getClickedBlock().getLocation().equals(new Location(SchoolWars.WORLD, -60.0, 80.0, 196.0)) || e.getClickedBlock().getLocation().equals(new Location(SchoolWars.WORLD, -58.0, 80.0, 197.0))) {
                Player p = e.getPlayer();

                Inventory zwiebelKiste = Bukkit.createInventory(null, 9 * 3, ChatColor.DARK_RED + "Zwiebelkiste");
                for(int i = 0; i < ProbabilityConfig.getInteger("vornberger.zwiebel_count", 3); i++) {
                    Random rand = new Random();
                    int random = rand.nextInt(27);

                    zwiebelKiste.setItem(random, Items.zwiebel);
                }
                for(int i = 0; i < zwiebelKiste.getSize(); i++) {
                    if(zwiebelKiste.getItem(i) == null) {
                        zwiebelKiste.setItem(i, Items.geschimmelte_zwiebel);
                    }
                }

                p.openInventory(zwiebelKiste);
            }
        }
    }

    @EventHandler
    public void onZwiebelkisteKlick(InventoryClickEvent e) {
        if( e.getView().getTitle().equals(ChatColor.DARK_RED + "Zwiebelkiste")) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() != null && e.getCurrentItem().equals(Items.zwiebel)) {
                p.getInventory().addItem(Items.zwiebel);
                p.sendMessage(ChatColor.GREEN + "Du hast eine Zwiebel erhalten!");
                p.closeInventory();
            } else if (e.getCurrentItem() != null && e.getCurrentItem().equals(Items.geschimmelte_zwiebel)) {
                p.sendMessage(ChatColor.RED + "Tja, die war verschimmelt...");
                p.closeInventory();
            }
        }
    }
}
