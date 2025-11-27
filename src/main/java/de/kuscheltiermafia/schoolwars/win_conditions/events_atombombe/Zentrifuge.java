package de.kuscheltiermafia.schoolwars.win_conditions.events_atombombe;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.BlockInteraction;
import de.kuscheltiermafia.schoolwars.mechanics.ProgressBarHandler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Handles centrifuge interactions for uranium enrichment.
 * <p>
 * Players use the centrifuge to process materials needed
 * for the atomic bomb crafting process.
 * </p>
 */
public class Zentrifuge implements Listener {

    ArrayList<ItemStack> zentrifugeInventory = new ArrayList<>();

    static int duration = 20 * 30;

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
                    Items.createItemsEntity(new ItemStack(Items.uranium), b.getLocation().add(0.5, 2.75, 0.5));
                }
            }.runTaskLater(SchoolWars.getPlugin(), duration);

            BlockInteraction.progressBlock(b, duration, "Zentrifugenfortschritt", "entity.minecart.riding", 30);
        }
    }

}
