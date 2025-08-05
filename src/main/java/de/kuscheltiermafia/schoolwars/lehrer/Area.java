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

package de.kuscheltiermafia.schoolwars.lehrer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;

import java.util.ArrayList;

import static de.kuscheltiermafia.schoolwars.SchoolWars.WORLD;


public enum Area {

    NEUER_MUSIKSAAL("Neuer Musiksaal", new Location(WORLD, 43.0, 79.0, 187.0), new Location(WORLD, 57.0, 85.0, 200.0), new Location(WORLD, 46.0, 80.0, 199.0), 1, Raum.MUSIK),
    PHYSIKSAAL("Physiksaal", new Location(WORLD, -33.0, 79.0, 187.0), new Location(WORLD, -21.0, 85.0, 197.0), new Location(WORLD, -21.0, 80.0, 192.0), 1, Raum.PHYSIK),
    CHEMIESAAL("Chemiesaal", new Location(WORLD, -33.0, 94.0, 187.0), new Location(WORLD, -20.0, 97.0, 197.0), new Location(WORLD, -22.0, 94.0, 192.0), 1, Raum.CHEMIE),
    CHIEMIE_VORBEREITUNG_1("Chemie Vorbereitung", new Location(WORLD, -19.0, 94.0, 187.0), new Location(WORLD, -13.0, 98.0, 197.0), new Location(WORLD, -16.0, 95.0, 187.0), 1, Raum.CHEMIE),
    CHIEMIE_VORBEREITUNG_2("Chemie Vorbereitung", new Location(WORLD, -11.0, 94.0, 187.0), new Location(WORLD, -5.0, 98.0, 197.0), new Location(WORLD, -8.0, 95.0, 187.0), 1, Raum.CHEMIE),
    CHEMIE_UEBUNG("Chemie Übung", new Location(WORLD, -3.0, 94.0, 187.0), new Location(WORLD, 12.0, 98.0, 197.0), new Location(WORLD, -2.0, 95.0, 192.0), 1, Raum.CHEMIE),
    KOMISCHER_BIORAUM("Komischer Bio Raum", new Location(WORLD, 14.0, 94.0, 187.0), new Location(WORLD, 24.0, 98.0, 197.0), new Location(WORLD, 23.0, 94.0, 188.0), 1, Raum.CHEMIE),
    RAUM_E04("Raum E04", new Location(WORLD, -40.0, 79.0, 142.0), new Location(WORLD, -28.0, 84.0, 150.0), new Location(WORLD, -28.0, 80.0, 143.0), 1, Raum.NORMAL),
    RAUM_E05("Raum E05", new Location(WORLD, -25.0, 79.0, 142.0), new Location(WORLD, -13.0, 84.0, 150.0), new Location(WORLD, -13.0, 80.0, 143.0), 1, Raum.NORMAL),
    RAUM_E06("Raum E06", new Location(WORLD, -10.0, 79.0, 142.0), new Location(WORLD, 2.0, 84.0, 150.0), new Location(WORLD, 2.0, 80.0, 143.0), 1, Raum.NORMAL),
    RAUM_E07("Raum E07", new Location(WORLD, 5.0, 79.0, 142.0), new Location(WORLD, 17.0, 84.0, 150.0), new Location(WORLD, 17.0, 80.0, 143.0), 1, Raum.NORMAL),
    RAUM_E08("Raum E08", new Location(WORLD, 20.0, 79.0, 142.0), new Location(WORLD, 31.0, 84.0, 151.0), new Location(WORLD, 31.0, 80.0, 143.0), 1, Raum.NORMAL),
    RAUM_108("Raum 108", new Location(WORLD, -25.0, 87.0, 142.0), new Location(WORLD, -13.0, 91.0, 150.0), new Location(WORLD, -13.0, 87.0, 143.0), 1, Raum.NORMAL),
    RAUM_109("Raum 109", new Location(WORLD, -10.0, 87.0, 142.0), new Location(WORLD, 2.0, 91.0, 150.0), new Location(WORLD, 2.0, 87.0, 143.0), 1, Raum.NORMAL),
    RAUM_110("Raum 110", new Location(WORLD, 5.0, 87.0, 142.0), new Location(WORLD, 17.0, 91.0, 150.0), new Location(WORLD, 17.0, 87.0, 143.0), 1, Raum.NORMAL),
    RAUM_111("Raum 111", new Location(WORLD, 20.0, 87.0, 142.0), new Location(WORLD, 31.0, 91.0, 150.0), new Location(WORLD, 31.0, 87.0, 143.0), 1, Raum.NORMAL),
    RAUM_116("Raum 116", new Location(WORLD, 42.0, 86.0, 141.0), new Location(WORLD, 50.0, 91.0, 156.0), new Location(WORLD, 49.0, 87.0, 155.0), 1, Raum.NORMAL),
    RAUM_204("Raum 204", new Location(WORLD, -40.0, 94.0, 142.0), new Location(WORLD, -28.0, 98.0, 150.0), new Location(WORLD, -28.0, 98.0, 150.0), 1, Raum.NORMAL),
    RAUM_205("Raum 205", new Location(WORLD, -25.0, 94.0, 142.0), new Location(WORLD, -13.0, 98.0, 150.0), new Location(WORLD, -13.0, 94.0, 143.0), 1, Raum.NORMAL),
    RAUM_206("Raum 206", new Location(WORLD, -10.0, 94.0, 142.0), new Location(WORLD, 2.0, 98.0, 150.0), new Location(WORLD, 2.0, 94.0, 143.0), 1, Raum.NORMAL),
    RAUM_207("Raum 207", new Location(WORLD, 5.0, 94.0, 142.0), new Location(WORLD, 17.0, 98.0, 150.0), new Location(WORLD, 17.0, 94.0, 143.0), 1, Raum.NORMAL),
    RAUM_208("Raum 208", new Location(WORLD, 20.0, 94.0, 142.0), new Location(WORLD, 31.0, 98.0, 150.0), new Location(WORLD, 31.0, 94.0, 143.0), 1, Raum.NORMAL),
    OBERE_INFORMATIK("Raum xxx", new Location(WORLD, -33.0, 86.0, 187.0), new Location(WORLD, -20.0, 91.0, 197.0), new Location(WORLD, -22.0, 87.0, 189.0), 1, Raum.PHYSIK),
    WERKRAUM("Werkraum", new Location(WORLD, -34.0, 72.0, 168.0), new Location(WORLD, -27.0, 76.0, 185.0), new Location(WORLD, 31.0, 94.0, 143.0), 1, Raum.PHYSIK),
    PHYSIK_VORBEREITUNG_1("Physik Vorbereitung", new Location(WORLD, -19.0, 80.0, 187.0), new Location(WORLD, -13.0, 85.0, 197.0), new Location(WORLD, -17.0, 80.0, 188.0), 1, Raum.PHYSIK),
    PHYSIK_VORBEREITUNG_2("Physik Vorbereitung", new Location(WORLD, -11.0, 80.0, 187.0), new Location(WORLD, -5.0, 85.0, 197.0), new Location(WORLD, -7.0, 80.0, 188.0), 1, Raum.PHYSIK),
    PHYSIK_UEBUNG("Physik Übung", new Location(WORLD, -4.0, 80.0, 187.0), new Location(WORLD, 13.0, 84.0, 197.0), new Location(WORLD, -3.0, 80.0, 192.0), 1, Raum.PHYSIK),
    ROBOTIK_RAUM("Robotik Raum", new Location(WORLD, 14.0, 79.0, 187.0), new Location(WORLD, 25.0, 84.0, 197.0), new Location(WORLD, 19.0, 80.0, 189.0), 1, Raum.PHYSIK),
    GLASKASTEN_1("Glaskasten 1", new Location(WORLD, 31.0, 87.0, 167.0), new Location(WORLD, 36.0, 91.0, 170.0), new Location(WORLD, 36.0, 87.0, 167.0), 1, Raum.GLASKASTEN),
    GLASKASTEN_2("Glaskasten 2", new Location(WORLD, 31.0, 87.0, 172.0), new Location(WORLD, 36.0, 91.0, 175.0), new Location(WORLD, 35.0, 87.0, 174.0), 2, Raum.GLASKASTEN),
    GLASKASTEN_3("Glaskasten 3", new Location(WORLD, 31.0, 87.0, 177.0), new Location(WORLD, 36.0, 91.0, 180.0), new Location(WORLD, 35.0, 87.0, 180.0), 1, Raum.GLASKASTEN),
    SEKRETARIAT("Sekretariat", new Location(WORLD, -56.0, 86.0, 167.0), new Location(WORLD, -45.0, 91.0, 175.0), new Location(WORLD, -49.0, 86.0, 168.0), 1, Raum.VERWALTUNG),
    LEHRERZIMMER("Lehrerzimmer", new Location(WORLD, -35.0, 87.0, 200.0), new Location(WORLD, 25.0, 91.0, 204.0), new Location(WORLD, 4.0, 87.0, 191.0), 4, Raum.GENERAL),
    HINTERER_PAUSENHOF("Hinterer Pausenhof", new Location(WORLD, 43.0, 79.0, 130.0), new Location(WORLD, 84.0, 85.0, 184.0), new Location(WORLD, 43.0, 80.0, 177.0), 2, Raum.GENERAL),
    LINKER_FLUEGEL_GANG_ERSTER_STOCK("Linker Flügel erstes Obergeschoss", new Location(WORLD, -45.0, 87.0, 152.0), new Location(WORLD, 40.0, 91.0, 158.0), new Location(WORLD, -10.0, 87.0, 202.0), 0, Raum.GENERAL),
    NEUE_BUECHEREI("Neue Bücherei", new Location(WORLD, 37.0, 87.0, 188.0), new Location(WORLD, 50.0, 91.0, 202.0), new Location(WORLD, -10.0, 87.0, 202.0), 0, Raum.GENERAL),
    SILENZIUMRAUM("Silenziumraum", new Location(WORLD, 14.0, 87.0, 187.0), new Location(WORLD, 23.0, 91.0, 197.0), new Location(WORLD, -10.0, 87.0, 202.0), 0, Raum.GLASKASTEN),
    SCHULKIOSK("Kisok", new Location(WORLD, -55.0, 79.0, 194.0), new Location(WORLD, -53.0, 83.0, 203.0), new Location(WORLD, -10.0, 87.0, 202.0), 0, Raum.GENERAL),
    JORDAN_BUERO("Frau Jordans Büro", new Location(WORLD, -56.0, 87.0, 177.0), new Location(WORLD, -49.0, 91.0, 183.0), new Location(WORLD, -10.0, 87.0, 202.0), 0, Raum.VERWALTUNG),
    HAUSMEISTER("Hausmeister Kabuff", new Location(WORLD, -56.0, 80.0, 172.0), new Location(WORLD, -53.0, 83.0, 203.0), new Location(WORLD, -54.0, 85.0, 174.0), 0, Raum.GENERAL),
    PAUSENHOF("Pausenhof", new Location(WORLD, -34.0, 77.0, 160.0), new Location(WORLD, 42.0, 84.0, 185.0), new Location(WORLD, -15.0, 80, 175.0), 1, Raum.GENERAL);


