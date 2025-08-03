package de.kuscheltiermafia.schoolwars.win_conditions.events_atombombe;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class Fluor implements Listener {

    private static final Location waschbecken1 = new Location(SchoolWars.WORLD, 12.0, 94.0, 188.0);
    private static final Location waschbecken2 = new Location(SchoolWars.WORLD, 12.0, 94.0, 196.0);
    private static final Location waschbecken3 = new Location(SchoolWars.WORLD, -15.0, 94.0, 194.0);
    private static final Location waschbecken4 = new Location(SchoolWars.WORLD, -17.0, 94.0, 194.0);

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
        }catch (Exception ignored){}
    }

    @EventHandler
    public static void onDrinkAquatisiertesFluor(PlayerItemConsumeEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().equals(Items.aquatisiertes_fluor)) {
            e.setCancelled(true);
        }

    }

}
