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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.ArrayList;

import static de.kuscheltiermafia.schoolwars.lehrer.LehrerQuests.questLehrerList;
import static de.kuscheltiermafia.schoolwars.lehrer.Stundenplan.studenplan;

public enum Lehrer {

    SCHNEIDER("Schneider", Villager.Type.SWAMP, Villager.Profession.CLERIC, true, 1, true, true, false, Raum.NORMAL, null),
    FISCHER("Fischer", Villager.Type.PLAINS, Villager.Profession.TOOLSMITH, true, 1, true, true, true, Raum.NORMAL, null),
    FLOETER("Flöter", Villager.Type.PLAINS, Villager.Profession.NITWIT, true, 1, true, false, true, Raum.OS_SEKI, null),
    KLIEM("Kliem", Villager.Type.DESERT, Villager.Profession.WEAPONSMITH, true, 1, true, true, true, Raum.PHYSIK, null),
    GERLICH("Gerlich", Villager.Type.PLAINS, Villager.Profession.SHEPHERD, true, 1.03, true, false, true, Raum.GLASKASTEN, null),
    BAAR("Baar", Villager.Type.DESERT, Villager.Profession.ARMORER, true, 1, true, true, true, Raum.CHEMIE, null),
    KESSELRING("Keßelring", Villager.Type.SWAMP, Villager.Profession.BUTCHER, true, 1.1, true, false, true, Raum.PHYSIK, null),
    HERGET("Herget", Villager.Type.TAIGA, Villager.Profession.MASON, true, 1.04, true, false, true, Raum.NORMAL, null),
    GREB("Greb", Villager.Type.PLAINS, Villager.Profession.CARTOGRAPHER, true, 0.7, true, false, false, Raum.NORMAL, null),
    GEITNER("Geitner", Villager.Type.SAVANNA, Villager.Profession.FISHERMAN, true, 1, true, true, true, Raum.PHYSIK, null),
    BLUMPFI("Blumpfingstl", Villager.Type.JUNGLE, Villager.Profession.LEATHERWORKER, true, 0.9, false, false, false, Raum.KUNST, null),
    KRAUS("Kraus", Villager.Type.SWAMP, Villager.Profession.FARMER, true, 1, true, false, true, Raum.MUSIK, null),
    METTENLEITER("Mettenleiter", Villager.Type.SNOW, Villager.Profession.FLETCHER, true, 0.6, false, false, false, Raum.MUSIK, null),
    DEHNER("Dehner", Villager.Type.DESERT, Villager.Profession.LIBRARIAN, true, 1, true, false, false, Raum.NORMAL, null),
    WEISS("Weiß", Villager.Type.TAIGA, Villager.Profession.CARTOGRAPHER, true, 1, true, false, false, Raum.NORMAL, null),
    OLTEAN("Oltean", Villager.Type.JUNGLE, Villager.Profession.SHEPHERD, true, 1, true, false, false, Raum.KUNST, null),
    OBERMEIER("Obermeier", Villager.Type.SAVANNA, Villager.Profession.NONE, true, 0.8, true, false, false, Raum.GLASKASTEN, null),
    WEBERT("Webert", Villager.Type.SAVANNA, Villager.Profession.FLETCHER, true, 1, true, false, true, Raum.NORMAL, null),
    WEBER("Weber", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, false, Raum.CHEMIE, null),
    DIEZMANN("Diezmann", Villager.Type.TAIGA, Villager.Profession.FLETCHER, true, 1, true, false, true, Raum.PHYSIK, null),
    SCHOEMIG("Schömig", Villager.Type.SNOW, Villager.Profession.NONE, true, 0.5, true, false, false, Raum.NORMAL, null),
    FORSTER("Forster", Villager.Type.SNOW, Villager.Profession.NONE, true, 1, true, false, false, Raum.NORMAL, null),
    BERGER("Berger", Villager.Type.SNOW, Villager.Profession.NONE, true, 1, true, false, true, Raum.PHYSIK, null),
    AMON("Amon", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, true, Raum.NORMAL, null),
    VORNBERGER("Vornberger", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, true, true, Raum.NORMAL, null),
    MEDING("Meding", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, false, Raum.NORMAL, null),
    SCHAIBLE("Schaible", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, false, Raum.GLASKASTEN, null),
    JOHA("Joha", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, false, Raum.NORMAL, null),
    DICKERT("Dickert", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, true, true, Raum.CHEMIE, null),
    MOLL("Moll", Villager.Type.PLAINS, Villager.Profession.NONE, true, 0.7, true, false, false, Raum.CHEMIE, null),
    REISS("Reiß", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, false, Raum.CHEMIE, null),
    BIEBER("Bieber", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, true, true, Raum.NORMAL, null),
    RAITH("Raith", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, true, true, Raum.VERWALTUNG, null),
    ERSETZBAR_07("Ersetzbarer Referendar #07", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, false, Raum.GLASKASTEN, null),
    ERSETZBAR_38("Ersetzbarer Referendar #38", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, false, Raum.GLASKASTEN, null),
    ERSETZBAR_69("Ersetzbarer Referendar #69", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, true, Raum.GLASKASTEN, null),
    ERSETZBAR_96("Ersetzbarer Referendar #96", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, true, Raum.GLASKASTEN, null),;

