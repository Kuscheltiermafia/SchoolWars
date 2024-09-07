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
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;

import static de.kuscheltiermafia.schoolwars.SchoolWars.world;


public enum Area {
    LEHRERZIMMER("Lehrerzimmer", new Location(world, -35.0, 87.0, 200.0), new Location(world, 25.0, 91.0, 204.0), new Location(world, 4.0, 87.0, 191.0), 4 /*Rest oder so idk*/, Raum.NORMAL),
    HINTERER_PAUSENHOF("Hinterer Pausenhof", new Location(world, 43.0, 79.0, 130.0), new Location(world, 84.0, 85.0, 184.0), new Location(world, 43.0, 80.0, 177.0), 2, Raum.NORMAL),
    NEUER_MUSIKSAAL("Neuer Musiksaal", new Location(world, 43.0, 79.0, 187.0), new Location(world, 57.0, 85.0, 200.0), new Location(world, 46.0, 80.0, 199.0), 1, Raum.MUSIK),
    PHYSIKSAAL("Physiksaal", new Location(world, -33.0, 79.0, 187.0), new Location(world, -21.0, 85.0, 197.0), new Location(world, -21.0, 80.0, 192.0), 1, Raum.PHYSIK),
    RAUM_E04("Raum E04", new Location(world, -40.0, 79.0, 142.0), new Location(world, -28.0, 84.0, 150.0), new Location(world, -28.0, 80.0, 143.0), 1, Raum.NORMAL),
    RAUM_E05("Raum E05", new Location(world, -25.0, 79.0, 142.0), new Location(world, -13.0, 84.0, 150.0), new Location(world, -13.0, 80.0, 143.0), 1, Raum.NORMAL),
    RAUM_E06("Raum E06", new Location(world, -10.0, 79.0, 142.0), new Location(world, 2.0, 84.0, 150.0), new Location(world, 2.0, 80.0, 143.0), 1, Raum.NORMAL),
    RAUM_E07("Raum E07", new Location(world, 5.0, 79.0, 142.0), new Location(world, 17.0, 84.0, 150.0), new Location(world, 17.0, 80.0, 143.0), 1, Raum.NORMAL),
    RAUM_E08("Raum E08", new Location(world, 20.0, 79.0, 142.0), new Location(world, 31.0, 84.0, 151.0), new Location(world, 31.0, 80.0, 143.0), 1, Raum.NORMAL),
    RAUM_108("Raum 108", new Location(world, -25.0, 87.0, 142.0), new Location(world, -13.0, 91.0, 150.0), new Location(world, -13.0, 87.0, 143.0), 1, Raum.NORMAL),
    RAUM_109("Raum 109", new Location(world, -10.0, 87.0, 142.0), new Location(world, 2.0, 91.0, 150.0), new Location(world, 2.0, 87.0, 143.0), 1, Raum.NORMAL),
    RAUM_110("Raum 110", new Location(world, 5.0, 87.0, 142.0), new Location(world, 17.0, 91.0, 150.0), new Location(world, 17.0, 87.0, 143.0), 1, Raum.NORMAL),
    RAUM_111("Raum 111", new Location(world, 20.0, 87.0, 142.0), new Location(world, 31.0, 91.0, 150.0), new Location(world, 31.0, 87.0, 143.0), 1, Raum.NORMAL),
    RAUM_116("Raum 116", new Location(world, 42.0, 86.0, 141.0), new Location(world, 50.0, 91.0, 156.0), new Location(world, 49.0, 87.0, 155.0), 1, Raum.NORMAL),
    RAUM_204("Raum 204", new Location(world, -40.0, 94.0, 142.0), new Location(world, -28.0, 98.0, 150.0), new Location(world, -28.0, 98.0, 150.0), 1, Raum.NORMAL),
    RAUM_205("Raum 205", new Location(world, -25.0, 94.0, 142.0), new Location(world, -13.0, 98.0, 150.0), new Location(world, -13.0, 94.0, 143.0), 1, Raum.NORMAL),
    RAUM_206("Raum 206", new Location(world, -10.0, 94.0, 142.0), new Location(world, 2.0, 98.0, 150.0), new Location(world, 2.0, 94.0, 143.0), 1, Raum.NORMAL),
    RAUM_207("Raum 207", new Location(world, 5.0, 94.0, 142.0), new Location(world, 17.0, 98.0, 150.0), new Location(world, 17.0, 94.0, 143.0), 1, Raum.NORMAL),
    RAUM_208("Raum 208", new Location(world, 20.0, 94.0, 142.0), new Location(world, 31.0, 98.0, 150.0), new Location(world, 31.0, 94.0, 143.0), 1, Raum.NORMAL),
    OBERE_INFORMATIK("Raum xxx", new Location(world, -33.0, 86.0, 187.0), new Location(world, -20.0, 91.0, 197.0), new Location(world, -22.0, 87.0, 189.0), 1, Raum.PHYSIK),
    WERKRAUM("Werkraum", new Location(world, -34.0, 72.0, 168.0), new Location(world, -27.0, 76.0, 185.0), new Location(world, 31.0, 94.0, 143.0), 1, Raum.PHYSIK),
    LINKER_FLUEGEL_GANG_ERSTER_STOCK("Linker Flügel erstes Obergeschoss", new Location(world, -45.0, 87.0, 152.0), new Location(world, 40.0, 91.0, 158.0), new Location(world, -22.0, 87.0, 189.0), 0, Raum.PHYSIK),
    PAUSENHOF("Pausenhof", new Location(world, -34.0, 77.0, 160.0), new Location(world, 42.0, 84.0, 185.0), new Location(world, -15.0, 80, 175.0), 1, Raum.NORMAL),
    PHYSIK_VORBEREITUNG_1("Physik Vorbereitung", new Location(world, -19.0, 80.0, 187.0), new Location(world, -13.0, 85.0, 197.0), new Location(world, -17.0, 80.0, 188.0), 1, Raum.PHYSIK),
    PHYSIK_VORBEREITUNG_2("Physik Vorbereitung", new Location(world, -11.0, 80.0, 187.0), new Location(world, -5.0, 85.0, 197.0), new Location(world, -7.0, 80.0, 188.0), 1, Raum.PHYSIK),
    PHYSIK_UEBUNG("Physik Übung", new Location(world, -4.0, 80.0, 187.0), new Location(world, 13.0, 84.0, 197.0), new Location(world, -3.0, 80.0, 192.0), 1, Raum.PHYSIK),
    ROBOTIK_RAUM("Robotik Raum", new Location(world, 14.0, 79.0, 187.0), new Location(world, 25.0, 84.0, 197.0), new Location(world, 19.0, 80.0, 189.0), 1, Raum.PHYSIK);


    final String name;
    final Location minCoord;
    final Location maxCoord;
    final Location lehrerSpawnPos;
    final int maxLehrerAmount;
    final Raum raum;

    Villager lehrer;

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
    
}