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
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Minasisierung implements Listener {

    public static int minasDauer = 20 * 30;

    public static void onMinasisierung(Player p) {
        for(ItemStack item : p.getInventory().getContents()) {

            if(item != null) {
                if (item.equals(Items.minas_flasche)) {
                    item.setType(Items.buffed_minas_flasche.getType());
                    item.setItemMeta(Items.buffed_minas_flasche.getItemMeta());
                }
                if (item.equals(Items.attack_stuhl)) {
                    item.setType(Items.buffed_stuhl.getType());
                    item.setItemMeta(Items.buffed_stuhl.getItemMeta());
                }
            }
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
}