    final String name;
    final Villager.Type type;
    final Villager.Profession profession;
    final boolean hasAI;
    final double scale;
    final boolean isSilent;
    final boolean hasQuest;
    final boolean isMale;
    final Raum raum;
    final ArrayList<String> dialogue;

    public static ArrayList<Lehrer> lehrerList = new ArrayList<>();
    public static ArrayList<Villager> lehrerEntityList = new ArrayList<>();

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
        this.dialogue = dialogue;
    }

    public static Lehrer getLehrerByName(String name) {
        for (Lehrer lehrer : Lehrer.values()) {
            if (lehrer.name.equals(name)) {
                return lehrer;
            }
        }
        return null;
    }

    public static Lehrer getLehrerByEntity(Entity e) {
        return getLehrerByName(e.getCustomName().substring(5));
    }

    public static Entity getLehrerEntity(Lehrer lehrer) {
        for (Villager villager : lehrerEntityList) {
            if (villager.getCustomName().equals("Herr " + lehrer.name) || villager.getCustomName().equals("Frau " + lehrer.name)) {
                return villager;
            }
        }
        return null;
    }

    public static void removeAllLehrer() {
        for (Villager currentLehrer : lehrerEntityList) {
            currentLehrer.remove();
        }
        lehrerList.clear();
        lehrerEntityList.clear();
    }

    static Villager createLehrer(Location location, String name, Villager.Type type, Villager.Profession profession, boolean hasAI, double scale, boolean isSilent, boolean isMale) {

        Villager currentLehrer = (Villager) Bukkit.getWorld("schoolwars").spawnEntity(location, EntityType.VILLAGER);

        if(isMale) {
            currentLehrer.setCustomName("Herr " + name);
        }else{
            currentLehrer.setCustomName("Frau " + name);
        }
        currentLehrer.setCustomNameVisible(true);
        currentLehrer.setVillagerType(type);
        currentLehrer.setAI(hasAI);
        currentLehrer.setInvulnerable(true);
        currentLehrer.setSilent(false);
        currentLehrer.setCanPickupItems(false);
        currentLehrer.setCollidable(true);
        currentLehrer.setRemoveWhenFarAway(false);
        currentLehrer.setPersistent(true);
        currentLehrer.setSilent(isSilent);
        currentLehrer.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(scale);
        currentLehrer.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25);

        currentLehrer.setProfession(profession);

        return currentLehrer;
    }

    public static Villager summonLehrer(Location loc, Lehrer lehrer) {
        Villager currentLehrer = createLehrer(loc, lehrer.name, lehrer.type, lehrer.profession, lehrer.hasAI, lehrer.scale, lehrer.isSilent, lehrer.isMale);
        currentLehrer.setProfession(lehrer.profession);
        lehrerList.add(lehrer);
        lehrerEntityList.add(currentLehrer);
        if (lehrer.hasQuest) {
            questLehrerList.add(currentLehrer);
        }
        return currentLehrer;
    }

    public ArrayList<String> getDialogue() {
        return this.dialogue;
    }

    public static void updateLehrerPosition(boolean instant) {
        if(instant) {
            initLehrerPosition();
        }else{
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(SchoolWars.gameStarted) {
                        initLehrerPosition();
                        updateLehrerPosition(false);
                    }
                }
            }.runTaskLater(SchoolWars.getPlugin(), 20 * 15);
        }
    }

    private static void initLehrerPosition() {
        for (Lehrer lehrer : lehrerList) {

            Villager villager = lehrerEntityList.get(lehrerList.indexOf(lehrer));

            villager.getPathfinder().moveTo(studenplan.get(lehrer).lehrerSpawnPos);
        }

    }

    public String getName() {
        return name;
    }

    public boolean isMale() {
        return isMale;
    }

}
