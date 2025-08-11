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
import de.kuscheltiermafia.schoolwars.config.ProbabilityConfig;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.ParticleHandler;
import io.github.realMorgon.sunriseLib.Message;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static de.kuscheltiermafia.schoolwars.SchoolWars.WORLD;
import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

public class Generalschluessel implements Listener {

    private static final int riskIncrease = ProbabilityConfig.getInteger("generalschluessel.risk_increase", 5);

    void summonSekritaerin(Location loc) {
        Vindicator sekritaerin = WORLD.spawn(loc.add(0.5, 0, 0.5), Vindicator.class);

        ArrayList<String> names = new ArrayList<>();
        names.add("Frau Wacker");
        names.add("Frau Blümel");
        names.add("Frau Schranck-Teichmann");
        Collections.shuffle(names);

        sekritaerin.setCustomName(names.getFirst());
        sekritaerin.setCustomNameVisible(true);
        sekritaerin.setCanPickupItems(false);
        sekritaerin.setRemoveWhenFarAway(false);

        sekritaerin.setLootTable(null);

        ParticleHandler.createParticles(loc, Particle.EXPLOSION, 2, 0, true, null);
        ParticleHandler.createParticleCircle(loc, Particle.ANGRY_VILLAGER, 1.2, 20);
    }


    @EventHandler
    public void onGeneralSchluessel(PlayerInteractEvent e) {

        try {

            Block block = e.getClickedBlock();

            if (e.getItem().equals(Items.general_schluessel) && block.getType() == Material.IRON_DOOR) {
                e.setCancelled(true);

                Door door = (Door) block.getBlockData();
                if(door.isOpen() == false) {

                    door.setOpen(true);
                    block.setBlockData(door);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            door.setOpen(false);
                            block.setBlockData(door);
                        }
                    }.runTaskLater(SchoolWars.getPlugin(), 20 * 5);

                    playerMirror.get(e.getPlayer().getName()).getTeam().sekiRisk += riskIncrease;

                    Random random = new Random();
                    int randomRisk = random.nextInt(ProbabilityConfig.getInteger("generalschluessel.risk_check_max", 100));

                    if (randomRisk <= playerMirror.get(e.getPlayer().getName()).getTeam().sekiRisk) {
                        int randomMessage = random.nextInt(ProbabilityConfig.getInteger("generalschluessel.message_variants", 10));
                        if (randomMessage == 1) {
                            Message.sendInArea("§4Die Kollegin von der Seki-Frau hat dich erwischt!", block.getLocation(), 5, 3, 5);
                        } else {
                            Message.sendInArea("§4Die Seki-Frau hat dich erwischt!", block.getLocation(), 5, 3, 5);
                        }
                        summonSekritaerin(block.getLocation());
                    }

                }else{
                    e.getPlayer().sendActionBar("Dein Schlüssel hat nicht funktioniert!");
                }
            }
        }catch (Exception ignored){}
    }

    @EventHandler
    public void onClickItemframe(PlayerInteractEntityEvent e){

        if (e.getRightClicked() instanceof ItemFrame) {

            Player p = e.getPlayer();
            ItemFrame itemFrame = (ItemFrame) e.getRightClicked();

            if (itemFrame.getItem().equals(Items.general_schluessel)) {
                e.setCancelled(true);
                p.getInventory().addItem(new ItemStack(Items.general_schluessel));
            }
        }
    }
}
