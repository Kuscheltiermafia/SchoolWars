package de.kuscheltiermafia.schoolwars.items;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;


public class ItemIdentifier {

    String name;
    int randID;
    ItemStack item;
    Location loc;

    public ItemIdentifier(String name, int randID, ItemStack item, Location loc) {

        this.name = name;
        this.randID = randID;
        this.item = item;
        this.loc = loc;

    }

    public String getName() {
        return name;
    }

    public int getRandID() {
        return randID;
    }

    public ItemStack getItem() {
        return item;
    }

    public Location getLoc() {
        return loc;
    }

}
