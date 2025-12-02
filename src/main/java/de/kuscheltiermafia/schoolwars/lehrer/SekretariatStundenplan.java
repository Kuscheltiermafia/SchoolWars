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

package de.kuscheltiermafia.schoolwars.lehrer;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.commands.ItemList;
import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * Provides an in-game GUI for viewing the current class schedule.
 * <p>
 * Players can access this schedule from a specific location in the secretariat
 * to see which teachers are in which rooms. Supports pagination for large
 * teacher lists.
 * </p>
 */
public class SekretariatStundenplan implements Listener {

    private static int page = 0;
    private static int[] slotsForLehrer = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    private static ArrayList<Player> viewingPlayers = new ArrayList<>();

    @EventHandler
    public void onStundenplanView(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getLocation().getX() == -49 && e.getClickedBlock().getLocation().getY() == 88 && e.getClickedBlock().getLocation().getZ() ==  173 && SchoolWars.gameStarted) {
            e.setCancelled(true);

            Inventory stundenplan = createStundenplan(page);

            viewingPlayers.add(e.getPlayer());
            e.getPlayer().openInventory(stundenplan);
        }
    }

    @EventHandler
    public void onStundenplanInteraction(InventoryClickEvent e) {
        if(e.getWhoClicked().getOpenInventory().getTitle().equals("§4Momentaner Stundenplan")) {
            e.setCancelled(true);
            try {
                if (e.getCurrentItem().equals(Items.page_down)) {
                    Inventory stundenplan = createStundenplan(page - 1);

                    for (Player player : viewingPlayers) {
                        if (player.getOpenInventory().getTitle().equals("§4Momentaner Stundenplan")) {
                            player.openInventory(stundenplan);
                        } else {
                            viewingPlayers.remove(player);
                        }
                    }
                }else if (e.getCurrentItem().equals(Items.page_up)) {
                    Inventory stundenplan = createStundenplan(page + 1);
                    for (Player player : viewingPlayers) {
                        if (player.getOpenInventory().getTitle().equals("§4Momentaner Stundenplan")) {
                            player.openInventory(stundenplan);
                        } else {
                            viewingPlayers.remove(player);
                        }
                    }
                }
            }catch (NullPointerException ignored) {
            }
        }
    }
    
    public static Inventory createStundenplan(int tempPage) {

        page = tempPage;
        Inventory stundenplan = Bukkit.createInventory(null, 9*6, "§4Momentaner Stundenplan");

        if(page > 0) {
            stundenplan.setItem(0, Items.page_down);
        }else{
            stundenplan.setItem(0, Items.no_page_down);
        }

        if(page < Math.ceil(Stundenplan.studenplan.size() / 28.0) - 1) {
            stundenplan.setItem(2, Items.page_up);
        }else{
            stundenplan.setItem(2, Items.no_page_up);
        }

        for(int i = 0; i < ItemList.spacers.length; i++) {
            stundenplan.setItem(ItemList.spacers[i], Items.spacer);
        }

        for(int i = 0; i < 28; i++) {

            int index = i + (page * 28);

            if(index < Stundenplan.studenplan.size()) {
                Lehrer lehrer = (Lehrer) Stundenplan.studenplan.keySet().toArray()[index];
                Area area = Stundenplan.studenplan.get(lehrer);

                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + area.name);

                if(lehrer.isMale) {
                    stundenplan.setItem(slotsForLehrer[i], Items.createItem(Material.OAK_SIGN, ChatColor.WHITE + "Herr " + lehrer.name, 1, 1, lore, false, false, false));
                } else {
                    stundenplan.setItem(slotsForLehrer[i], Items.createItem(Material.OAK_SIGN, ChatColor.WHITE + "Frau " + lehrer.name, 1, 1, lore, false, false, false));
                }
            }
        }

        stundenplan.setItem(1, Items.createItem(Material.PAPER, ChatColor.DARK_RED + "" + returnLetter(stundenplan), 20, 1, null, false, false, false));

        return stundenplan;
    }
    
    private static String returnLetter(Inventory inv) {
        Character firstLetter = null;
        
        for(int i = 0; i < slotsForLehrer.length; i++) {
            if(inv.getItem(slotsForLehrer[slotsForLehrer.length - (i + 1)]) != null) {
                firstLetter = inv.getItem(slotsForLehrer[slotsForLehrer.length - i - 1]).getItemMeta().getDisplayName().charAt(7);
                break;
            }
        }
        
        return inv.getItem(10).getItemMeta().getDisplayName().charAt(7) + " bis " + firstLetter;
    }
}
