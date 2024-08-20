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
import de.kuscheltiermafia.schoolwars.items.GenerateItems;
import de.kuscheltiermafia.schoolwars.items.Items;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;


public class AtombombeEvents implements Listener {

    ArrayList<ItemStack> zentrifugeInventory = new ArrayList<ItemStack>();
    public static boolean zentrifugeActive;

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
                if(p.getInventory().getItemInMainHand().equals(Items.useless_uranium)) {
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                    zentrifugeInventory.add(Items.useless_uranium);
                    onZentrifugeVoll(zentrifugeInventory, b, b.getLocation());
                    e.setCancelled(true);
                } else if(p.getInventory().getItemInMainHand().equals(Items.fluor)) {
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                    zentrifugeInventory.add(Items.fluor);
                    onZentrifugeVoll(zentrifugeInventory, b, b.getLocation());
                    e.setCancelled(true);
                }
            }
        }
    }

    public static int i = 0;
    public static String progress;

    public static void onZentrifugeVoll(ArrayList inv, Block b, Location loc) {
        if (inv.contains(Items.fluor) && inv.contains(Items.useless_uranium)) {
            zentrifugeActive = true;

            new BukkitRunnable() {
                @Override
                public void run() {

                    i++;
                    String n = "§7█";
                    String d = "§4█";

                    switch(i) {
                        case 1:
                            progress = n+n+n+n+n+n+n+n+n+n;
                        case 60:
                            progress = d + n + n + n + n + n + n + n + n + n;
                        case 120:
                            progress = d + d + n + n + n + n + n + n + n + n;
                        case 180:
                            progress = d + d + d + n + n + n + n + n + n + n;
                        case 240:
                            progress = d + d + d + d + n + n + n + n + n + n;
                        case 300:
                            progress = d + d + d + d + d + n + n + n + n + n;
                        case 360:
                            progress = d + d + d + d + d + d + n + n + n + n;
                        case 420:
                            progress = d + d + d + d + d + d + d + n + n + n;
                        case 480:
                            progress = d + d + d + d + d + d + d + d + n + n;
                        case 540:
                            progress = d + d + d + d + d + d + d + d + d + n;
                        case 600:
                            progress = d + d + d + d + d + d + d + d + d + d;
                    }

                    for (Entity player : loc.getWorld().getNearbyEntities(loc, 1, 1, 1)) {
                        if(player instanceof Player) {
                            Player p = (Player) player;
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(progress));
                        }
                    }

                }
            }.runTaskTimer(SchoolWars.getPlugin(), 0, 1);


            new BukkitRunnable() {
                @Override
                public void run() {
                    if (b instanceof Dropper) {
                        Dropper dropper = (Dropper) b;
                        dropper.getInventory().setItem(4, Items.uranium);
                        zentrifugeActive = false;
                    }
                }
            }.runTaskLater(SchoolWars.getPlugin(), 20 * 30);
        }
    }
}
