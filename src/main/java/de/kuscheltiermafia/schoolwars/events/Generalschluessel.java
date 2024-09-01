package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Generalschluessel implements Listener {

    @EventHandler
    public void onGeneralSchluessel(PlayerInteractEvent e) {

        try {

            Block block = e.getClickedBlock();

            if (e.getItem().equals(Items.generalSchluessel) && block.getType() == Material.IRON_DOOR) {

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


            }
        }catch (Exception ignored){}

    }

}
