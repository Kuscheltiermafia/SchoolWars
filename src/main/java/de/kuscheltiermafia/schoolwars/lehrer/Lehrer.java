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

/**
 * Enumeration of all teachers (Lehrer) in the SchoolWars game.
 * <p>
 * Each teacher is represented as a Villager entity with unique characteristics including:
 * <ul>
 *   <li>Name and appearance (villager type and profession)</li>
 *   <li>Behavioral attributes (AI, silence, scale)</li>
 *   <li>Game properties (has quests, preferred room type)</li>
 *   <li>Optional dialogue lines</li>
 * </ul>
 * </p>
 * <p>
 * Teachers are spawned according to the class schedule (Stundenplan) and can move around
 * the school, give quests to players, and interact with the game world.
 * </p>
 */
public enum Lehrer {

    SCHNEIDER("Schneider", Villager.Type.SWAMP, Villager.Profession.CLERIC, true, 1, true, true, false, Raum.NORMAL, null),
    FISCHER("Fischer", Villager.Type.PLAINS, Villager.Profession.TOOLSMITH, true, 1, true, true, true, Raum.NORMAL, null),
    FLOETER("Flöter", Villager.Type.PLAINS, Villager.Profession.NITWIT, true, 1, true, false, true, Raum.OS_SEKI, null),
    KLIEM("Kliem", Villager.Type.DESERT, Villager.Profession.WEAPONSMITH, true, 1, true, true, true, Raum.PHYSIK, null),
    GERLICH("Gerlich", Villager.Type.PLAINS, Villager.Profession.SHEPHERD, true, 1.01, true, false, true, Raum.GLASKASTEN, null),
    BAAR("Baar", Villager.Type.DESERT, Villager.Profession.ARMORER, true, 1, true, true, true, Raum.CHEMIE, null),
    KESSELRING("Keßelring", Villager.Type.SWAMP, Villager.Profession.BUTCHER, true, 1.01, true, false, true, Raum.PHYSIK, null),
    HERGET("Herget", Villager.Type.TAIGA, Villager.Profession.MASON, true, 1.01, true, false, true, Raum.NORMAL, null),
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
    AMON("Amon", Villager.Type.SWAMP, Villager.Profession.CARTOGRAPHER, true, 1, true, false, true, Raum.NORMAL, null),
    VORNBERGER("Vornberger", Villager.Type.SWAMP, Villager.Profession.MASON, true, 1, true, true, true, Raum.NORMAL, null),
    MEDING("Meding", Villager.Type.SNOW, Villager.Profession.BUTCHER, true, 1, true, false, false, Raum.NORMAL, null),
    SCHAIBLE("Schaible", Villager.Type.PLAINS, Villager.Profession.FLETCHER, true, 1, true, false, false, Raum.GLASKASTEN, null),
    JOHA("Joha", Villager.Type.DESERT, Villager.Profession.SHEPHERD, true, 1, true, false, false, Raum.NORMAL, null),
    DICKERT("Dickert", Villager.Type.SWAMP, Villager.Profession.ARMORER, true, 1, true, true, true, Raum.CHEMIE, null),
    MOLL("Moll", Villager.Type.JUNGLE, Villager.Profession.LIBRARIAN, true, 0.7, true, false, false, Raum.CHEMIE, null),
    REISS("Reiß", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, false, Raum.CHEMIE, null),
    BIEBER("Bieber", Villager.Type.TAIGA, Villager.Profession.LEATHERWORKER, true, 1, true, true, true, Raum.NORMAL, null),
    RAITH("Raith", Villager.Type.PLAINS, Villager.Profession.ARMORER, true, 1, true, true, true, Raum.VERWALTUNG, null),
    BECK("Beck", Villager.Type.SNOW, Villager.Profession.ARMORER, true, 0.9, true, false, true, Raum.NORMAL, null),
    STOCK("Stock", Villager.Type.SAVANNA, Villager.Profession.MASON, true, 1.01, true, false, true, Raum.GLASKASTEN, null),
    ERSETZBAR_07("Ersetzbarer Referendar #07", Villager.Type.SWAMP, Villager.Profession.NONE, true, 1, true, false, false, Raum.GLASKASTEN, null),
    ERSETZBAR_38("Ersetzbarer Referendar #38", Villager.Type.SWAMP, Villager.Profession.NONE, true, 1, true, false, false, Raum.GLASKASTEN, null),
    ERSETZBAR_69("Ersetzbarer Referendar #69", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, true, Raum.GLASKASTEN, null),
    ERSETZBAR_420("Ersetzbarer Referendar #420", Villager.Type.PLAINS, Villager.Profession.NONE, true, 1, true, false, true, Raum.GLASKASTEN, null),;

