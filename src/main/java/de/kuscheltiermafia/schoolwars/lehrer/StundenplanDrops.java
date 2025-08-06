package de.kuscheltiermafia.schoolwars.lehrer;

import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

import static de.kuscheltiermafia.schoolwars.SchoolWars.WORLD;

public enum StundenplanDrops {

    KEKS(Lehrer.KESSELRING, Items.keks, 0.4, Raum.PHYSIK),
    VASILIS_IPAD(new Location(WORLD, 22.0, 88.0, 143.0), Items.ipad, 0.1),
    EMILIAS_BRIEF(new Location(WORLD, -2.0, 87.0, 201.0), Items.emilia_ausland_brief, 0.2);

    private final Lehrer lehrer;
    private final Location location;
    private final ItemStack item;
    private final double chance;
    private final Raum raum;

    StundenplanDrops(Lehrer lehrer, ItemStack item, double chance, @Nullable Raum raum) {
        this.lehrer = lehrer;
        this.location = Lehrer.getLehrerEntity(lehrer).getLocation();
        this.item = item;
        this.chance = chance;
        this.raum = raum;
    }

    StundenplanDrops(Location location, ItemStack item, double chance) {
        this.lehrer = null; // No specific teacher for this drop
        this.location = location;
        this.item = item;
        this.chance = chance;
        this.raum = null;
    }

    public static void rollDrops() {
        outer:
        for (StundenplanDrops drop : values()){
            if (Math.random() > drop.chance) continue;
            for (Item item : Items.itemHitboxes.values()){
                if (item.getItemStack().equals(drop.item)) continue outer;

            }

            if (drop.raum == null || drop.lehrer == null) {
                Items.createItemsEntity(drop.item, drop.location);
            }else if (Area.getAreaByLocation(Lehrer.getLehrerEntity(drop.lehrer).getLocation()).raum == drop.raum){
                Items.createItemsEntity(drop.item, drop.location);
            }
        }
    }

}
