package de.kuscheltiermafia.schoolwars.events.win_conditions.events_atombombe;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.config.TimeConfig;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.BlockInteraction;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Handles centrifuge interactions for uranium enrichment.
 * <p>
 * Players use the centrifuge to process materials needed
 * for the atomic bomb crafting process.
 * </p>
 */
public class Zentrifuge implements Listener {

    ArrayList<ItemStack> zentrifugeInventory = new ArrayList<>();

    /** Duration of centrifuge processing in ticks. Configurable via times.properties. */
    static int duration = TimeConfig.getTicks("zentrifuge.duration", 20 * 30);

    @EventHandler
    public void onZentrifugeUse(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.DISPENSER)) {
            Block b = e.getClickedBlock();
            Player p = e.getPlayer();
            if(b.getLocation().add(0, 1, 0).getBlock().getType().equals(Material.POLISHED_TUFF_WALL) && b.getLocation().add(0, 2, 0).getBlock().getType().equals(Material.STONE_BUTTON)) {
                if (Items.isSpecificItem(p.getInventory().getItemInMainHand(), "useless_uranium")) {
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                    zentrifugeInventory.add(Items.getItem("useless_uranium"));
                    onZentrifugeVoll(zentrifugeInventory, b);
                    e.setCancelled(true);
                } else if (Items.isSpecificItem(p.getInventory().getItemInMainHand(), "fluor")) {
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                    zentrifugeInventory.add(Items.getItem("fluor"));
                    onZentrifugeVoll(zentrifugeInventory, b);
                    e.setCancelled(true);
                }
            }
        }
    }

    public static int i = 0;

    public static void onZentrifugeVoll(ArrayList<ItemStack> inv, Block b) {
        boolean hasFluor = inv.stream().anyMatch(it -> Items.isSpecificItem(it, "fluor"));
        boolean hasUran = inv.stream().anyMatch(it -> Items.isSpecificItem(it, "useless_uranium"));

        if (hasFluor && hasUran) {

            new BukkitRunnable() {
                @Override
                public void run() {
                    ItemStack uranium = Items.getItem("uranium");
                    if (uranium != null) Items.createItemsEntity(uranium, b.getLocation().add(0.5, 2.75, 0.5));
                }
            }.runTaskLater(SchoolWars.getPlugin(), duration);

            BlockInteraction.progressBlock(b, duration, "Zentrifugenfortschritt", "entity.minecart.riding", 30);
        }
    }

}