    /** Display name of the teacher. */
    final String name;
    
    /** Villager biome type for appearance. */
    final Villager.Type type;
    
    /** Villager profession for clothing appearance. */
    final Villager.Profession profession;
    
    /** Whether the villager has AI and can move. */
    final boolean hasAI;
    
    /** Scale modifier for the villager size. */
    final double scale;
    
    /** Whether the villager is silent (no ambient sounds). */
    final boolean isSilent;
    
    /** Whether this teacher offers quests to players. */
    final boolean hasQuest;
    
    /** Whether this teacher uses "Herr" (true) or "Frau" (false) as title. */
    final boolean isMale;
    
    /** The type of room this teacher prefers to spawn in. */
    final Raum raum;
    
    /** Optional dialogue lines for this teacher. */
    final ArrayList<String> dialogue;

    /** List of all currently active teacher enums. */
    public static ArrayList<Lehrer> lehrerList = new ArrayList<>();
    
    /** List of all spawned teacher villager entities. */
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

    /**
     * Finds a teacher by their display name.
     *
     * @param name the teacher's name to search for
     * @return the matching Lehrer enum, or null if not found
     */
    public static Lehrer getLehrerByName(String name) {
        for (Lehrer lehrer : Lehrer.values()) {
            if (lehrer.name.equals(name)) {
                return lehrer;
            }
        }
        return null;
    }

    /**
     * Gets the Lehrer enum from a villager entity.
     * <p>
     * Extracts the teacher name from the entity's custom name (removing the "Herr "/"Frau " prefix).
     * </p>
     *
     * @param e the entity to look up
     * @return the corresponding Lehrer enum
     */
    public static Lehrer getLehrerByEntity(Entity e) {
        return getLehrerByName(e.getCustomName().substring(5));
    }

    /**
     * Gets the villager entity for a specific teacher.
     *
     * @param lehrer the teacher to find the entity for
     * @return the villager entity, or null if not spawned
     */
    public static Entity getLehrerEntity(Lehrer lehrer) {
        for (Villager villager : lehrerEntityList) {
            if (villager.getCustomName().equals("Herr " + lehrer.name) || villager.getCustomName().equals("Frau " + lehrer.name)) {
                return villager;
            }
        }
        return null;
    }

    /**
     * Removes all spawned teacher entities and clears the tracking lists.
     */
    public static void removeAllLehrer() {
        for (Villager currentLehrer : lehrerEntityList) {
            currentLehrer.remove();
        }
        lehrerList.clear();
        lehrerEntityList.clear();
    }

    /**
     * Creates a villager entity configured as a teacher.
     *
     * @param location spawn location
     * @param name display name
     * @param type villager biome type
     * @param profession villager profession
     * @param hasAI whether AI is enabled
     * @param scale size scale modifier
     * @param isSilent whether the villager is silent
     * @param isMale whether to use "Herr" (true) or "Frau" (false)
     * @return the created villager entity
     */
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

    /**
     * Spawns a teacher at the specified location.
     * <p>
     * Creates the villager entity, adds it to tracking lists, and registers quest NPCs.
     * </p>
     *
     * @param loc the location to spawn at
     * @param lehrer the teacher to spawn
     * @return the spawned villager entity
     */
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

    /**
     * Gets the dialogue lines for this teacher.
     *
     * @return the list of dialogue lines, or null if none defined
     */
    public ArrayList<String> getDialogue() {
        return this.dialogue;
    }

    /**
     * Updates all teacher positions according to the current schedule.
     * <p>
     * If instant is false, schedules the update to run after 15 seconds
     * and then recursively schedules the next update.
     * </p>
     *
     * @param instant whether to update immediately or schedule for later
     */
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

    /**
     * Internal method to move all teachers to their scheduled positions.
     */
    private static void initLehrerPosition() {
        for (Lehrer lehrer : lehrerList) {

            Villager villager = lehrerEntityList.get(lehrerList.indexOf(lehrer));

            villager.getPathfinder().moveTo(studenplan.get(lehrer).lehrerSpawnPos);
        }

    }

    /**
     * Gets the display name of this teacher.
     *
     * @return the teacher's name
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if this teacher uses a male title ("Herr").
     *
     * @return true if male title is used, false for female title ("Frau")
     */
    public boolean isMale() {
        return isMale;
    }

}
