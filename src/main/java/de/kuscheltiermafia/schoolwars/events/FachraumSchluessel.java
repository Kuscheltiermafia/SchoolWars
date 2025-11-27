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
 * Handles classroom key (Fachraum Schlüssel) interactions.
 * <p>
 * Allows players to use special keys to access locked classrooms
 * or storage areas in the school.
 * </p>
 */
public class FachraumSchluessel implements Listener {

    @EventHandler
    public void onFachraumSchluessel (PlayerInteractEvent e){
        try{
            if (e.getItem().equals(Items.fachraum_schrank_schluessel) && e.getClickedBlock().getType() == Material.BLUE_SHULKER_BOX) {
                e.setCancelled(true);
                if (e.getClickedBlock().getLocation().getX() == -5 && e.getClickedBlock().getLocation().getY() == 81 && e.getClickedBlock().getLocation().getZ() == 187) {
                    schrankInventory(e.getPlayer());
                    e.getPlayer().getInventory().remove(Items.fachraum_schrank_schluessel);
                } else {
                    e.getPlayer().sendMessage("§cDieser Schrank ist nicht für dich zugänglich!");
                }
            }
        }catch (Exception ignored) {}
    }


    private void schrankInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, "§4Fachraum Schrank");
        inventory.setItem(13, Items.versuch);
        player.openInventory(inventory);
    }

}
