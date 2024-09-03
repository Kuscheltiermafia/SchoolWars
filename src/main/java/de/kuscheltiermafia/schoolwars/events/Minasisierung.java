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
import de.kuscheltiermafia.schoolwars.mechanics.PlayerStun;
import de.kuscheltiermafia.schoolwars.mechanics.ProgressBarHandler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Minasisierung implements Listener {

    public static int minasDauer = 20 * 30;

    public static void onMinasisierung(Player p) {
        for(ItemStack item : p.getInventory().getContents()) {
            try {
                if(!item.equals(null)) {
                    if (item.equals(Items.minas_flasche)) {
                        item.setType(Items.buffed_minas_flasche.getType());
                        item.setItemMeta(Items.buffed_minas_flasche.getItemMeta());
                    }
                    if (item.equals(Items.attack_stuhl)) {
                        item.setType(Items.buffed_stuhl.getType());
                        item.setItemMeta(Items.buffed_stuhl.getItemMeta());
                    }
                }
            } catch (Exception ignored){}
        }


        for(int i = 0; i < minasDauer; i++) {
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    double calcProgress = (double) finalI / minasDauer * 100;
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Autismus: " + ProgressBarHandler.progressBarsUpdate(calcProgress, ChatColor.DARK_RED) + ChatColor.GRAY + " --"));

                    if(finalI == minasDauer - 1) {
                        for(ItemStack item : p.getInventory().getContents()) {
                            if(item != null) {
                                if (item.equals(Items.buffed_minas_flasche)) {
                                    item.setItemMeta(Items.minas_flasche.getItemMeta());
                                }
                                if (item.equals(Items.buffed_stuhl)) {
                                    item.setItemMeta(Items.attack_stuhl.getItemMeta());
                                }
                            }
                        }
                    }
                }
            }.runTaskLater(SchoolWars.getPlugin(), i);
        }
    }

    @EventHandler
    public void OnStuhlHit(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player hitter = (Player) e.getDamager();
            Player hitted = (Player) e.getEntity();

            if(hitter.getInventory().getItemInMainHand().equals(Items.buffed_stuhl) && hitter.getCooldown(Material.OAK_STAIRS) < 2) {
                hitter.setCooldown(Material.OAK_STAIRS, 20 * 6);

                ParticleHandler.createParticles(hitted.getLocation().add(0, 1, 0), Particle.CRIT, 20, 0.2, true, null);
                ParticleHandler.createParticles(hitted.getLocation().add(0, 1, 0), Particle.CLOUD, 30, 0.2, true, null);
                PlayerStun.stunPlayer(hitted, 3, true);
            }
        }
    }
}
