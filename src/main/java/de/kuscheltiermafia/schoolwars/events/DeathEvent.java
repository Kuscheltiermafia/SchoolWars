package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.gameprep.Teams;
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

public class DeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(EntityDamageEvent e){

        if (e.getEntity() instanceof Player && e.getFinalDamage() >= ((Player) e.getEntity()).getHealth()) {

            e.setCancelled(true);

            Player p = (Player) e.getEntity();
            assert p != null;
            String playerName = p.getName();

            String killerName = "";


            if (Teams.naturwissenschaftler.contains(p.getName())) {
                playerName = ChatColor.GREEN + "[NWS] " + p.getName();
                Ranzen.destroyRanzen(p.getKiller(), ChatColor.GREEN + "naturwissenschaftler", p.getLocation());
                p.getInventory().remove(new ItemStack(Items.nws_ranzen));
            } else if (Teams.sportler.contains(p.getName())) {
                playerName = ChatColor.DARK_RED + "[Sport] " + p.getName();
                Ranzen.destroyRanzen(p.getKiller(), ChatColor.DARK_RED + "sportler", p.getLocation());
                p.getInventory().remove(new ItemStack(Items.sport_ranzen));

            } else if (Teams.sprachler.contains(p.getName())) {
                playerName = ChatColor.GOLD + "[Sprache] " + p.getName();
                Ranzen.destroyRanzen(p.getKiller(), ChatColor.GOLD + "sprachler", p.getLocation());
                p.getInventory().remove(new ItemStack(Items.sprach_ranzen));

            }


            if (p.getKiller() != null) {
                Player killer = p.getKiller();
                killerName = killer.getName();

                if (Teams.naturwissenschaftler.contains(killer.getName())) {
                    killerName = ChatColor.GREEN + "[NWS] " + killer.getName();
                } else if (Teams.sportler.contains(killer.getName())) {
                    killerName = ChatColor.DARK_RED + "[Sport] " + killer.getName();
                } else if (Teams.sprachler.contains(killer.getName())) {
                    killerName = ChatColor.GOLD + "[Sprache] " + killer.getName();
                }
            }


            if (p.getKiller() == null) {
                Bukkit.broadcastMessage(playerName + ChatColor.GRAY + " hat auf natürliche Weise sein Ende gefunden.");
            } else {
                Bukkit.broadcastMessage(playerName + ChatColor.GRAY + " wurde von " + killerName + ChatColor.GRAY + " besiegt.");
            }

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

            RevivePlayer.deadPlayers.put(p.getName(), mount.getUniqueId());

        }

    }

}
