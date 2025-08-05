package de.kuscheltiermafia.schoolwars.win_conditions.events_atombombe;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.BlockInteraction;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class BaarsKaffee implements Listener {

    @EventHandler
    public void onClickItemframe(PlayerInteractEntityEvent e){

        if (e.getRightClicked() instanceof ItemFrame) {

            Player p = e.getPlayer();
            ItemFrame itemFrame = (ItemFrame) e.getRightClicked();

            if (itemFrame.getItem().equals(Items.leere_tasse)) {
                e.setCancelled(true);
                p.getInventory().addItem(new ItemStack(Items.leere_tasse));
            }
        }
    }

    @EventHandler
    public void onZwiebelKrate(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getClickedBlock().getLocation().equals(new Location(SchoolWars.WORLD, -35.0, 73.0, 149.0)) || e.getClickedBlock().getLocation().equals(new Location(SchoolWars.WORLD, -32.0, 73.0, 149.0))) {
                Player p = e.getPlayer();

                Inventory kaffeeKiste = Bukkit.createInventory(null, 9 * 3, "Kaffeekiste");
                for(int i = 0; i < 3; i++) {
                    Random rand = new Random();
                    int random = rand.nextInt(27);

                    kaffeeKiste.setItem(random, Items.kaffeebohnen);
                }
                for(int i = 0; i < kaffeeKiste.getSize(); i++) {
                    if(kaffeeKiste.getItem(i) == null) {
                        kaffeeKiste.setItem(i, Items.verbrannte_bohnen);
                    }
                }

                p.openInventory(kaffeeKiste);
            }
        }
    }

    private static final int duration = 20 * 60;
    private static boolean amBrauen = false;

    @EventHandler
    public void onKaffeeBrauen(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getLocation().equals(new Location(SchoolWars.WORLD, -3.0, 88.0, 197.0))){
                e.setCancelled(true);

                if (amBrauen) {
                    e.getPlayer().sendActionBar(Component.text(ChatColor.RED + "Du kannst nur einen Kaffee gleichzeitig brauen!"));
                    return;
                }

                Player p = e.getPlayer();
                if (p.getInventory().contains(Items.leere_tasse) && p.getInventory().contains(Items.kaffeebohnen)) {
                    amBrauen = true;
                    p.getInventory().removeItem(Items.leere_tasse);
                    p.getInventory().removeItem(Items.kaffeebohnen);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Items.createItemsEntity(new ItemStack(Items.baar_kaffee), e.getClickedBlock().getLocation().add(0.5, 0, -0.5));
                            amBrauen = false;
                        }
                    }.runTaskLater(SchoolWars.getPlugin(), duration);
                    BlockInteraction.progressBlock(e.getClickedBlock(), 20 * 60, "Kaffee kochen", "entity.cow.milk", 20 * 3);

                } else {
                    p.sendActionBar(Component.text( ChatColor.RED + "Du hast nicht die richtigen Zutaten, um Kaffee zu brauen!"));
                }
            }
        }
    }

}
