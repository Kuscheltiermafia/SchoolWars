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

public enum Lehrer {

    SCHNEIDER("Schneider", Villager.Type.SWAMP, Villager.Profession.CLERIC, true, false, true, true, false, Raum.NORMAL),
    FISCHER("Fischer", Villager.Type.PLAINS, Villager.Profession.TOOLSMITH, true, false, true, true, true, Raum.NORMAL),
    FLOETER("Flöter", Villager.Type.PLAINS, Villager.Profession.NITWIT, true, false, true, false, true, Raum.OS_SEKI),
    KLIEM("Kliem", Villager.Type.DESERT, Villager.Profession.WEAPONSMITH, true, false, true, true, true, Raum.PHYSIK),
    GERLICH("Gerlich", Villager.Type.PLAINS, Villager.Profession.SHEPHERD, true, false, true, false, true, Raum.GLASKASTEN),
    BAAR("Baar", Villager.Type.DESERT, Villager.Profession.ARMORER, true, false, true, true, true, Raum.CHEMIE),
    KESSELRING("Kesselring", Villager.Type.SWAMP, Villager.Profession.BUTCHER, true, false, true, false, true, Raum.PHYSIK),
    HERGET("Herget", Villager.Type.TAIGA, Villager.Profession.MASON, true, false, true, false, true, Raum.NORMAL),
    GREB("Greb", Villager.Type.PLAINS, Villager.Profession.CARTOGRAPHER, true, false, true, false, false, Raum.NORMAL),
    GEITNER("Geitner", Villager.Type.SAVANNA, Villager.Profession.FISHERMAN, true, false, true, false, true, Raum.PHYSIK),
    BLUMPFI("Blumpfingstl", Villager.Type.JUNGLE, Villager.Profession.LEATHERWORKER, true, false, true, false, false, Raum.KUNST),
    KRAUS("Kraus", Villager.Type.SWAMP, Villager.Profession.FARMER, true, false, true, false, true, Raum.MUSIK),
    METTENLEITER("Mettenleiter", Villager.Type.SNOW, Villager.Profession.FLETCHER, true, false, false, false, false, Raum.MUSIK),
    DEHNER("Dehner", Villager.Type.DESERT, Villager.Profession.LIBRARIAN, true, false, true, false, false, Raum.NORMAL),
    WEISS("Weiß", Villager.Type.TAIGA, Villager.Profession.CARTOGRAPHER, true, false, true, false, false, Raum.NORMAL),
    OLTEAN("Oltean", Villager.Type.JUNGLE, Villager.Profession.SHEPHERD, true, false, true, false, false, Raum.KUNST),
    OBERMEIER("Obermeier", Villager.Type.SAVANNA, Villager.Profession.NONE, true, false, true, false, false, Raum.NORMAL),
    WEBERT("Webert", Villager.Type.SAVANNA, Villager.Profession.FLETCHER, true, false, true, false, true, Raum.NORMAL),
    WEBER("Weber", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, false, false, Raum.CHEMIE),
    DIEZMANN("Diezmann", Villager.Type.TAIGA, Villager.Profession.FLETCHER, true, false, true, false, true, Raum.PHYSIK),
    SCHOEMIG("Schömig", Villager.Type.SNOW, Villager.Profession.NONE, true, true, true, false, false, Raum.NORMAL),;


    final String name;
    final Villager.Type type;
    final Villager.Profession profession;
    final boolean hasAI;
    final boolean isBaby;
    final boolean isSilent;
    final boolean hasQuest;
    final boolean isMale;
    final Raum raum;

    Lehrer(String name, Villager.Type type, Villager.Profession profession, boolean hasAI, boolean isBaby, boolean isSilent, boolean hasQuest, boolean isMale, Raum raum) {
        this.name = name;
        this.type = type;
        this.profession = profession;
        this.hasAI = hasAI;
        this.isBaby = isBaby;
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
