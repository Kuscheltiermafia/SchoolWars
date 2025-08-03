package de.kuscheltiermafia.schoolwars.lehrer;

import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

import static de.kuscheltiermafia.schoolwars.SchoolWars.WORLD;

public enum StundenplanDrops {

    KEKS(Lehrer.KESSELRING, Items.keks, 0.4, Raum.PHYSIK),
    EMILIAS_BRIEF(new Location(WORLD, -2.0, 87.0, 201.0), Items.emilia_ausland_brief, 0.2, null);

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

    StundenplanDrops(Location location, ItemStack item, double chance, @Nullable Raum raum) {
        this.lehrer = null; // No specific teacher for this drop
        this.location = location;
        this.item = item;
        this.chance = chance;
        this.raum = raum;
    }

    public static void rollDrops() {
        Bukkit.broadcastMessage("Rolling drops for Stundenplan...");
        for (StundenplanDrops drop : values()){
            Bukkit.broadcastMessage("Rolling drop: " + drop.name());
            if (Math.random() > drop.chance) continue;
            if (drop.raum == null || drop.lehrer == null) {
                Items.createItemsEntity(drop.item, drop.location);
                Bukkit.broadcastMessage("Dropped item: " + drop.item.getType() + " at " + drop.location);
            }else if (Area.getAreaByLocation(Lehrer.getLehrerEntity(drop.lehrer).getLocation()).raum == drop.raum){
                Items.createItemsEntity(drop.item, drop.location);
                Bukkit.broadcastMessage("Dropped item: " + drop.item.getType() + " for teacher: " + drop.lehrer.name() + " at " + drop.location);
            }
        }
    }

}
