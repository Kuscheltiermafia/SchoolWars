package de.kuscheltiermafia.schoolwars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class interactionCancel implements Listener {

    @EventHandler
    public void clickSpacer(InventoryClickEvent e) {
        if(e.getCurrentItem().getItemMeta().getCustomModelData() == 2) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void clickSpacer(PlayerInteractEvent e) {
        if(e.getItem().getItemMeta().getCustomModelData() == 1) {
            e.setCancelled(true);
        }
    }
}
