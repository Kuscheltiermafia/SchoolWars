package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.ParticleHandler;
import de.kuscheltiermafia.schoolwars.player_mirror.PlayerMirror;
import de.kuscheltiermafia.schoolwars.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class FischersSpielzeug implements Listener {

    public static HashMap<Shulker, BlockDisplay> fischers_spielzeuge = new HashMap<>();
    public static HashMap<BlockDisplay, Team> fischers_team = new HashMap<>();

    @EventHandler
    public void onFischerPlace(BlockPlaceEvent e) {
        if(e.getPlayer().getInventory().getItemInMainHand().equals(Items.fischers_spiel)) {
            Player p = e.getPlayer();
            e.setCancelled(true);
            BlockDisplay box = p.getWorld().spawn(e.getBlockPlaced().getLocation(), BlockDisplay.class);
            Shulker hitbox = p.getWorld().spawn(e.getBlockPlaced().getLocation(), Shulker.class);
            hitbox.setAI(false);
            hitbox.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
            hitbox.setSilent(true);
            hitbox.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(12);
            box.setBlock(Bukkit.createBlockData(Material.JUKEBOX));
            box.setDisplayHeight(1f);
            box.setDisplayWidth(1f);

            fischers_spielzeuge.put(hitbox, box);
            fischers_team.put(box, SchoolWars.playerMirror.get(p.getName()).getTeam());

            for(int i = 0; i < 20 * 60 * 2; i++) {
                int finalI = i;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ParticleHandler.createParticles(box.getLocation(), Particle.NOTE, 2, 0, true, null);
                        for(Entity e : box.getWorld().getNearbyEntities(box.getLocation(), 8, 8, 8)) {
                            if(e instanceof Player) {
                                Player a = (Player) e;
                                if(!fischers_team.get(box).equals(SchoolWars.playerMirror.get(a).getTeam())) {
                                    a.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 3, 1, false, false));
                                    a.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 3, 1, false, false));
                                }
                            }
                        }
                    }
                }.runTaskLater(SchoolWars.getPlugin(), i);
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        hitbox.remove();
                        box.remove();
                        fischers_team.remove(box);
                        fischers_spielzeuge.remove(hitbox);
                    } catch (Exception ignored) {}
                }
            }.runTaskLater(SchoolWars.getPlugin(), 20 * 60 * 2);
        }
    }

    @EventHandler
    public void onFischerKill(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Shulker && e.getDamager() instanceof Player) {
            if(((Shulker) e.getEntity()).getHealth() == 0) {
                if(fischers_spielzeuge.containsKey(e.getEntity())) {
                    e.setCancelled(true);
                    fischers_spielzeuge.get(e.getEntity()).remove();
                    Item item = e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), Items.fischers_spiel);
                    e.getEntity().remove();
                }
            }
        }
    }
}
