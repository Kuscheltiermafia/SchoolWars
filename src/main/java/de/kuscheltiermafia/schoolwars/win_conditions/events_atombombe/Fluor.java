package de.kuscheltiermafia.schoolwars.win_conditions.events_atombombe;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.BlockInteraction;
import de.kuscheltiermafia.schoolwars.mechanics.ProgressBarHandler;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class Fluor implements Listener {

    private static final Location waschbecken1 = new Location(SchoolWars.WORLD, 12.0, 94.0, 188.0);
    private static final Location waschbecken2 = new Location(SchoolWars.WORLD, 12.0, 94.0, 196.0);
    private static final Location waschbecken3 = new Location(SchoolWars.WORLD, -15.0, 94.0, 194.0);
    private static final Location waschbecken4 = new Location(SchoolWars.WORLD, -17.0, 94.0, 194.0);

    private static final int duration = 20 * 30;
    private static final Location ofen = new Location(SchoolWars.WORLD, -9.0, 94.0, 194.0);

    @EventHandler
    public static void onAquatisieren(PlayerInteractEvent e) {
        try {
            if (e.getClickedBlock().getLocation().equals(waschbecken1) ||
                    e.getClickedBlock().getLocation().equals(waschbecken2) ||
                    e.getClickedBlock().getLocation().equals(waschbecken3) ||
                    e.getClickedBlock().getLocation().equals(waschbecken4)) {
                if (e.getPlayer().getInventory().getItemInMainHand().equals(Items.natrium_fluorid)) {
                    e.getPlayer().getInventory().remove(Items.natrium_fluorid);
                    e.getPlayer().getInventory().addItem(Items.aquatisiertes_fluor);
                    e.getPlayer().sendActionBar(Component.text("§aDu hast das Fluor aquatisiert!"));
                    e.setCancelled(true);
                }
            }
        } catch (Exception ignored) {
        }
    }

    @EventHandler
    public static void onDrinkAquatisiertesFluor(PlayerItemConsumeEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().equals(Items.aquatisiertes_fluor)) {
            e.setCancelled(true);
        }

    }


    @EventHandler
    public static void onVerdampfen(PlayerInteractEvent e) {
        try {

            Block b = e.getClickedBlock();
            if (b.getLocation().equals(ofen)) {
                e.setCancelled(true);
                if (e.getPlayer().getInventory().getItemInMainHand().equals(Items.aquatisiertes_fluor)) {

                    Furnace furnace = (Furnace) b.getState();
                    furnace.setBurnTime((short) duration);
                    furnace.update();

                    e.getPlayer().getInventory().remove(Items.aquatisiertes_fluor);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Items.createItemsEntity(new ItemStack(Items.fluor), b.getLocation().add(0.5, 0.8, 0.5));
                        }
                    }.runTaskLater(SchoolWars.getPlugin(), duration);

                    BlockInteraction.progressBlock(b, duration, "Fluorverdampfung", "block.fire.extinguish", 40);
                }
            }
        }catch (Exception ignored) {
        }
    }
}
