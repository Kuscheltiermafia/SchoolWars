package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Locale;
import java.util.Random;

import static de.kuscheltiermafia.schoolwars.SchoolWars.world;

public class Generalschluessel implements Listener {

    void summonSekritaerin(Location loc) {
        Illusioner sekritaerin = world.spawn(loc.add(0.5, -1, 0.5), Illusioner.class);

        sekritaerin.setCustomName("Sekritärin");
        sekritaerin.setCustomNameVisible(true);
        sekritaerin.setCanPickupItems(false);
        sekritaerin.setRemoveWhenFarAway(false);
    }


    @EventHandler
    public void onGeneralSchluessel(PlayerInteractEvent e) {

        try {

            Block block = e.getClickedBlock();

            if (e.getItem().equals(Items.generalSchluessel) && block.getType() == Material.IRON_DOOR) {

                double sekiRisk = SchoolWars.playerMirror.get(e.getPlayer().getName()).getTeam().sekiRisk;

                e.setCancelled(true);

                Door door = (Door) block.getBlockData();
                boolean doorOpen = door.isOpen();


                door.setOpen(!doorOpen);
                block.setBlockData(door);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        door.setOpen(doorOpen);
                        block.setBlockData(door);
                    }
                }.runTaskLater(SchoolWars.getPlugin(), 20 * 5);

                 sekiRisk += 5;

                Random random = new Random();
                int randomInt = random.nextInt(100);

                if (randomInt <= sekiRisk){
                    world.getNearbyEntities(block.getLocation(), 5, 5, 5).forEach(entity -> {
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            player.sendMessage("Die Sekritärin hat dich erwischt!");
                        }
                    });

                    summonSekritaerin(block.getLocation());

                }


            }
        }catch (Exception ignored){}

    }

}
