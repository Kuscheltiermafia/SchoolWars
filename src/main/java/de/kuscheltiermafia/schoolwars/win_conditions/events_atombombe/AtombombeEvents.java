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

package de.kuscheltiermafia.schoolwars.win_conditions.events_atombombe;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.events.ManageFoodLevel;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.EndGame;
import de.kuscheltiermafia.schoolwars.mechanics.ProgressBarHandler;
import de.kuscheltiermafia.schoolwars.Team;
import de.kuscheltiermafia.schoolwars.win_conditions.AtombombeBossfight;
import de.kuscheltiermafia.schoolwars.win_conditions.Ranzen;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;


/**
 * Handles atomic bomb win condition events.
 * <p>
 * Manages the placement, countdown, and detonation of the nuclear device.
 * Teams can win by placing a bomb and defending it until detonation.
 * A boss fight is triggered when shrunk players approach the bomb.
 * </p>
 */
public class AtombombeEvents implements Listener {

    // ========== Nuke State ==========
    /** Whether a nuke is currently placed. */
    static boolean nukePlaced = false;
    
    /** Location of the placed nuke. */
    static Location nukeLoc;
    
    /** Timer task for the nuke countdown. */
    private static BukkitTask timer;
    
    /** Visual entity representing the nuke. */
    private static BlockDisplay nuke;


