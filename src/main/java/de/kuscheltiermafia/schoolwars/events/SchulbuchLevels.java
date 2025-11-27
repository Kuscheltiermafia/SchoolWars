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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Manages the schoolbook leveling and collection system.
 * <p>
 * Players can find and collect knowledge from bookshelves around the school,
 * with locations cycling randomly to encourage exploration.
 * </p>
 */
public class SchulbuchLevels implements Listener {

    public static HashMap<Player, Integer> knowledgeAquiered = new HashMap<Player, Integer>();
    public static ArrayList<Location> bookshelfLoc = new ArrayList<Location>();
    public static int random;

    public static Location currentBookshelfLoc;

    public static void initShelfLocations() {
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), 23, 89, 190));
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), 43, 89, 191));
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), -52, 91, 175));
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), -54.0, 81.0, 194.0));
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), -50.0, 89.0, 177.0));
        bookshelfLoc.add(new Location(Bukkit.getWorld("schoolwars"), -54.0, 83.0, 172.0));
    }

    @EventHandler
    public void onBookshelfInteraction(PlayerInteractEvent e) {
        if(e.getPlayer().getInventory().getItemInMainHand().equals(Items.schulbuch1) || e.getPlayer().getInventory().getItemInMainHand().equals(Items.schulbuch2) || e.getPlayer().getInventory().getItemInMainHand().equals(Items.schulbuch3) || e.getPlayer().getInventory().getItemInMainHand().equals(Items.schulbuch4) || e.getPlayer().getInventory().getItemInMainHand().equals(Items.schulbuch5)) {
            e.setCancelled(true);
            Player p = e.getPlayer();
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                for(int i = 0; i < bookshelfLoc.size(); i++) {
                    if(e.getClickedBlock().getLocation().equals(currentBookshelfLoc)) {
                        if(knowledgeAquiered.containsKey(p)) {
                            knowledgeAquiered.put(p, knowledgeAquiered.get(p) + 1);
                            checkSchulbuchUpgrade(p, knowledgeAquiered.get(p));
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Schulbücher gesammelt: " + ChatColor.RED + knowledgeAquiered.get(p) + ChatColor.GRAY + " --"));
                            resetBookshelf();
                        }else if(!knowledgeAquiered.containsKey(p)){
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

    public static void checkSchulbuchUpgrade(Player p, int b) {
        if(b % 5 == 0) {
            if(b == 5) {
                p.getInventory().setItemInMainHand(Items.schulbuch2);
                p.sendMessage(ChatColor.DARK_GRAY + "Dein " + ChatColor.BLUE + Items.schulbuch1.getItemMeta().getDisplayName() + ChatColor.DARK_GRAY + " wurde zu einem " + ChatColor.DARK_BLUE + Items.schulbuch2.getItemMeta().getDisplayName() + ChatColor.DARK_GRAY + ".");
            }
            if(b == 10) {
                p.getInventory().setItemInMainHand(Items.schulbuch3);
                p.sendMessage(ChatColor.DARK_GRAY + "Dein " + ChatColor.DARK_BLUE + Items.schulbuch2.getItemMeta().getDisplayName() + ChatColor.DARK_GRAY + " wurde zu einem " + ChatColor.RED + Items.schulbuch3.getItemMeta().getDisplayName() + ChatColor.DARK_GRAY + ".");
            }
            if(b == 15) {
                p.getInventory().setItemInMainHand(Items.schulbuch4);
                p.sendMessage(ChatColor.DARK_GRAY + "Dein " + ChatColor.RED + Items.schulbuch3.getItemMeta().getDisplayName() + ChatColor.DARK_GRAY + " wurde zu einem " + ChatColor.GREEN + Items.schulbuch4.getItemMeta().getDisplayName() + ChatColor.DARK_GRAY + ".");
            }
            if(b == 20) {
                p.getInventory().setItemInMainHand(Items.schulbuch5);
                p.sendMessage(ChatColor.DARK_GRAY + "Dein " + ChatColor.GREEN + Items.schulbuch4.getItemMeta().getDisplayName() + ChatColor.DARK_GRAY + " wurde zu einem " + ChatColor.DARK_RED + Items.schulbuch5.getItemMeta().getDisplayName() + ChatColor.DARK_GRAY + ".");
            }
        }
    }

    @EventHandler
    public void onAusleiheUse(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getPlayer().getInventory().getItemInMainHand().equals(Items.ausleihschein)) {
                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Nächstes Schulbuch befindet sich in: " + ChatColor.RED + Area.getAreaByLocation(currentBookshelfLoc).getName() + ChatColor.GRAY + " --"));
            }
        }
    }

    public static void resetBookshelf() {
        Random rand = new Random();
        random = rand.nextInt(bookshelfLoc.size());
        currentBookshelfLoc = bookshelfLoc.get(random);
    }
}