    final String name;
    final Location minCoord;
    final Location maxCoord;
    final Location lehrerSpawnPos;
    final int maxLehrerAmount;
    final Raum raum;

    Area(String name, Location minCoord, Location maxCoord, Location lehrerSpawnPos, int maxLehrerAmount, Raum raum) {
        this.name = name;
        this.minCoord = minCoord;
        this.maxCoord = maxCoord;
        this.lehrerSpawnPos = lehrerSpawnPos;
        this.maxLehrerAmount = maxLehrerAmount;
        this.raum = raum;
    }


    public ArrayList<Player> getPlayersInArea() {
        ArrayList<Player> playersInArea = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()){
            if (player.getLocation().getX() >= minCoord.getX() && player.getLocation().getY() >= minCoord.getY() && player.getLocation().getZ() >= minCoord.getZ()) {
                if (player.getLocation().getX() <= maxCoord.getX() && player.getLocation().getY() <= maxCoord.getY() && player.getLocation().getZ() <= maxCoord.getZ()) {
                    playersInArea.add(player);
                }
            }
        }
        return playersInArea;
    }

    public static Area getAreaByLocation(Location location) {
        for (Area area : Area.values()) {
            if (location.getX() >= area.minCoord.getX() && location.getY() >= area.minCoord.getY() && location.getZ() >= area.minCoord.getZ()) {
                if (location.getX() <= area.maxCoord.getX() && location.getY() <= area.maxCoord.getY() && location.getZ() <= area.maxCoord.getZ()) {
                    return area;
                }
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}