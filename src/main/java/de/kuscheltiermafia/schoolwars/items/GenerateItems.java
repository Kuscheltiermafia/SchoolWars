/**
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

package de.kuscheltiermafia.schoolwars.items;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

public class GenerateItems {

    static ItemIdentifier[][] spawnItems;

    public static void generateItemLocations() {

        spawnItems = new ItemIdentifier[][]{
                {
                    new ItemIdentifier("minas_flasche", 1, Items.minas_flasche, new Location(Bukkit.getWorld("schoolwars"), 37, 88, 150)),
                    new ItemIdentifier("minas_flasche", 2, Items.minas_flasche, new Location(Bukkit.getWorld("schoolwars"), -65, 81, 193)),
                    new ItemIdentifier("minas_flasche", 3, Items.minas_flasche, new Location(Bukkit.getWorld("schoolwars"), 31, 88, 161)),
                    new ItemIdentifier("minas_flasche", 4, Items.minas_flasche, new Location(Bukkit.getWorld("schoolwars"), 24, 82, 196))
                },
                {
                    new ItemIdentifier("bens_vape", 1, Items.vape_fruitberry, new Location(Bukkit.getWorld("schoolwars"), 42, 87, 141)),
                }
        };

    }

    public static void summonItems() {
        for (int i1 = 0; i1 < spawnItems.length; i1++) {
            spawnItem(i1);
        }
    }

    public static void createItemsEntity(ItemStack item, Location loc) {
        Block b = loc.getBlock();

        Item itemEntity = b.getWorld().dropItem(loc, item);
        itemEntity.setPersistent(true);
        itemEntity.setInvulnerable(true);
        itemEntity.setVelocity(new Vector(0, 0, 0));
        itemEntity.setPickupDelay(Integer.MAX_VALUE);
    }

    public static void spawnItem(int i1) {

        Random rand = new Random();
        int random = rand.nextInt(spawnItems[i1].length);


        for(int i2 = 0; i2 < spawnItems[i1].length ; i2++) {

            ItemStack item = spawnItems[i1][i2].getItem();
            int randID = spawnItems[i1][i2].getRandID();
            Location loc = spawnItems[i1][i2].getLoc();

            if (random + 1 == randID) {

                createItemsEntity(item, loc.add(0.5, 0, 0.5));
                return;

            }
        }

    }
}
