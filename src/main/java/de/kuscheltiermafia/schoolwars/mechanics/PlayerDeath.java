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
import de.kuscheltiermafia.schoolwars.config.ProbabilityConfig;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.win_conditions.Ranzen;
import io.github.realMorgon.sunriseLib.Message;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
import org.bukkit.scheduler.BukkitRunnable;

import static de.kuscheltiermafia.schoolwars.mechanics.RevivePlayer.playerBatMap;
import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

/**
 * Handles player death mechanics in SchoolWars.
 * <p>
 * Instead of killing players normally, this system "downs" them by mounting
 * them on an invisible bat and applying movement-restricting effects.
 * Downed players must be revived by teammates.
 * </p>
 */
public class PlayerDeath implements Listener {

    static final int DOWNED_DURATION_TICKS = 20 * 30; // 30 seconds

    /**
     * Handles natural/environmental death (fall damage, fire, etc.).
     */
    @EventHandler
    public void onNaturalDeath(EntityDamageEvent event) {

        // Only process player deaths
        if (!(event.getEntity() instanceof Player)){
            return;
        }

        //Only process natural causes
        if (event instanceof EntityDamageByEntityEvent){
            return;
        }

        // No damage outside of game
        if (!SchoolWars.gameStarted) {
            event.setCancelled(true);
            return;
        }

        // Check if this damage would kill the player
        if (event.getEntity() instanceof Player player && event.getFinalDamage() >= ((Player) event.getEntity()).getHealth()) {

            event.setCancelled(true);

            String playerName = player.getName();

            Message.sendToAllPlayers(playerName + ChatColor.GRAY + " hat auf natürliche Weise sein Ende gefunden.");

            handleDeath(player);

        }
    }

    /**
     * Handles death from entity damage (players, mobs, etc.).
     */
    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e){

        // Only process player deaths
        if (!(e.getEntity() instanceof Player)){
            return;
        }

        // No damage outside of game
        if (!SchoolWars.gameStarted) {
            e.setCancelled(true);
            return;
        }

        // Check if this damage would kill the player
        if (e.getEntity() instanceof Player player && e.getFinalDamage() >= ((Player) e.getEntity()).getHealth()) {

            e.setCancelled(true);

            String playerName = player.getName();
            Team team = playerMirror.get(player.getName()).getTeam();

            String killerName = "";

            // Prepare killer's name for death message
            if (player.getKiller() != null) {
                Player killer = player.getKiller();
                killerName = playerMirror.get(killer.getName()).getTeam().prefix + killer.getName();
            }

            // Send appropriate death message based on damager type
            if (e.getDamager().getType() == EntityType.PLAYER) {
                Message.sendToAllPlayers(playerName + ChatColor.GRAY + " wurde von " + killerName + ChatColor.GRAY + " besiegt.");
            } else if (e.getDamager().getType() == EntityType.VINDICATOR) {
                Message.sendToAllPlayers(playerName + ChatColor.GRAY + " wurde von wild gewordenem Schulpersonal besiegt.");
            } else {
                Message.sendToAllPlayers(playerName + ChatColor.GRAY + " hat auf natürliche Weise sein Ende gefunden.");
            }

            // Drop the team's backpack if player was carrying it
            if(player.getInventory().contains(team.ranzen_item) && player.getKiller() != null) {
                Ranzen.destroyRanzen(player.getKiller(), team, player.getLocation());
                player.getInventory().remove(new ItemStack(team.ranzen_item));
            }

            handleDeath(player);

        }
    }

    /**
     * Processes a player death by applying downed state.
     * Handles bossfight deaths differently (respawn in medical room).
     */
    private static void handleDeath(Player player) {

        // ========== Handle bossfight death (special respawn) ==========
        if (playerMirror.get(player.getName()).isInBossfight()) {
            respawnPlayerAtMedicalRoom(player);
            return;
        }

        // ========== Handle normal death (downed state) ==========
        
        // Restore health and show death message
        player.setHealth(20);
        player.sendTitle("§4Du wurdest besiegt!", "Dein Team muss dich wiederbeleben.", 10, 70, 20);
        player.playSound(player.getLocation(), "minecraft:block.beacon.deactivate", 1, 1);
        
        // Apply movement lock effects
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, DOWNED_DURATION_TICKS, 255, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, DOWNED_DURATION_TICKS, 255, false, false, false));

        // Create invisible bat mount to lock player position
        Bat mount = (Bat) player.getWorld().spawnEntity(player.getLocation().add(0, -1, 0), EntityType.BAT);
        mount.setInvisible(true);
        mount.setSilent(true);
        mount.setAI(false);
        mount.setInvulnerable(true);
        mount.setGravity(false);

        mount.setPassenger(player);

        // Track the bat for later revival
        playerBatMap.put(player.getName(), mount.getUniqueId());

        // Mark player as dead
        playerMirror.get(player.getName()).setAlive(false);


        for(int i = 0; i < DOWNED_DURATION_TICKS; i++) {
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    // Show progress bar in action bar
                    double calcProgress = (double) finalI / DOWNED_DURATION_TICKS * 100;
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Dauer bis respawn: " + ProgressBarHandler.progressBarsUpdate(calcProgress, ChatColor.DARK_RED) + ChatColor.GRAY + " --"));


                    if (finalI == DOWNED_DURATION_TICKS - 1) {
                        mount.setPassenger(null);
                        mount.remove();
                        playerBatMap.remove(player.getName());

                        playerMirror.get(player.getName()).setAlive(true);
                        respawnPlayerAtMedicalRoom(player);

                        for (int i = 0; i < 36; i++) {
                            if (player.getInventory().getItem(i) == null || player.getInventory().getItem(i).equals(Items.schulbuch1) || player.getInventory().getItem(i).equals(Items.schulbuch2)
                                    || player.getInventory().getItem(i).equals(Items.schulbuch3) || player.getInventory().getItem(i).equals(Items.schulbuch4)
                                    || player.getInventory().getItem(i).equals(Items.schulbuch5) || Items.ranzenList.contains(player.getInventory().getItem(i))) {
                                continue;
                            }
                            if (Math.random() < ProbabilityConfig.getProbability("item_loss_on_death", 0.2)) {
                                player.sendMessage(ChatColor.RED + "Du hast beim Sterben " + player.getInventory().getItem(i).getItemMeta().getDisplayName() + ChatColor.RED + " verloren!");
                                player.getInventory().setItem(i, null);
                            }
                        }
                    }
                }
            }.runTaskLater(SchoolWars.getPlugin(), i);
        }
    }

    static void respawnPlayerAtMedicalRoom(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1, false, false, false));
        player.sendTitle("§4Du wurdest besiegt!", "Du wachst im Krankenzimmer wieder auf", 10, 70, 20);
        player.playSound(player.getLocation(), "minecraft:block.beacon.deactivate", 1, 1);
        player.teleport(new Location(player.getWorld(), -35.0, 88.0, 144.0, -90, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 60, 255, true, true, true));
        playerMirror.get(player.getName()).setInBossfight(false);
    }

    /**
     * Prevents downed players from dismounting their bat.
     */
    @EventHandler
    public void onDismount(EntityDismountEvent e){
        if (e.getEntity() instanceof Player){
            if (e.getDismounted() instanceof Bat){
                e.setCancelled(true);
            }
        }
    }


    /**
     * Prevents downed players from interacting with the world.
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        try {
            if (!playerMirror.get(e.getPlayer().getName()).isAlive()) {
                e.setCancelled(true);
            }
        }catch (Exception ignored){}
    }

    /**
     * Prevents downed players from dealing damage.
     */
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
