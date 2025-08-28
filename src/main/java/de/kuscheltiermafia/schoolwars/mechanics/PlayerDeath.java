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

package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.Team;
import de.kuscheltiermafia.schoolwars.win_conditions.Ranzen;
import io.github.realMorgon.sunriseLib.Message;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static de.kuscheltiermafia.schoolwars.mechanics.RevivePlayer.playerBatMap;
import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onNaturalDeath(EntityDamageEvent event) {
        if (!SchoolWars.gameStarted) {
            event.setCancelled(true);
            return;
        }

        if (event.getEntity() instanceof Player player && event.getFinalDamage() >= ((Player) event.getEntity()).getHealth()) {

            event.setCancelled(true);

            String playerName = player.getName();

            Message.sendToAllPlayers(playerName + ChatColor.GRAY + " hat auf natürliche Weise sein Ende gefunden.");

            handleDeath(player);

        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e){

        if (!SchoolWars.gameStarted) {
            e.setCancelled(true);
            return;
        }

        if (e.getEntity() instanceof Player player && e.getFinalDamage() >= ((Player) e.getEntity()).getHealth()) {

            e.setCancelled(true);

            String playerName = player.getName();
            Team team = playerMirror.get(player.getName()).getTeam();

            String killerName = "";

//Ready Killers Name for message
            if (player.getKiller() != null) {
                Player killer = player.getKiller();
                killerName = playerMirror.get(killer.getName()).getTeam().prefix + killer.getName();
            }

//send death message
            if (e.getDamager().getType() == EntityType.PLAYER) {
                Message.sendToAllPlayers(playerName + ChatColor.GRAY + " wurde von " + killerName + ChatColor.GRAY + " besiegt.");
            } else if (e.getDamager().getType() == EntityType.VINDICATOR) {
                Message.sendToAllPlayers(playerName + ChatColor.GRAY + " wurde von wild gewordenem Schulpersonal besiegt.");
            } else {
                Message.sendToAllPlayers(playerName + ChatColor.GRAY + " hat auf natürliche Weise sein Ende gefunden.");
            }

//destroy Ranzen

            if(player.getInventory().contains(team.ranzen_item)) {
                Ranzen.destroyRanzen(player.getKiller(), team, player.getLocation());
                player.getInventory().remove(new ItemStack(team.ranzen_item));
            }

            handleDeath(player);

        }
    }

    private static void handleDeath(Player player) {

        if (playerMirror.get(player.getName()).isInBossfight()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1, false, false, false));
            player.sendTitle("§4Du wurdest besiegt!", "Du wachst im Krankenzimmer wieder auf", 10, 70, 20);
            player.playSound(player.getLocation(), "minecraft:block.beacon.deactivate", 1, 1);
            player.teleport(new Location(player.getWorld(), -35.0, 88.0, 144.0, -90, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 60, 255, true, true, true));
            playerMirror.get(player.getName()).setInBossfight(false);
            return;
        }

        player.setHealth(20);
        player.sendTitle("§4Du wurdest besiegt!", "Dein Team muss dich wiederbeleben.", 10, 70, 20);
        player.playSound(player.getLocation(), "minecraft:block.beacon.deactivate", 1, 1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 999999, 255, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 999999, 255, false, false, false));

        Bat mount = (Bat) player.getWorld().spawnEntity(player.getLocation().add(0, -1, 0), EntityType.BAT);
        mount.setInvisible(true);
        mount.setSilent(true);
        mount.setAI(false);
        mount.setInvulnerable(true);
        mount.setGravity(false);

        mount.setPassenger(player);

        playerBatMap.put(player.getName(), mount.getUniqueId());

        playerMirror.get(player.getName()).setAlive(false);
    }

    //Prevent dead Players from standing up
    @EventHandler
    public void onDismount(EntityDismountEvent e){
        if (e.getEntity() instanceof Player){
            if (e.getDismounted() instanceof Bat){
                e.setCancelled(true);
            }
        }
    }


    //Prevent dead Players from interacting
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        try {
            if (!playerMirror.get(e.getPlayer().getName()).isAlive()) {
                e.setCancelled(true);
            }
        }catch (Exception ignored){}
    }

    //Prevent dead Players from hitting
    @EventHandler
    public void onPunch(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            try {
                if (!playerMirror.get(e.getDamager().getName()).isAlive()) {
                    e.setCancelled(true);
                }
            }catch (Exception ignored){}
        }
    }
}
