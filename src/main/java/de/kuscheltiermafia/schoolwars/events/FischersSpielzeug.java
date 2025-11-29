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
import de.kuscheltiermafia.schoolwars.Team;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

/**
 * Handles Fischer's game item mechanics.
 * <p>
 * Manages the placement and behavior of Fischer's game pieces which
 * apply nausea effects to enemy team players in the area. Pieces expire
 * after 60 seconds or can be destroyed by breaking them 4 times.
 * </p>
 */
public class FischersSpielzeug implements Listener {

    /** Duration the game piece stays active in ticks (60 seconds). */
    int fischerDauer = 60 * 20;

    /** Tracks game pieces by location with their hit count. */
    public static HashMap<Location, Integer> fischers_spielzeuge = new HashMap<>();
    
    /** Tracks which team placed each game piece. */
    public static HashMap<Location, Team> fischers_team = new HashMap<>();

    /** Tracks player positions for Fischer tracking (not currently used). */
    public static HashMap<Player, Entity> fischers_position = new HashMap<>();

    /**
     * Handles placing Fischer's game piece in the world.
     * Creates an area effect that applies nausea to enemy team players.
     */
    @EventHandler
    public void onFischerPlace(BlockPlaceEvent e) {
        if(e.getItemInHand().equals(Items.fischers_spiel)) {

            // ========== Validate game state ==========
            if(!SchoolWars.gameStarted) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "Das Spiel hat noch nicht begonnen!");
                return;
            }

            // ========== Register game piece ==========
            e.setCancelled(false);
            fischers_spielzeuge.put(e.getBlockPlaced().getLocation(), 0);
            fischers_team.put(e.getBlockPlaced().getLocation(), playerMirror.get(e.getPlayer().getName()).getTeam());
            
            // ========== Active effect loop ==========
            for(int i = 0; i < fischerDauer; i++) {
                int finalI = i;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        // Check for nearby players
                        for(Entity en : e.getBlockPlaced().getWorld().getNearbyEntities(e.getBlockPlaced().getLocation(), 4, 4, 4)) {

                            if(en instanceof Player p) {

                                // ========== Particle indicator (team colored) ==========
                                if(finalI % 5 == 0) {
                                    if (playerMirror.get(e.getPlayer().getName()).getTeam().equals(Team.SPRACHLER)) {
                                        Particle.DustOptions color = new Particle.DustOptions(Color.fromRGB(255, 170, 0), 5);
                                        p.spawnParticle(Particle.NOTE, e.getBlockPlaced().getLocation().add(0.5, 1.1, 0.5), 1, color);
                                    } else if (playerMirror.get(e.getPlayer().getName()).getTeam().equals(Team.NWS)) {
                                        Particle.DustOptions color = new Particle.DustOptions(Color.fromRGB(85, 255, 85), 5);
                                        p.spawnParticle(Particle.NOTE, e.getBlockPlaced().getLocation().add(0.5, 1.1, 0.5), 1, color);
                                    } else {
                                        Particle.DustOptions color = new Particle.DustOptions(Color.fromRGB(170, 0, 0), 5);
                                        p.spawnParticle(Particle.NOTE, e.getBlockPlaced().getLocation().add(0.5, 1.1, 0.5), 1, color);
                                    }
                                }

                                // ========== Apply nausea to enemy players ==========
                                if(!playerMirror.get(p.getName()).getTeam().equals(fischers_team.get(e.getBlockPlaced().getLocation())) && fischers_team.containsKey(e.getBlockPlaced().getLocation())) {
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 4 * 20, 1, false, false, false));
                                }
                            }
                        }
                    }
                }.runTaskLater(SchoolWars.plugin, i);
            }
            
            // ========== Auto-remove after duration ==========
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(fischers_spielzeuge.containsKey(e.getBlockPlaced().getLocation())) {
                        e.getBlockPlaced().setType(Material.AIR);
                        ParticleHandler.createParticles(e.getBlock().getLocation().add(0.5, 1, 0.5), Particle.CAMPFIRE_COSY_SMOKE, 4, 0, true, null);
                        fischers_spielzeuge.remove(e.getBlock().getLocation());
                        fischers_team.remove(e.getBlock().getLocation());
                    }
                }
            }.runTaskLater(SchoolWars.plugin, fischerDauer);
        }
    }

    /**
     * Handles destroying Fischer's game piece.
     * Requires 4 hits to fully destroy; each hit shows particle feedback.
     */
    @EventHandler
    public void onFischerKill(BlockBreakEvent e) {
        if(fischers_spielzeuge.containsKey(e.getBlock().getLocation())) {
            // Increment hit counter (needs 4 hits to destroy)
            if(fischers_spielzeuge.get(e.getBlock().getLocation()) < 3 ) {
                fischers_spielzeuge.put(e.getBlock().getLocation(), fischers_spielzeuge.get(e.getBlock().getLocation()) + 1);
                ParticleHandler.createParticles(e.getBlock().getLocation().add(0.5, 1.1, 0.5), Particle.LAVA, 5, 0, true, null);
            }else{
                // Fourth hit - destroy the piece
                ParticleHandler.createParticles(e.getBlock().getLocation(), Particle.EXPLOSION, 3, 0, true, null);
                e.getBlock().setType(Material.AIR);
                fischers_spielzeuge.remove(e.getBlock().getLocation());
                fischers_team.remove(e.getBlock().getLocation());
            }
        }
    }

    /*
    @EventHandler
    public void onPeilsender(PlayerInteractEntityEvent e) {
        if(e.getRightClicked().getCustomName().equals("Herr Fischer") && e.getPlayer().getInventory().getItemInMainHand().equals(Items.peilsender)) {
            Player p = e.getPlayer();
            p.getInventory().setItemInMainHand(Items.bound_peilsender);
            fischers_position.put(p, e.getRightClicked());
        }
    }

    @EventHandler
    public void onPeilsender(PlayerInteractEvent e) {
        if(e.getPlayer().getInventory().getItemInMainHand().equals(Items.bound_peilsender)) {
            Player p = e.getPlayer();
            p.setCooldown(Material.COMPASS, 15 * 20);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY "-- " + ChatColor.DARK_RED + "Herr Fischer befindet sich "));
        }
    }
    */
}
