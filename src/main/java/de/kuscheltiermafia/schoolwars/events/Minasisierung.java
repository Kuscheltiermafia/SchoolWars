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
import de.kuscheltiermafia.schoolwars.config.TimeConfig;
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
 * Handles the "Minasisierung" effect (special ability).
 * <p>
 * When activated through a candle sacrifice ritual, this effect
 * grants the player increased strength by upgrading their weapons
 * (e.g., Minas bottle and attack chair become buffed versions).
 * </p>
 */
public class Minasisierung implements Listener {

    /** Tracks the current sacrifice progress for each player. */
    private static HashMap<Player, Integer> currentAutism = new HashMap<>();

    /** Duration of the Minasisierung effect in ticks. Configurable via times.properties. */
    public static int minasDauer = TimeConfig.getTicks("minasisierung.duration", 20 * 30);

    /**
     * Activates the Minasisierung effect for a player.
     * Upgrades weapons to buffed versions and starts the effect timer.
     *
     * @param p the player to apply the effect to
     */
    public static void onMinasisierung(Player p) {
        // ========== Upgrade weapons to buffed versions ==========
        for(ItemStack item : p.getInventory().getContents()) {
            if(item != null) {
                // Upgrade Minas bottle to buffed version
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

        // ========== Effect duration timer with progress bar ==========
        for(int i = 0; i < minasDauer; i++) {
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    // Show progress bar in action bar
                    double calcProgress = (double) finalI / minasDauer * 100;
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Autismus: " + ProgressBarHandler.progressBarsUpdate(calcProgress, ChatColor.DARK_RED) + ChatColor.GRAY + " --"));

                    // When effect expires, revert weapons to normal versions
                    if(finalI == minasDauer - 1) {
                        for(ItemStack item : p.getInventory().getContents()) {
                            if(item != null) {
                                if (item.equals(Items.buffed_minas_flasche)) {
                                    item.setItemMeta(Items.minas_flasche.getItemMeta());
                                }
                                if (item.equals(Items.buffed_stuhl)) {
                                    item.setItemMeta(Items.attack_stuhl.getItemMeta());
                                }
                                currentAutism.remove(p);
                            }
                        }
                    }
                }
            }.runTaskLater(SchoolWars.getPlugin(), i);
        }
    }

    /**
     * Handles the candle sacrifice ritual to activate the Minasisierung effect.
     * Player must sacrifice 3 candles at the ritual location.
     */
    @EventHandler
    public void onStartSacrifice(PlayerInteractEvent e) {
        // Check if player right-clicks the ritual altar location
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getLocation().equals(new Location(SchoolWars.WORLD, -63, 80, 197))) {
            Player p = e.getPlayer();

            try {
                if (e.getItem().equals(Items.kerze)) {
                    e.setCancelled(true);
                    p.getInventory().removeItem(Items.kerze);
                    
                    // Initialize sacrifice counter if needed
                    if(!currentAutism.containsKey(p)) {
                        currentAutism.put(p, 0);
                    }
                    
                    // ========== Sacrifice progress (0-2 candles) ==========
                    if (currentAutism.get(p) < 3) {
                        currentAutism.put(p, currentAutism.get(p) + 1);
                        p.getInventory().removeItem(new ItemStack(Material.CANDLE, 1));
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- " + currentAutism.get(p) + "/3 --"));
                    }
                    
                    // ========== Complete sacrifice (3 candles) ==========
                    if (currentAutism.get(p) == 3) {
                        // Visual effect for ritual completion
                        ParticleHandler.createParticleCircle(p.getLocation().add(0, 1, 0), Particle.END_ROD, 1, 45);

                        // Stun player during activation, then apply effect
                        PlayerStun.stunPlayer(p, 2, false);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                onMinasisierung(p);
                                currentAutism.put(p, 4);
                            }
                        }.runTaskLater(SchoolWars.getPlugin(), TimeConfig.getTicks("minasisierung.activation_delay", 20 * 2));
                    }
                }
            }catch (Exception ignored) {}
        }
    }
}
