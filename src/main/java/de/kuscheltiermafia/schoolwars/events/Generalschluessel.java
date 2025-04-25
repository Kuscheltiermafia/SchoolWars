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

package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import io.github.realMorgon.sunriseLib.Message;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

import static de.kuscheltiermafia.schoolwars.SchoolWars.world;
import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

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

                playerMirror.get(e.getPlayer().getName()).getTeam().sekiRisk += 5;

                Random random = new Random();
                int randomRisk = random.nextInt(100);

                if (randomRisk <= playerMirror.get(e.getPlayer().getName()).getTeam().sekiRisk){
                    int randomMessage = random.nextInt(10);
                    if (randomMessage == 1){
                        Message.sendInArea("§4Die Kollegin von der Seki-Frau hat dich erwischt!", block.getLocation(), 5, 3, 5);
                    }else {
                        Message.sendInArea("§4Die Seki-Frau hat dich erwischt!", block.getLocation(), 5, 3, 5);
                    }
                    summonSekritaerin(block.getLocation());
                }

            }
        }catch (Exception ignored){}
    }
}
