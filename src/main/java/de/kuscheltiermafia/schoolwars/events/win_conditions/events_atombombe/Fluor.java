package de.kuscheltiermafia.schoolwars.events.win_conditions.events_atombombe;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.config.TimeConfig;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.BlockInteraction;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Handles fluorine processing for atomic bomb crafting.
 * <p>
 * Players must process fluorine at sinks and furnaces to create
 * components needed for the nuclear device.
 * </p>
 */
public class Fluor implements Listener {

    private static final Location waschbecken1 = new Location(SchoolWars.WORLD, 12.0, 94.0, 188.0);
    private static final Location waschbecken2 = new Location(SchoolWars.WORLD, 12.0, 94.0, 196.0);
    private static final Location waschbecken3 = new Location(SchoolWars.WORLD, -15.0, 94.0, 194.0);
    private static final Location waschbecken4 = new Location(SchoolWars.WORLD, -17.0, 94.0, 194.0);

    /** Duration of fluorine evaporation in ticks. Configurable via times.properties. */
    private static final int duration = TimeConfig.getTicks("fluor.evaporation_duration", 20 * 30);
    private static final Location ofen = new Location(SchoolWars.WORLD, -9.0, 94.0, 194.0);

    @EventHandler
    public static void onAquatisieren(PlayerInteractEvent e) {
        try {
            if (e.getClickedBlock().getLocation().equals(waschbecken1) ||
                    e.getClickedBlock().getLocation().equals(waschbecken2) ||
                    e.getClickedBlock().getLocation().equals(waschbecken3) ||
                    e.getClickedBlock().getLocation().equals(waschbecken4)) {
                if (Items.isSpecificItem(e.getPlayer().getInventory().getItemInMainHand(), "natrium_fluorid")) {
                    ItemStack nat = Items.getItem("natrium_fluorid");
                    if (nat != null) e.getPlayer().getInventory().remove(nat);
                    ItemStack aqu = Items.getItem("aquatisiertes_fluor");
                    if (aqu != null) e.getPlayer().getInventory().addItem(aqu);
                    e.getPlayer().sendActionBar(Component.text("§aDu hast das Fluor aquatisiert!"));
                    e.setCancelled(true);
                }
            }
        } catch (Exception ignored) {
        }
    }

    @EventHandler
    public static void onDrinkAquatisiertesFluor(PlayerItemConsumeEvent e) {
        if (Items.isSpecificItem(e.getPlayer().getInventory().getItemInMainHand(), "aquatisiertes_fluor")) {
            e.setCancelled(true);
        }

    }

    private static boolean amVerdampfen = false;

    @EventHandler
    public static void onVerdampfen(PlayerInteractEvent e) {
        try {

            Block b = e.getClickedBlock();
            if (b.getLocation().equals(ofen)) {
                e.setCancelled(true);
                if (Items.isSpecificItem(e.getPlayer().getInventory().getItemInMainHand(), "aquatisiertes_fluor") && !amVerdampfen) {
                    amVerdampfen = true;

                    Furnace furnace = (Furnace) b.getState();
                    furnace.setBurnTime((short) duration);
                    furnace.update();

                    ItemStack aqu = Items.getItem("aquatisiertes_fluor");
                    if (aqu != null) e.getPlayer().getInventory().remove(aqu);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            ItemStack fluor = Items.getItem("fluor");
                            if (fluor != null) Items.createItemsEntity(fluor, b.getLocation().add(0.5, 1, 0.5));
                            amVerdampfen = false;
                        }
                    }.runTaskLater(SchoolWars.getPlugin(), duration);

                    BlockInteraction.progressBlock(b, duration, "Fluorverdampfung", "block.fire.extinguish", 40);
                }
            }
        }catch (Exception ignored) {
        }
    }
}
