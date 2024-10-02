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

import org.bukkit.entity.Villager;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public enum Lehrer {

    SCHNEIDER("Schneider", Villager.Type.SWAMP, Villager.Profession.CLERIC, true, 1, true, true, false, Raum.NORMAL, null),
    FISCHER("Fischer", Villager.Type.PLAINS, Villager.Profession.TOOLSMITH, true, 1, true, true, true, Raum.NORMAL, new ArrayList<>(List.of("Ja...", "Müssen wir halt mal schauen"))),
    FLOETER("Flöter", Villager.Type.PLAINS, Villager.Profession.NITWIT, true, 1, true, false, true, Raum.OS_SEKI, null),
    KLIEM("Kliem", Villager.Type.DESERT, Villager.Profession.WEAPONSMITH, true, 1, true, true, true, Raum.PHYSIK, null),
    GERLICH("Gerlich", Villager.Type.PLAINS, Villager.Profession.SHEPHERD, true, 1.03, true, false, true, Raum.GLASKASTEN, null),
    BAAR("Baar", Villager.Type.DESERT, Villager.Profession.ARMORER, true, 1, true, true, true, Raum.CHEMIE, null),
    KESSELRING("Kesselring", Villager.Type.SWAMP, Villager.Profession.BUTCHER, true, 1.1, true, false, true, Raum.PHYSIK, null),
    HERGET("Herget", Villager.Type.TAIGA, Villager.Profession.MASON, true, 1, true, false, true, Raum.NORMAL, null),
    GREB("Greb", Villager.Type.PLAINS, Villager.Profession.CARTOGRAPHER, true, 0.7, true, false, false, Raum.NORMAL, null),
    GEITNER("Geitner", Villager.Type.SAVANNA, Villager.Profession.FISHERMAN, true, 1, true, false, true, Raum.PHYSIK, null),
    BLUMPFI("Blumpfingstl", Villager.Type.JUNGLE, Villager.Profession.LEATHERWORKER, true, 0.9, true, false, false, Raum.KUNST, null),
    KRAUS("Kraus", Villager.Type.SWAMP, Villager.Profession.FARMER, true, 1, true, false, true, Raum.MUSIK, null),
    METTENLEITER("Mettenleiter", Villager.Type.SNOW, Villager.Profession.FLETCHER, true, 0.6, false, false, false, Raum.MUSIK, new ArrayList<>(List.of("Seit bitte Leise", "Ich hab sehr feine Ohren", "Das schadet mir!"))),
    DEHNER("Dehner", Villager.Type.DESERT, Villager.Profession.LIBRARIAN, true, 1, true, false, false, Raum.NORMAL, null),
    WEISS("Weiß", Villager.Type.TAIGA, Villager.Profession.CARTOGRAPHER, true, 1, true, false, false, Raum.NORMAL, null),
    OLTEAN("Oltean", Villager.Type.JUNGLE, Villager.Profession.SHEPHERD, true, 1, true, false, false, Raum.KUNST, null),
    OBERMEIER("Obermeier", Villager.Type.SAVANNA, Villager.Profession.NONE, true, 0.8, true, false, false, Raum.NORMAL, null),
    WEBERT("Webert", Villager.Type.SAVANNA, Villager.Profession.FLETCHER, true, 1, true, false, true, Raum.NORMAL, null),
    WEBER("Weber", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, false, Raum.CHEMIE, null),
    DIEZMANN("Diezmann", Villager.Type.TAIGA, Villager.Profession.FLETCHER, true, 1, true, false, true, Raum.PHYSIK, null),
    SCHOEMIG("Schömig", Villager.Type.SNOW, Villager.Profession.NONE, true, 0.5, true, false, false, Raum.NORMAL, null),
    FORSTER("Forster", Villager.Type.SNOW, Villager.Profession.NONE, true, 1, true, false, false, Raum.NORMAL, null),
    BERGER("Berger", Villager.Type.SNOW, Villager.Profession.NONE, true, 1, true, false, true, Raum.PHYSIK, null),
    AMON("Amon", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1.1, true, false, true, Raum.NORMAL, null),
    VORNBERGER("Vornberger", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, true, true, Raum.NORMAL, null),;


    final String name;
    final Villager.Type type;
    final Villager.Profession profession;
    final boolean hasAI;
    final double scale;
    final boolean isSilent;
    final boolean hasQuest;
    final boolean isMale;
    final Raum raum;

    Lehrer(String name, Villager.Type type, Villager.Profession profession, boolean hasAI, double scale, boolean isSilent, boolean hasQuest, boolean isMale, Raum raum, @Nullable ArrayList<String> dialogue) {
        this.name = name;
        this.type = type;
        this.profession = profession;
        this.hasAI = hasAI;
        this.scale = scale;
        this.isSilent = isSilent;
        this.hasQuest = hasQuest;
        this.isMale = isMale;
        this.raum = raum;
    }

    public static Lehrer getLehrerByName(String name) {
        for (Lehrer lehrer : Lehrer.values()) {
            if (lehrer.name.equals(name)) {
                return lehrer;
            }
        }
        return null;
    }

}
