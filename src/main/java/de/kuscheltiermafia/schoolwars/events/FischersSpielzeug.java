package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.ParticleHandler;
import de.kuscheltiermafia.schoolwars.teams.Team;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class FischersSpielzeug implements Listener {

    int fischerDauer = 60 * 20;

    public static HashMap<Location, Integer> fischers_spielzeuge = new HashMap<>();
    public static HashMap<Location, Team> fischers_team = new HashMap<>();

    @EventHandler
    public void onFischerPlace(BlockPlaceEvent e) {
        if(e.getItemInHand().equals(Items.fischers_spiel)) {
            e.setCancelled(false);
            fischers_spielzeuge.put(e.getBlockPlaced().getLocation(), 0);
            fischers_team.put(e.getBlockPlaced().getLocation(), SchoolWars.playerMirror.get(e.getPlayer().getName()).getTeam());
            for(int i = 0; i < fischerDauer; i++) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for(Entity en : e.getPlayer().getNearbyEntities(4, 4, 4)) {
                            if(en instanceof Player) {
                                Player p = (Player) en;
                                if(!SchoolWars.playerMirror.get(en.getName()).getTeam().equals(fischers_team.get(e.getBlockPlaced().getLocation()))) {
                                    ((Player) en).addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 4, 1, false, false, false));
                                }
                            }
                        }
                    }
                }.runTaskLater(SchoolWars.plugin, i);
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(fischers_spielzeuge.containsKey(e.getBlockPlaced().getLocation())) {
                        e.getBlockPlaced().setType(Material.AIR);
                        ParticleHandler.createParticles(e.getBlock().getLocation().add(0.5, 1, 0.5), Particle.CAMPFIRE_COSY_SMOKE, 4, 0, true, null);
                        fischers_spielzeuge.remove(e.getBlockPlaced().getLocation());
                        fischers_team.remove(e.getBlock().getLocation());
                    }
                }
            }.runTaskLater(SchoolWars.plugin, fischerDauer);
        }
    }

    @EventHandler
    public void onFischerKill(BlockBreakEvent e) {
        if(fischers_spielzeuge.containsKey(e.getBlock().getLocation())) {
            if(fischers_spielzeuge.get(e.getBlock().getLocation()) < 3 ) {
                fischers_spielzeuge.put(e.getBlock().getLocation(), fischers_spielzeuge.get(e.getBlock().getLocation()) + 1);
                ParticleHandler.createParticles(e.getBlock().getLocation().add(0.5, 1.1, 0.5), Particle.CRIT, 20, 0, true, null);
            }else{
                ParticleHandler.createParticles(e.getBlock().getLocation(), Particle.EXPLOSION, 3, 0, true, null);
                e.getBlock().setType(Material.AIR);
                fischers_spielzeuge.remove(e.getBlock().getLocation());
                fischers_team.remove(e.getBlock().getLocation());
            }
        }
    }
}
