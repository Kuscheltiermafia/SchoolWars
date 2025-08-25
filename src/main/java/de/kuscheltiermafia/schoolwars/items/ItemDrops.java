package de.kuscheltiermafia.schoolwars.items;

import de.kuscheltiermafia.schoolwars.config.ProbabilityConfig;
import de.kuscheltiermafia.schoolwars.lehrer.Area;
import de.kuscheltiermafia.schoolwars.lehrer.Lehrer;
import de.kuscheltiermafia.schoolwars.lehrer.Raum;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static de.kuscheltiermafia.schoolwars.SchoolWars.WORLD;

public enum ItemDrops {

    MINAS_FLASCHE(new Location[]{
        new Location(WORLD, 37, 88, 150),
        new Location(WORLD, -64, 81, 193),
        new Location(WORLD, 31, 88, 161),
        new Location(WORLD, 23, 81, 195)
    }, Items.minas_flasche, "drops.minas_flasche", 1.0),

    BENS_VAPE_FRUITBERRY(new Location[]{
        new Location(WORLD, 43, 87, 143)
    }, Items.vape_fruitberry, "drops.vape_fruitberry", 0.1),

    BENS_VAPE_ARSCHWASSER(new Location[]{
        new Location(WORLD, -54, 82, 155)
    }, Items.vape_arschwasser, "drops.vape_arschwasser", 0.1),

    BENS_VAPE_STRAWBERRY(new Location[]{
        new Location(WORLD, -11, 73, 152)
    }, Items.vape_strawberry, "drops.vape_strawberry", 0.1),

    BENS_VAPE_MANGO(new Location[]{
        new Location(WORLD, -50, 80, 203)
    }, Items.vape_mango, "drops.vape_mango", 0.1),

    BENS_VAPE_TRIPLE(new Location[]{
        new Location(WORLD, 31, 87, 160)
    }, Items.vape_triple, "drops.vape_triple", 0.1),

    BENS_VAPE_ARABICS(new Location[]{
        new Location(WORLD, 2, 74, 197)
    }, Items.vape_arabics, "drops.vape_arabics", 0.1),

    BENS_VAPE_AIR(new Location[]{
        new Location(WORLD, -31, 80, 172)
    }, Items.vape_air, "drops.vape_air", 0.1),

    BENS_VAPE_LEER(new Location[]{
        new Location(WORLD, 6, 79, 184),
        new Location(WORLD, -2, 78, 161),
        new Location(WORLD, 16, 80, 161)
    }, Items.vape_empty, "drops.vape_leer", 0.1),

    KEKS(Lehrer.KESSELRING, Items.keks, "drops.keks", 0.4, Raum.PHYSIK),

    VASILIS_IPAD(new Location[]{
        new Location(WORLD, 22.0, 88.5, 143.0)
    }, Items.ipad, "drops.vasilis_ipad", 0.1),

    EMILIAS_BRIEF(new Location[]{
        new Location(WORLD, -2.0, 87.0, 201.0)
    }, Items.emilia_ausland_brief, "drops.emilias_brief", 0.2),

    KERZE(new Location[]{
        new Location(WORLD, -56.0, 81.0, 164.0),
        new Location(WORLD, 12.0, 88.0, 187.0),
    }, Items.kerze, "drops.kerze", 0.4)
    ;

    private final Lehrer lehrer;
    private final Location[] locations;
    private final ItemStack item;
    private final String probabilityKey;
    private final double defaultChance;
    private final Raum raum;

    ItemDrops(Lehrer lehrer, ItemStack item, String probabilityKey, double defaultChance, @Nullable Raum raum) {
        this.lehrer = lehrer;
        this.locations = new Location[]{Lehrer.getLehrerEntity(lehrer).getLocation()};
        this.item = item;
        this.probabilityKey = probabilityKey;
        this.defaultChance = defaultChance;
        this.raum = raum;
    }

    ItemDrops(Location[] locations, ItemStack item, String probabilityKey, double defaultChance) {
        this.lehrer = null; // No specific teacher for this drop
        this.locations = locations;
        this.item = item;
        this.probabilityKey = probabilityKey;
        this.defaultChance = defaultChance;
        this.raum = null;

        for (Location loc : this.locations) {
            loc.add(0.5, 0, 0.5); // Center in the block
        }
    }

    /**
     * Get the current probability for this drop from the config
     */
    public double getChance() {
        return ProbabilityConfig.getProbability(probabilityKey, defaultChance);
    }

    public static void rollDrops() {
        outer:
        for (ItemDrops drop : values()){
            if (Math.random() > drop.getChance()) continue;
            for (Item item : Items.itemHitboxes.values()){
                if (item.getItemStack().equals(drop.item)) continue outer;
            }

            int random = new Random().nextInt(drop.locations.length);

            if (drop.raum == null || drop.lehrer == null) {
                Items.createItemsEntity(drop.item, drop.locations[random]);
            }else if (Area.getAreaByLocation(Lehrer.getLehrerEntity(drop.lehrer).getLocation()).raum == drop.raum){
                Items.createItemsEntity(drop.item, drop.locations[random]);
            }
        }
    }

}
