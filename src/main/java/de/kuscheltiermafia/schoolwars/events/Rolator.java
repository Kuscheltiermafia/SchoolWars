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

package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.ParticleHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;


public class Rolator implements Listener {

    private boolean BukkitRunnablesFicktEuch;

    @EventHandler
    public void onRolatorRide(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        Location loc = p.getLocation();

        if(item.equals(Items.rollator)) {
            if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

                e.setCancelled(true);
                p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                BukkitRunnablesFicktEuch = false;

                for (int i = 0; i < 20 * 3; i++) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {

                            if (!loc.getBlock().getType().equals(Material.AIR) || p.isSneaking()) {
                                if(!BukkitRunnablesFicktEuch) {
                                    BukkitRunnablesFicktEuch = true;
                                    ParticleHandler.createParticles(loc, Particle.EXPLOSION, 20, 0, true, null);
                                }
                            }

                            if(loc.getBlock().getType().equals(Material.AIR) || !p.isSneaking()) {
                                if(!BukkitRunnablesFicktEuch) {
                                    p.teleport(loc);
                                    for(Entity e : p.getNearbyEntities(1, 1, 1)) {
                                        if(e instanceof Player) {
                                            Player t = (Player) e;
                                            ParticleHandler.createParticles(t.getLocation(), Particle.CRIT, 10, 0, true, null);
                                            t.damage(15);
                                        }
                                    }
                                    ParticleHandler.createParticles(loc, Particle.CLOUD, 10, 0, true, null);
                                    loc.add(loc.getDirection().getX() / 1.3, 0, loc.getDirection().getZ() / 1.3);
                                }
                            }
                        }
                    }.runTaskLater(SchoolWars.getPlugin(), i);
                }
            }
        }
    }
}
