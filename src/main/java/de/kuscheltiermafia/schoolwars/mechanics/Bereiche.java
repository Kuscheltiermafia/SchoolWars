package de.kuscheltiermafia.schoolwars.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Bereiche {

    public static HashMap<String, Location> minCoord = new HashMap<>();
    public static HashMap<String, Location> maxCoord = new HashMap<>();
    public static HashMap<String, Location> lehrerSpawnPos = new HashMap<>();
    public static HashMap<Location, String> areaIdentifier = new HashMap<>();

    public static ArrayList<String> lehrerAufsichtAreas = new ArrayList<>();

    public static void initAreas() {
        createArea("lehrerzimmer_gang", new Location(Bukkit.getWorld("schoolwars"), -35.0, 87.0, 200.0), new Location(Bukkit.getWorld("schoolwars"), 25.0, 91.0, 204.0), new Location(Bukkit.getWorld("schoolwars"), -7.0, 87.0, 201.0));
        lehrerAufsichtAreas.add("lehrerzimmer_gang");
    }
    public static void createArea(String name, Location smallCoord, Location largeCoord, Location lehrerSpawn) {
        minCoord.put(name, smallCoord);
        maxCoord.put(name, largeCoord);
        lehrerSpawnPos.put(name, lehrerSpawn);
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
