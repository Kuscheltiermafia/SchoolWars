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

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.ParticleHandler;
import de.kuscheltiermafia.schoolwars.mechanics.PlayerStun;
import de.kuscheltiermafia.schoolwars.mechanics.ProgressBarHandler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

/**
 * Handles the "Minasisierung" (autism) mechanics in SchoolWars.
 * This system allows players to sacrifice candles at a specific location
 * to gain temporary buffs on their items, but with the side effect of 
 * acquiring a temporary "autism" status with visual effects.
 */
public class Minasisierung implements Listener {

    /** Tracks the current autism level for each player (0-4) */
    private static HashMap<Player, Integer> currentAutism = new HashMap<>();

    /** Duration of the autism effect in ticks (30 seconds * 20 ticks/second) */
    public static int minasDauer = 20 * 30;

    /**
     * Applies the "Minasisierung" effect to a player.
     * Buffs their minas bottle and attack chair items, then displays a progress bar
     * showing the remaining duration of the effect. Items are reverted when the effect ends.
     * 
     * @param p The player to apply the effect to
     */
    public static void onMinasisierung(Player p) {
        // Buff the player's items by upgrading them to enhanced versions
        for(ItemStack item : p.getInventory().getContents()) {
            if(item != null) {
                // Upgrade minas bottle to buffed version
                if (item.equals(Items.minas_flasche)) {
                    item.setType(Items.buffed_minas_flasche.getType());
                    item.setItemMeta(Items.buffed_minas_flasche.getItemMeta());
                }
                // Upgrade attack chair to buffed version
                if (item.equals(Items.attack_stuhl)) {
                    item.setType(Items.buffed_stuhl.getType());
                    item.setItemMeta(Items.buffed_stuhl.getItemMeta());
                }
            }
        }

        // Display progress bar and revert items when effect expires
        for(int i = 0; i < minasDauer; i++) {
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    // Calculate and display progress bar showing remaining autism effect time
                    double calcProgress = (double) finalI / minasDauer * 100;
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Autismus: " + ProgressBarHandler.progressBarsUpdate(calcProgress, ChatColor.DARK_RED) + ChatColor.GRAY + " --"));

                    // When effect expires, revert items back to normal versions
                    if(finalI == minasDauer - 1) {
                        for(ItemStack item : p.getInventory().getContents()) {
                            if(item != null) {
                                // Revert buffed minas bottle back to normal
                                if (item.equals(Items.buffed_minas_flasche)) {
                                    item.setItemMeta(Items.minas_flasche.getItemMeta());
                                }
                                // Revert buffed chair back to normal
                                if (item.equals(Items.buffed_stuhl)) {
                                    item.setItemMeta(Items.attack_stuhl.getItemMeta());
                                }
                                // Remove player from autism tracking
                                currentAutism.remove(p);
                            }
                        }
                    }
                }
            }.runTaskLater(SchoolWars.getPlugin(), i);
        }
    }

    /**
     * Handles the sacrifice ritual at the specific altar location.
     * Players can right-click with candles to progress through the ritual.
     * After 3 candles are sacrificed, the player gains the Minasisierung effect.
     * 
     * @param e The PlayerInteractEvent triggered when interacting with the altar block
     */
    @EventHandler
    public void onStartSacrifice(PlayerInteractEvent e) {
        // Check if player right-clicked on the specific altar block at coordinates (-63, 80, 197)
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getLocation().equals(new Location(SchoolWars.WORLD, -63, 80, 197))) {
            Player p = e.getPlayer();

            try {
                // Check if player is holding a candle for the sacrifice
                if (e.getItem().equals(Items.kerze)) {
                    e.setCancelled(true);
                    
                    // Initialize autism level if player hasn't used the altar before
                    if(!currentAutism.containsKey(p)) {
                        currentAutism.put(p, 0);
                    }
                    
                    // Allow up to 3 candle sacrifices
                    if (currentAutism.get(p) < 3) {
                        // Increment sacrifice count and consume one candle
                        currentAutism.put(p, currentAutism.get(p) + 1);
                        p.getInventory().removeItem(new ItemStack(Material.CANDLE, 1));
                        
                        // Show progress towards completing the ritual
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- " + currentAutism.get(p) + "/3 --"));
                    }
                    
                    // When 3 candles have been sacrificed, trigger the Minasisierung effect
                    if (currentAutism.get(p) == 3) {
                        // Create dramatic particle effect at the altar
                        ParticleHandler.createParticleCircle(p.getLocation().add(0, 1, 0), Particle.END_ROD, 1, 45);

                        // Apply the Minasisierung effect after a 2-second delay
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                onMinasisierung(p);
                                // Set autism level to 4 to indicate effect is active
                                currentAutism.put(p, 4);
                            }
                        }.runTaskLater(SchoolWars.getPlugin(), 20 * 2);
                    }
                }
            }catch (Exception ignored) {}
        }
    }
}
