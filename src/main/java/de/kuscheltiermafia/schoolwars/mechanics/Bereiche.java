package de.kuscheltiermafia.schoolwars.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Bereiche {

    public static HashMap<String, Location> minCoord = new HashMap<>();
    public static HashMap<String, Location> maxCoord = new HashMap<>();
    public static HashMap<Location, String> areaIdentifier = new HashMap<>();

    public static void initAreas() {
        createArea("lehrerzimmer_gang", new Location(Bukkit.getWorld("schoolwars"), -35.0, 87.0, 200.0), new Location(Bukkit.getWorld("schoolwars"), 25.0, 91.0, 204.0));
    }
    public static void createArea(String name, Location smallCoord, Location largeCoord) {
        minCoord.put(name, smallCoord);
        maxCoord.put(name, largeCoord);
        areaIdentifier.put(largeCoord, name);
    }

    public static String checkArea(Player p) {
        for(Location minLoc : minCoord.values()) {
            if(p.getLocation().getX() >= minLoc.getX() && p.getLocation().getY() >= minLoc.getY() && p.getLocation().getZ() >= minLoc.getZ()) {
                for(Location maxLoc : maxCoord.values()) {
                    if(p.getLocation().getX() <= maxLoc.getX() && p.getLocation().getY() <= maxLoc.getY() && p.getLocation().getZ() <= maxLoc.getZ()) {
                        return areaIdentifier.get(maxLoc);
                    }
                }
            }
        }
        return "No area found.";
    }
    public static boolean checkInArea(Player p, String area) {
        if(p.getLocation().getX() >= minCoord.get(area).getX() && p.getLocation().getY() >= minCoord.get(area).getY() && p.getLocation().getZ() >= minCoord.get(area).getZ()) {
            if(p.getLocation().getX() <= maxCoord.get(area).getX() && p.getLocation().getY() <= maxCoord.get(area).getY() && p.getLocation().getZ() <= maxCoord.get(area).getZ()) {
                return true;
            }
        }
        return false;
    }
}
