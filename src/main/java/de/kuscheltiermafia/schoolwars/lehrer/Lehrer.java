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

    SCHNEIDER("Schneider", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, true, false, Raum.NORMAL),
    FISCHER("Fischer", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, true, true, Raum.NORMAL),
    FLOETER("Floeter", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, false, true, Raum.NORMAL),
    KLIEM("Kliem", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, true, true, Raum.PHYSIK),
    GERLICH("Gerlich", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, false, true, Raum.NORMAL),
    BAAR("Baar", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, true, true, Raum.CHEMIE),
    KESSELRING("Kesselring", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, false, true, Raum.PHYSIK),
    HERGET("Herget", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, false, true, Raum.NORMAL),
    GREB("Greb", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, false, false, Raum.NORMAL),
    GEITNER("Geitner", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, false, true, Raum.PHYSIK),
    BLUMPFI("Blumpfingstl", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, false, false, Raum.KUNST),
    KRAUS("Kraus", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, false, true, Raum.MUSIK),
    METTENLEITER("Mettenleiter", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, false, false, false, Raum.MUSIK),
    DEHNER("Dehner", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, false, false, Raum.NORMAL),
    WEISS("Weiß", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, false, false, Raum.NORMAL),
    OLTEAN("Oltean", Villager.Type.PLAINS, Villager.Profession.NONE, true, false, true, false, false, Raum.KUNST);

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


}
