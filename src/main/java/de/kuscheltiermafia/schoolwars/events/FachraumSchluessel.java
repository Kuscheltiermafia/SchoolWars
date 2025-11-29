package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

/**
 * Handles special cabinet key (Fachraum Schlüssel) interactions.
 * <p>
 * Allows players to use special keys to unlock and access
 * special cabinets (shulker boxes) containing valuable items.
 * </p>
 */
public class FachraumSchluessel implements Listener {

    /**
     * Handles player interaction with special cabinets using the cabinet key.
     */
    @EventHandler
    public void onFachraumSchluessel (PlayerInteractEvent e){
        try{
            // Check if player is using the cabinet key on a blue shulker box
            if (e.getItem().equals(Items.fachraum_schrank_schluessel) && e.getClickedBlock().getType() == Material.BLUE_SHULKER_BOX) {
                e.setCancelled(true);
                
                // Verify the cabinet is at the correct location
                if (e.getClickedBlock().getLocation().getX() == -5 && e.getClickedBlock().getLocation().getY() == 81 && e.getClickedBlock().getLocation().getZ() == 187) {
                    // Open the cabinet inventory and consume the key
                    schrankInventory(e.getPlayer());
                    e.getPlayer().getInventory().remove(Items.fachraum_schrank_schluessel);
                } else {
                    // Wrong cabinet location
                    e.getPlayer().sendMessage("§cDieser Schrank ist nicht für dich zugänglich!");
                }
            }
        }catch (Exception ignored) {}
    }


    /**
     * Creates and opens the special cabinet inventory for the player.
     */
    private void schrankInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, "§4Fachraum Schrank");
        inventory.setItem(13, Items.versuch);
        player.openInventory(inventory);
    }

}
