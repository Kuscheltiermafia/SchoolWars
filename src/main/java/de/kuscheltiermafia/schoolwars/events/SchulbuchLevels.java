package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.items.Items;
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

public class SchulbuchLevels implements Listener {
    public static HashMap<Player, Integer> knowledgeAquiered = new HashMap<Player, Integer>();
    public static ArrayList<Location> bookshelfLoc = new ArrayList<Location>();

    public static void initShelfLocations() {
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), 23, 89, 190));
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), 43, 89, 191));
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), -52, 91, 175));
    }

    @EventHandler
    public void onBookshelfInteraction(PlayerInteractEvent e) {
        if(e.getPlayer().getInventory().getItemInMainHand().equals(Items.schulbuch1) || e.getPlayer().getInventory().getItemInMainHand().equals(Items.schulbuch2) || e.getPlayer().getInventory().getItemInMainHand().equals(Items.schulbuch3) || e.getPlayer().getInventory().getItemInMainHand().equals(Items.schulbuch4) || e.getPlayer().getInventory().getItemInMainHand().equals(Items.schulbuch5)) {
            e.setCancelled(true);
            Player p = e.getPlayer();
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                Bukkit.broadcastMessage("Right Clicked block");
                Random rand = new Random();
                int random = rand.nextInt(bookshelfLoc.size());
                for(int i = 0; i < bookshelfLoc.size(); i++) {
                    Bukkit.broadcastMessage("Cycling...");
                    if(e.getClickedBlock().getLocation().equals(bookshelfLoc.get(random + 1))) {
                        Bukkit.broadcastMessage("Right book");
                        if(knowledgeAquiered.containsKey(p)) {
                            Bukkit.broadcastMessage("Already has knowledge");
                            knowledgeAquiered.put(p, knowledgeAquiered.get(p) + 1);
                            checkSchulbuchUpgrade(p, knowledgeAquiered.get(p));
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Schulbücher gesammelt: " + ChatColor.RED + knowledgeAquiered.get(p) + ChatColor.GRAY + " --"));
                        }else{
                            Bukkit.broadcastMessage("Knew nothing lmao");
                            knowledgeAquiered.put(p, 1);
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Schulbücher gesammelt: " + ChatColor.RED + knowledgeAquiered.get(p) + ChatColor.GRAY + " --"));
                        }
                        break;
                    }
                }
            }
        }
    }

    public static void checkSchulbuchUpgrade(Player p, int b) {
        if(b % 5 == 0) {
            for(ItemStack item : p.getInventory().getContents()) {
                if(item.equals(Items.schulbuch1)) {
                    item.setItemMeta(Items.schulbuch2.getItemMeta());
                }
                if(item.equals(Items.schulbuch2)) {
                    item.setItemMeta(Items.schulbuch3.getItemMeta());
                }
                if(item.equals(Items.schulbuch3)) {
                    item.setItemMeta(Items.schulbuch4.getItemMeta());
                }
                if(item.equals(Items.schulbuch4)) {
                    item.setItemMeta(Items.schulbuch5.getItemMeta());
                }
            }
        }
    }
}