    /**
     * Handles crafting the nuke using the experiment setup at a blast furnace.
     * Requires uranium and experiment items at the correct furnace configuration.
     */
    @EventHandler
    public void onVersuch(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.BLAST_FURNACE)) {

            Block b = e.getClickedBlock();
            Player p = e.getPlayer();

            // ========== Validate furnace configuration ==========
            // Check for specific block pattern around the furnace
            if(b.getLocation().add(0, 0, 1).getBlock().getType().equals(Material.BLAST_FURNACE) && b.getLocation().add(0, 0, -1).getBlock().getType().equals(Material.BLAST_FURNACE) && b.getLocation().add(0, 1, -1).getBlock().getType().equals(Material.IRON_BARS) && b.getLocation().add(0, 1, 1).getBlock().getType().equals(Material.HEAVY_CORE)) {
                
                // ========== Check for required materials ==========
                if(p.getInventory().contains(Items.uranium) && p.getInventory().contains(Items.versuch)) {

                    // Consume materials and spawn nuke item
                    p.getInventory().remove(Items.versuch);
                    p.getInventory().remove(Items.uranium);
                    Items.createItemsEntity(new ItemStack(Items.nuke), b.getLocation().add(0.5, 1, 0.5));

                    e.setCancelled(true);

                }
            }
        }
    }


    /**
     * Handles placing the nuke in the world.
     * Starts a 5-minute countdown timer. Only one nuke can be placed at a time.
     */
    @EventHandler
    public static void onPlaceNuke(BlockPlaceEvent event) {
        if(event.getItemInHand().equals(Items.nuke)) {
            // ========== Validate placement ==========
            if (nukePlaced) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "Es kann nur eine Atombox platziert werden");
                return;
            }

            Player player = event.getPlayer();
            nukeLoc = event.getBlock().getLocation();

            Team team = playerMirror.get(player.getName()).getTeam();

            // ========== Create visual nuke entity ==========
            event.setCancelled(true);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            nuke = Bukkit.getServer().getWorld("schoolwars").spawn(nukeLoc, BlockDisplay.class);
            nuke.setBlock(Bukkit.createBlockData(Material.TNT));
            nuke.setGlowing(true);
            nuke.setGlowColorOverride(Color.RED);

            nukePlaced = true;


            // ========== Start countdown timer (5 minutes) ==========
            timer = new BukkitRunnable() {

                int timeSec = 0;

                @Override
                public void run() {

                    // ========== Broadcast announcements at intervals ==========
                    if (timeSec == 0){
                        Bukkit.broadcastMessage(ChatColor.RED + "Das Team der " + team + ChatColor.RED + " hat eine Atombombe platziert");
                        Bukkit.broadcastMessage(ChatColor.RED + "Wird sie nicht innerhalb von 5 Minuten entschärft, wird sie explodieren und das Team der " + team + ChatColor.RED + " gewinnt");
                        Bukkit.getOnlinePlayers().forEach(p -> p.sendTitle(ChatColor.RED + "Atombombe platziert", ""));
                    }

                    if (timeSec == 60){
                        Bukkit.broadcastMessage(ChatColor.RED + "Die Atombombe detoniert in 4 Minuten");
                    }

                    if (timeSec == 3*60){
                        Bukkit.broadcastMessage(ChatColor.RED + "Die Atombombe detoniert in 2 Minuten");
                    }

                    if (timeSec == 4*60){
                        Bukkit.broadcastMessage(ChatColor.RED + "Die Atombombe detoniert in 1 Minute");
                    }

                    // ========== Handle detonation at 5 minutes ==========
                    if (timeSec == 5*60){
                        // Check if other teams still have backpacks (tie condition)
                        for (Map.Entry<Team, Integer> entry : Ranzen.ranzenAmount.entrySet()) {
                            if (entry.getKey() != team && entry.getValue() > 0){
                                nukeExplode(nuke);
                                Bukkit.broadcast(Component.text(ChatColor.RED + "Die Atombombe explodiert und zerstört alles. In dieser Runde gibt es keinen Gewinner!"));
                                Bukkit.getScheduler().cancelTask(this.getTaskId());
                                return;
                            }
                        }
                        // Team wins
                        nukeExplode(nuke);
                        Bukkit.broadcast(Component.text(ChatColor.YELLOW + "Die Atombombe ist explodiert! Das Team der " + team + ChatColor.YELLOW + " hat gewonnen!"));
                        Bukkit.getScheduler().cancelTask(this.getTaskId());
                        return;
                    }

                    // Play tick sound for all players
                    for (Player p : Bukkit.getOnlinePlayers()){
                        p.playSound(nukeLoc, Sound.ENTITY_TNT_PRIMED, 1, 1);
                    }


                    // ========== Check for shrunk players to trigger bossfight ==========
                    for (Entity entity : nukeLoc.getWorld().getNearbyEntities(nukeLoc, 2, 1, 2)) {
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            // Only shrunk players can enter the bossfight
                            if (player.getAttribute(Attribute.GENERIC_SCALE).getBaseValue() < 0.6) {
                                player.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1);
                                player.teleport(new Location(player.getWorld(), 14.0, 32.0, 179.0));
                                playerMirror.get(player.getName()).setInBossfight(true);

                                AtombombeBossfight.startBossfight();
                            }
                        }
                    }

                    timeSec ++;
                }
            }.runTaskTimer(SchoolWars.plugin, 0, 20);

        }
    }

    /**
     * Stops the nuke countdown and removes the nuke.
     * Called when the bossfight is won.
     */
    public static void stopNuke() {
        if (nukePlaced) {
            // Cancel timer and remove nuke
            Bukkit.getScheduler().cancelTask(timer.getTaskId());
            nuke.remove();
            nukePlaced = false;
            
            // Notify all players
            Bukkit.broadcastMessage(ChatColor.RED + "Die Atombombe wurde entschärft");
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(ChatColor.RED + "Atombombe entschärft", "");
                
                // Return bossfight players to nuke location
                if(playerMirror.get(p.getName()).isInBossfight()) {
                    playerMirror.get(p.getName()).setInBossfight(false);
                    p.setGameMode(GameMode.SURVIVAL);
                    p.teleport(nukeLoc);
                }
            }
        }
    }

    /**
     * Handles the nuke explosion animation and game end.
     */
    private static void nukeExplode(BlockDisplay nuke) {
        if (!nukePlaced) return;

        nukePlaced = false;
        
        // Set all players to spectator and show explosion
        for (Player p : Bukkit.getOnlinePlayers()){
            p.sendTitle(ChatColor.RED + "Spielende", ChatColor.RED + "Die Atombombe ist explodiert");
            p.setGameMode(GameMode.SPECTATOR);
            p.setSpectatorTarget(Bukkit.getEntity(UUID.fromString("e060929a-7c8f-471d-a76d-6a34244bdcab")));
            p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);
        }
        
        // Cleanup and end game
        Bukkit.getScheduler().cancelTask(timer.getTaskId());
        nuke.remove();
        EndGame.end();
    }

}
