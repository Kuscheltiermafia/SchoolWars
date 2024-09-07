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

package de.kuscheltiermafia.schoolwars.lehrer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Bereiche {

    public static HashMap<String, Location> minCoord = new HashMap<>();
    public static HashMap<String, Location> maxCoord = new HashMap<>();
    public static HashMap<String, Location> lehrerSpawnPos = new HashMap<>();
    public static HashMap<Location, String> areaIdentifier = new HashMap<>();

    public static ArrayList<String> lehrerAufsichtAreas = new ArrayList<>();

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
