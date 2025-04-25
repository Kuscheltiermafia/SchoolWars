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

package de.kuscheltiermafia.schoolwars.win_conditions;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.GenerateItems;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.ProgressBarHandler;
import de.kuscheltiermafia.schoolwars.Team;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;


public class AtombombeEvents implements Listener {

    ArrayList<ItemStack> zentrifugeInventory = new ArrayList<>();

    static boolean nukePlaced = false;

    static Location nukeLoc;

    static int duration = 20 * 30;

    @EventHandler
    public void onVersuch(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.BLAST_FURNACE)) {

            Block b = e.getClickedBlock();
            Player p = e.getPlayer();

            if(b.getLocation().add(0, 0, 1).getBlock().getType().equals(Material.BLAST_FURNACE) && b.getLocation().add(0, 0, -1).getBlock().getType().equals(Material.BLAST_FURNACE) && b.getLocation().add(0, 1, -1).getBlock().getType().equals(Material.IRON_BARS) && b.getLocation().add(0, 1, 1).getBlock().getType().equals(Material.HEAVY_CORE)) {
                if(p.getInventory().contains(Items.uranium) && p.getInventory().contains(Items.versuch)) {

                    p.getInventory().remove(Items.versuch);
                    p.getInventory().remove(Items.uranium);
                    GenerateItems.createItemsEntity(new ItemStack(Items.nuke), b.getLocation().add(0, 0.5, 0));

                    e.setCancelled(true);

                }
            }
        }
    }

    @EventHandler
    public void onZentrifugeUse(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.DISPENSER)) {
            Block b = e.getClickedBlock();
            Player p = e.getPlayer();
            if(b.getLocation().add(0, 1, 0).getBlock().getType().equals(Material.POLISHED_TUFF_WALL) && b.getLocation().add(0, 2, 0).getBlock().getType().equals(Material.STONE_BUTTON)) {
                if(p.getInventory().getItemInMainHand().getItemMeta().equals(Items.useless_uranium.getItemMeta())) {
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                    zentrifugeInventory.add(Items.useless_uranium);
                    onZentrifugeVoll(zentrifugeInventory, b);
                    e.setCancelled(true);
                } else if(p.getInventory().getItemInMainHand().getItemMeta().equals(Items.fluor.getItemMeta())) {
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                    zentrifugeInventory.add(Items.fluor);
                    onZentrifugeVoll(zentrifugeInventory, b);
                    e.setCancelled(true);
                }
            }
        }
    }

    public static int i = 0;

    public static void onZentrifugeVoll(ArrayList<ItemStack> inv, Block b) {
        if (inv.contains(Items.fluor) && inv.contains(Items.useless_uranium)) {

            new BukkitRunnable() {
                @Override
                public void run() {
                    GenerateItems.createItemsEntity(new ItemStack(Items.uranium), b.getLocation().add(0.5, 2.75, 0.5));
                }
            }.runTaskLater(SchoolWars.getPlugin(), duration);

            Location loc = b.getLocation();


            for(int i = 0; i < duration; i++) {
                int finalI = i;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        double calcProgress = (double) finalI / duration * 100;

                        Collection<Entity> nearbyPlayers = loc.getWorld().getNearbyEntities(loc, 5, 5, 5);

                        for(Entity p: nearbyPlayers) {
                            if (p instanceof Player) {
                                ((Player) p).spigot().sendMessage (ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- Zentrifugenfortschritt: " + ProgressBarHandler.progressBarsUpdate(calcProgress, ChatColor.DARK_GREEN) + ChatColor.GRAY + " --"));
                                if (finalI % 30 == 0) {
                                    ((Player) p).playSound(loc, "entity.minecart.riding", SoundCategory.BLOCKS, 100, 1);
                                }
                            }
                        }
                    }
                }.runTaskLater(SchoolWars.getPlugin(), finalI);
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
            BlockDisplay nuke = Bukkit.getServer().getWorld("schoolwars").spawn(nukeLoc, BlockDisplay.class);
            nuke.setBlock(Bukkit.createBlockData(Material.TNT));
            nuke.setGlowing(true);
            nuke.setGlowColorOverride(Color.RED);

            nukePlaced = true;


            new BukkitRunnable() {

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
                        for (Player p : Bukkit.getOnlinePlayers()){
                            p.sendTitle(ChatColor.RED + "Spielende", ChatColor.RED + "Die Atombombe ist explodiert");
                            p.setGameMode(GameMode.SPECTATOR);
                            p.setSpectatorTarget(Bukkit.getEntity(UUID.fromString("e060929a-7c8f-471d-a76d-6a34244bdcab")));
                            p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);
                        }
                        Bukkit.getScheduler().cancelTask(this.getTaskId());
                        Team.clearTeams();
                        nuke.remove();
                        nukePlaced = false;
                        SchoolWars.gameStarted = false;
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
                            }
                        }
                    }

                    timeSec ++;
                }
            }.runTaskTimer(SchoolWars.plugin, 0, 20);

        }
    }
}
