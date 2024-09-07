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

import de.kuscheltiermafia.schoolwars.SchoolWars;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static de.kuscheltiermafia.schoolwars.SchoolWars.world;

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
        for (Villager currentLehrer : world.getEntitiesByClass(Villager.class)) {
            if (currentLehrer.getCustomName() != null) {
                currentLehrer.remove();
            }
        }

        ArrayList<Lehrer> shuffledLehrer = new ArrayList<>(Arrays.asList(Lehrer.values()));
        Collections.shuffle(shuffledLehrer);

        for (Area area : Area.values()) {
            for (int i = 0; i < area.maxLehrerAmount; i++) {
                if (!shuffledLehrer.isEmpty()) {
                    if(area.raum == Raum.NORMAL || shuffledLehrer.get(0).raum.equals(area.raum)) {
                        LehrerHandler.summonLehrer(area.lehrerSpawnPos, shuffledLehrer.get(0));
                        studenplan.put(shuffledLehrer.get(0), area);
                        shuffledLehrer.remove(0);
                    }else {
                        shuffledLehrer.remove(0);
                    }
                }
            }
        }
    }
}
