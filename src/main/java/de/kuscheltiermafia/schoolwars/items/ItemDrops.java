package de.kuscheltiermafia.schoolwars.items;

import de.kuscheltiermafia.schoolwars.lehrer.Area;
import de.kuscheltiermafia.schoolwars.lehrer.Lehrer;
import de.kuscheltiermafia.schoolwars.lehrer.Raum;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

import static de.kuscheltiermafia.schoolwars.SchoolWars.WORLD;

public enum ItemDrops {

    MINAS_FLASCHE(new ArrayList<>() {{
        add(new Location(WORLD, 37, 88, 150));
        add(new Location(WORLD, -64, 81, 193));
        add(new Location(WORLD, 31, 88, 161));
        add(new Location(WORLD, 23, 81, 195));
    }}, Items.minas_flasche, 1),

    BENS_VAPE_FRUITBERRY(new ArrayList<>() {{
        add(new Location(WORLD, 43, 87, 143));
    }}, Items.vape_fruitberry, 0.1),

    BENS_VAPE_ARSCHWASSER(new ArrayList<>() {{
        add(new Location(WORLD, -54, 82, 155));
    }}, Items.vape_arschwasser, 0.1),

    BENS_VAPE_STRAWBERRY(new ArrayList<>() {{
        add(new Location(WORLD, -11, 73, 152));
    }}, Items.vape_strawberry, 0.1),

    BENS_VAPE_MANGO(new ArrayList<>() {{
        add(new Location(WORLD, -50, 80, 203));
    }}, Items.vape_mango, 0.1),

    BENS_VAPE_TRIPLE(new ArrayList<>() {{
        add(new Location(WORLD, 31, 87, 160));
    }}, Items.vape_triple, 0.1),

    BENS_VAPE_ARABICS(new ArrayList<>() {{
        add(new Location(WORLD, 2, 74, 197));
    }}, Items.vape_arabics, 0.1),

    BENS_VAPE_AIR(new ArrayList<>() {{
        add(new Location(WORLD, -31, 80, 172));
    }}, Items.vape_air, 0.1),

    BENS_VAPE_LEER(new ArrayList<>() {{
        add(new Location(WORLD, 6, 79, 184));
        add(new Location(WORLD, -2, 78, 161));
        add(new Location(WORLD, 16, 80, 161));
    }}, Items.vape_empty, 0.1),

    KEKS(Lehrer.KESSELRING, Items.keks, 0.4, Raum.PHYSIK),

    VASILIS_IPAD(new ArrayList<>() {{
        add(new Location(WORLD, 22.0, 88.0, 143.0));
    }}, Items.ipad, 0.1),

    EMILIAS_BRIEF(new ArrayList<>() {{
        add( new Location(WORLD, -2.0, 87.0, 201.0));
    }}, Items.emilia_ausland_brief, 0.2)
    ;

    private final Lehrer lehrer;
    private final ArrayList<Location> locations;
    private final ItemStack item;
    private final double chance;
    private final Raum raum;

    ItemDrops(Lehrer lehrer, ItemStack item, double chance, @Nullable Raum raum) {
        this.lehrer = lehrer;
        this.locations = new ArrayList<>();
        this.locations.add(Lehrer.getLehrerEntity(lehrer).getLocation());
        this.item = item;
        this.chance = chance;
        this.raum = raum;
    }

    ItemDrops(ArrayList<Location> locations, ItemStack item, double chance) {
        this.lehrer = null; // No specific teacher for this drop
        this.locations = locations;
        this.item = item;
        this.chance = chance;
        this.raum = null;
    }

    public static void rollDrops() {
        outer:
        for (ItemDrops drop : values()){
            if (Math.random() > drop.chance) continue;
            for (Item item : Items.itemHitboxes.values()){
                if (item.getItemStack().equals(drop.item)) continue outer;
            }

            int random = new Random().nextInt(drop.locations.size());

            if (drop.raum == null || drop.lehrer == null) {
                Items.createItemsEntity(drop.item, drop.locations.get(random).add(0.5, 0, 0.5));
            }else if (Area.getAreaByLocation(Lehrer.getLehrerEntity(drop.lehrer).getLocation()).raum == drop.raum){
                Items.createItemsEntity(drop.item, drop.locations.get(random).add(0.5, 0, 0.5));
            }
        }
    }

}
