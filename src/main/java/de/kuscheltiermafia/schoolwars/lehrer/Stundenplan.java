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

import de.kuscheltiermafia.schoolwars.SchoolWars;
import io.github.realMorgon.sunriseLib.Sounds;
import org.bukkit.Sound;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static de.kuscheltiermafia.schoolwars.lehrer.Lehrer.lehrerEntityList;
import static de.kuscheltiermafia.schoolwars.lehrer.Lehrer.lehrerList;

public class Stundenplan {

    public static HashMap<Lehrer, Area> studenplan = new HashMap<>();

    public static void updateStundenplan(boolean instant) {

        if(instant) {
            initStundenplan();
        }else{
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(SchoolWars.gameStarted) {
                        initStundenplan();
                        updateStundenplan(false);
                    }
                }
            }.runTaskLater(SchoolWars.getPlugin(), 20 * 60 * 3);
        }
    }

    public static void initStundenplan() {

        Sounds.playForAll(Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f, 1.5, 0.5);

        for (Villager lehrer : lehrerEntityList) {
            lehrer.remove();
        }
        lehrerEntityList.clear();
        lehrerList.clear();

        ArrayList<Lehrer> shuffledLehrer = new ArrayList<>(Arrays.asList(Lehrer.values()));
        Collections.shuffle(shuffledLehrer);

        for (Area area : Area.values()) {
            int lehrerAmount = 0;
            int i = 0;

            do {
                if (!shuffledLehrer.isEmpty()) {
                    if (area.raum == Raum.GENERAL || shuffledLehrer.get(0).raum.equals(area.raum)) {

                        Lehrer.summonLehrer(area.lehrerSpawnPos, shuffledLehrer.get(0));
                        studenplan.put(shuffledLehrer.get(0), area);
                        shuffledLehrer.remove(0);
                        lehrerAmount++;

                    } else {
                        Lehrer tempLehrer = shuffledLehrer.get(0);
                        shuffledLehrer.remove(0);
                        shuffledLehrer.add(shuffledLehrer.size(), tempLehrer);
                    }
                }
                i++;
            } while (i < Area.values().length * 2 && !shuffledLehrer.isEmpty() && lehrerAmount < area.maxLehrerAmount);
        }
    }
}
