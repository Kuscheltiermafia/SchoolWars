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

package de.kuscheltiermafia.schoolwars.lehrer;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.commands.ItemList;
import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class SekretariatStundenplan implements Listener {

    @EventHandler
    public void onStundenplanView(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getLocation().equals(new Location(SchoolWars.world, -49.0, 88.0, 173.0))) {
            e.setCancelled(true);

            Inventory stundenplan = Bukkit.createInventory(null, 9*6, "§4Momentaner Stundenplan");

            stundenplan.setItem(0, Items.spacer);
            stundenplan.setItem(1, Items.spacer);
            stundenplan.setItem(2, Items.spacer);
            for(int i = 0; i < ItemList.spacers.length; i++) {
                stundenplan.setItem(ItemList.spacers[i], Items.spacer);
            }

            for(Lehrer lehrer : Stundenplan.studenplan.keySet()) {
                if(lehrer.isMale) {
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + Stundenplan.studenplan.get(lehrer).name);
                    stundenplan.addItem(Items.createItem(Material.OAK_SIGN, ChatColor.WHITE + "Herr " + lehrer.name, 1, 1, lore, false, false, false));
                }else{
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + Stundenplan.studenplan.get(lehrer).name);
                    stundenplan.addItem(Items.createItem(Material.OAK_SIGN, ChatColor.WHITE + "Frau " + lehrer.name, 1, 1, lore, false, false, false));
                }
            }
            e.getPlayer().openInventory(stundenplan);
        }
    }

    @EventHandler
    public void onStundenplanInteraction(InventoryClickEvent e) {
        if(e.getWhoClicked().getOpenInventory().getTitle().equals("§4Momentaner Stundenplan")) {
            e.setCancelled(true);
        }
    }
}
