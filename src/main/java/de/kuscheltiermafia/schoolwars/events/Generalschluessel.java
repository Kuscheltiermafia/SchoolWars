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
import de.kuscheltiermafia.schoolwars.config.ProbabilityConfig;
import de.kuscheltiermafia.schoolwars.config.TimeConfig;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.ParticleHandler;
import io.github.realMorgon.sunriseLib.Message;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;

import static de.kuscheltiermafia.schoolwars.SchoolWars.WORLD;
import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

/**
 * Handles master key (Generalschlüssel) mechanics.
 * <p>
 * Allows players to use the master key to access restricted areas
 * with a risk of triggering secretariat staff encounters.
 * </p>
 */
public class Generalschluessel implements Listener {

    /**
     * Spawns a hostile secretariat staff member at the given location.
     * The staff member has a randomly chosen name and will attack intruders.
     *
     * @param loc the location to spawn the staff member
     */
    void summonSekritaerin(Location loc) {
        // Spawn hostile vindicator as secretariat staff
        Vindicator sekritaerin = WORLD.spawn(loc.add(0.5, 0, 0.5), Vindicator.class);

        // Randomly select a staff name
        ArrayList<String> names = new ArrayList<>();
        names.add("Frau Wacker");
        names.add("Frau Blümel");
        names.add("Frau Schranck-Teichmann");
        Collections.shuffle(names);

        // Configure entity properties
        sekritaerin.setCustomName(names.getFirst());
        sekritaerin.setCustomNameVisible(true);
        sekritaerin.setCanPickupItems(false);
        sekritaerin.setRemoveWhenFarAway(false);
        sekritaerin.setLootTable(null);

        // Visual effects for dramatic appearance
        ParticleHandler.createParticles(loc, Particle.EXPLOSION, 2, 0, true, null);
        ParticleHandler.createParticleCircle(loc, Particle.ANGRY_VILLAGER, 1.2, 20);
    }


    /**
     * Handles using the master key on iron doors.
     * Opens the door temporarily and may trigger secretariat encounters.
     */
    @EventHandler
    public void onGeneralSchluessel(PlayerInteractEvent e) {

        try {

            Block block = e.getClickedBlock();

            // ========== Validate key usage on iron door ==========
            if (e.getItem().equals(Items.general_schluessel) && block.getType() == Material.IRON_DOOR) {
                e.setCancelled(true);

                Door door = (Door) block.getBlockData();
                if(door.isOpen() == false) {

                    // ========== Open door temporarily ==========
                    door.setOpen(true);
                    block.setBlockData(door);

                    // Auto-close door after configurable duration
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            door.setOpen(false);
                            block.setBlockData(door);
                        }
                    }.runTaskLater(SchoolWars.getPlugin(), TimeConfig.getTicks("generalschluessel.door_duration", 20 * 5));

                    // ========== Risk check for secretariat encounter ==========
                    // Increase team's secretariat risk
                    playerMirror.get(e.getPlayer().getName()).getTeam().sekiRisk += ProbabilityConfig.getProbability("generalschluessel.risk_increase", 0.05);

                    // Check if seki appears based on risk probability
                    if (Math.random() <= playerMirror.get(e.getPlayer().getName()).getTeam().sekiRisk) {
                        // Check for message variant (10% chance for alternate message)
                        if (Math.random() < ProbabilityConfig.getProbability("message.generalschluessel_variant_chance", 0.1)) {
                            Message.sendInArea("§4Die Kollegin von der Seki-Frau hat dich erwischt!", block.getLocation(), 5, 3, 5);
                        } else {
                            Message.sendInArea("§4Die Seki-Frau hat dich erwischt!", block.getLocation(), 5, 3, 5);
                        }
                        // Spawn hostile staff member
                        summonSekritaerin(block.getLocation());
                    }

                }else{
                    e.getPlayer().sendActionBar("Dein Schlüssel hat nicht funktioniert!");
                }
            }
        }catch (Exception ignored){}
    }

    /**
     * Handles picking up the master key from item frames.
     */
    @EventHandler
    public void onClickItemframe(PlayerInteractEntityEvent e){

        if (e.getRightClicked() instanceof ItemFrame) {

            Player p = e.getPlayer();

            // Abort if player is in creative mode
            if (p.getGameMode() == GameMode.CREATIVE) return;

            // Give master key from item frame
            ItemFrame itemFrame = (ItemFrame) e.getRightClicked();

            if (itemFrame.getItem().equals(Items.general_schluessel)) {
                e.setCancelled(true);
                p.getInventory().addItem(new ItemStack(Items.general_schluessel));
            }
        }
    }
}
