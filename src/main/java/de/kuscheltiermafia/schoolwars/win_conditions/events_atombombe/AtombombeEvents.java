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
 * </p>
 */
public class AtombombeEvents implements Listener {

    static boolean nukePlaced = false;
    static Location nukeLoc;
    private static BukkitTask timer;
    private static BlockDisplay nuke;


    @EventHandler
    public void onVersuch(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.BLAST_FURNACE)) {

            Block b = e.getClickedBlock();
            Player p = e.getPlayer();

            if(b.getLocation().add(0, 0, 1).getBlock().getType().equals(Material.BLAST_FURNACE) && b.getLocation().add(0, 0, -1).getBlock().getType().equals(Material.BLAST_FURNACE) && b.getLocation().add(0, 1, -1).getBlock().getType().equals(Material.IRON_BARS) && b.getLocation().add(0, 1, 1).getBlock().getType().equals(Material.HEAVY_CORE)) {
                if(p.getInventory().contains(Items.uranium) && p.getInventory().contains(Items.versuch)) {

                    p.getInventory().remove(Items.versuch);
                    p.getInventory().remove(Items.uranium);
                    Items.createItemsEntity(new ItemStack(Items.nuke), b.getLocation().add(0.5, 1, 0.5));

                    e.setCancelled(true);

                }
            }
        }
    }


    @EventHandler
    public static void onPlaceNuke(BlockPlaceEvent event) {
        if(event.getItemInHand().equals(Items.nuke)) {
            if (nukePlaced) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "Es kann nur eine Atombox platziert werden");
                return;
            }

            Player player = event.getPlayer();
            nukeLoc = event.getBlock().getLocation();

            Team team = playerMirror.get(player.getName()).getTeam();

            event.setCancelled(true);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            nuke = Bukkit.getServer().getWorld("schoolwars").spawn(nukeLoc, BlockDisplay.class);
            nuke.setBlock(Bukkit.createBlockData(Material.TNT));
            nuke.setGlowing(true);
            nuke.setGlowColorOverride(Color.RED);

            nukePlaced = true;


            timer = new BukkitRunnable() {

                int timeSec = 0;

                @Override
                public void run() {

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

                    if (timeSec == 5*60){
                        for (Map.Entry<Team, Integer> entry : Ranzen.ranzenAmount.entrySet()) {
                            if (entry.getKey() != team && entry.getValue() > 0){
                                nukeExplode(nuke);
                                Bukkit.broadcast(Component.text(ChatColor.RED + "Die Atombombe explodiert und zerstört alles. In dieser Runde gibt es keinen Gewinner!"));
                                Bukkit.getScheduler().cancelTask(this.getTaskId());
                                return;
                            }
                        }
                        nukeExplode(nuke);
                        Bukkit.broadcast(Component.text(ChatColor.YELLOW + "Die Atombombe ist explodiert! Das Team der " + team + ChatColor.YELLOW + " hat gewonnen!"));
                        Bukkit.getScheduler().cancelTask(this.getTaskId());
                        return;
                    }

                    for (Player p : Bukkit.getOnlinePlayers()){
                        p.playSound(nukeLoc, Sound.ENTITY_TNT_PRIMED, 1, 1);
                    }


                    for (Entity entity : nukeLoc.getWorld().getNearbyEntities(nukeLoc, 2, 1, 2)) {
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
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

    public static void stopNuke() {
        if (nukePlaced) {
            Bukkit.getScheduler().cancelTask(timer.getTaskId());
            nuke.remove();
            nukePlaced = false;
            Bukkit.broadcastMessage(ChatColor.RED + "Die Atombombe wurde entschärft");
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(ChatColor.RED + "Atombombe entschärft", "");
                if(playerMirror.get(p.getName()).isInBossfight()) {
                    playerMirror.get(p.getName()).setInBossfight(false);
                    p.setGameMode(GameMode.SURVIVAL);
                    p.teleport(nukeLoc);
                }
            }
        }
    }

    private static void nukeExplode(BlockDisplay nuke) {
        if (!nukePlaced) return;

        nukePlaced = false;
        for (Player p : Bukkit.getOnlinePlayers()){
            p.sendTitle(ChatColor.RED + "Spielende", ChatColor.RED + "Die Atombombe ist explodiert");
            p.setGameMode(GameMode.SPECTATOR);
            p.setSpectatorTarget(Bukkit.getEntity(UUID.fromString("e060929a-7c8f-471d-a76d-6a34244bdcab")));
            p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);
        }
        Bukkit.getScheduler().cancelTask(timer.getTaskId());
        nuke.remove();
        EndGame.end();
    }

}
