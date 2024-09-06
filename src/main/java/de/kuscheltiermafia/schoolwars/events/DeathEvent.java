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

import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.Ranzen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static de.kuscheltiermafia.schoolwars.SchoolWars.*;
import static de.kuscheltiermafia.schoolwars.mechanics.RevivePlayer.playerBatMap;

public class DeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(EntityDamageEvent e){

        if (e.getEntity() instanceof Player && e.getFinalDamage() >= ((Player) e.getEntity()).getHealth()) {

            e.setCancelled(true);

            Player p = (Player) e.getEntity();
            assert p != null;
            String playerName = p.getName();

            String killerName = "";


//destroy Ranzen
            if (nws.mitglieder.contains(p.getName())) {
                playerName = ChatColor.GREEN + "[NWS] " + p.getName();
                if(p.getKiller() != null && p.getInventory().contains(Items.nws_ranzen)) {
                    Ranzen.destroyRanzen(p.getKiller(), nws.teamName, p.getLocation());
                    p.getInventory().remove(new ItemStack(Items.nws_ranzen));
                }
            } else if (sportler.mitglieder.contains(p.getName())) {
                playerName = ChatColor.DARK_RED + "[Sport] " + p.getName();
                if(p.getKiller() != null && p.getInventory().contains(Items.sport_ranzen)) {
                    Ranzen.destroyRanzen(p.getKiller(), sportler.teamName, p.getLocation());
                    p.getInventory().remove(new ItemStack(Items.sport_ranzen));
                }

            } else if (sprachler.mitglieder.contains(p.getName())) {
                playerName = ChatColor.GOLD + "[Sprache] " + p.getName();
                if(p.getKiller() != null && p.getInventory().contains(Items.sprach_ranzen)) {
                    Ranzen.destroyRanzen(p.getKiller(), sprachler.teamName, p.getLocation());
                    p.getInventory().remove(new ItemStack(Items.sprach_ranzen));
                }

            }

//Ready Killers Name for message
            if (p.getKiller() != null) {
                Player killer = p.getKiller();
                killerName = killer.getName();

                if (nws.mitglieder.contains(killer.getName())) {
                    killerName = ChatColor.GREEN + "[NWS] " + killer.getName();
                } else if (sportler.mitglieder.contains(killer.getName())) {
                    killerName = ChatColor.DARK_RED + "[Sport] " + killer.getName();
                } else if (sprachler.mitglieder.contains(killer.getName())) {
                    killerName = ChatColor.GOLD + "[Sprache] " + killer.getName();
                }
            }

//send death message
            if (p.getKiller() == null) {
                Bukkit.broadcastMessage(playerName + ChatColor.GRAY + " hat auf natürliche Weise sein Ende gefunden.");
            } else {
                Bukkit.broadcastMessage(playerName + ChatColor.GRAY + " wurde von " + killerName + ChatColor.GRAY + " besiegt.");
            }

///set player to dead state
            p.setHealth(20);
            p.sendTitle("§4Du wurdest besiegt!", "Dein Team muss dich wiederbeleben.", 10, 70, 20);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 999999, 255, false, false, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 999999, 255, false, false, false));

            Bat mount = (Bat) p.getWorld().spawnEntity(p.getLocation().add(0, -1, 0), EntityType.BAT);
            mount.setInvisible(true);
            mount.setSilent(true);
            mount.setAI(false);
            mount.setInvulnerable(true);
            mount.setGravity(false);

            mount.setPassenger(p);

            playerBatMap.put(p.getName(), mount.getUniqueId());

        }

    }

}
