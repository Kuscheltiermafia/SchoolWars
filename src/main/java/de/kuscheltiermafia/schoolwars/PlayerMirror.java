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

package de.kuscheltiermafia.schoolwars;

import de.kuscheltiermafia.schoolwars.lehrer.Lehrer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a mirror/shadow of a player's game state in SchoolWars.
 * <p>
 * This class stores additional player-specific data that isn't tracked by Minecraft,
 * including team assignment, reputation with teachers, combat status, and dialogue progress.
 * Each player has a corresponding PlayerMirror instance stored in the static {@link #playerMirror} map.
 * </p>
 */
public class PlayerMirror {

    /**
     * Global map storing PlayerMirror instances keyed by player name.
     * Used to quickly look up a player's game state.
     */
    public static HashMap<String, PlayerMirror> playerMirror = new HashMap<>();

    /** The name of the player this mirror represents. */
    String playerName;

    /** Map of teacher reputation values for this player. */
    HashMap<Lehrer, Double> rep = new HashMap<>();

    /** Whether the player is currently in combat. */
    boolean inCombat;

    /** Whether the player can revive other players. */
    boolean revives;

    /** Whether the player is currently alive (not downed). */
    boolean alive;

    /** Whether the player is currently in a boss fight. */
    boolean inBossfight;

    /** Number of disciplinary warnings (Verweise) the player has received. */
    Integer verweise;

    /** The team this player belongs to. */
    Team team;

    /** Current step in an active dialogue sequence, null if no dialogue active. */
    Integer currentDialogueStep;

    /** The current dialogue lines being displayed to the player. */
    ArrayList<String> currentDialogue;

    /**
     * Creates a new PlayerMirror for the specified player.
     *
     * @param playerName the name of the player to create a mirror for
     */
    public PlayerMirror(String playerName) {
        this.playerName = playerName;
        inCombat = false;
        alive = true;
        verweise = 0;
        currentDialogueStep = null;
        currentDialogue = null;
    }

    // ==================== Reputation Methods ====================

    /**
     * Gets the player's reputation with a specific teacher.
     *
     * @param lehrer the teacher to check reputation with
     * @return the reputation value, or null if no reputation is set
     */
    public Double getReputation(Lehrer lehrer) {
        return rep.get(lehrer);
    }

    /**
     * Sets the player's reputation with a specific teacher.
     *
     * @param lehrer the teacher to set reputation for
     * @param reputation the new reputation value
     */
    public void setReputation(Lehrer lehrer, Double reputation) {
        rep.put(lehrer, reputation);
    }

    /**
     * Adds to the player's reputation with a specific teacher.
     * If no reputation exists, initializes it with the given value.
     *
     * @param lehrer the teacher to add reputation for
     * @param reputation the amount to add
     */
    public void addReputation(Lehrer lehrer, Double reputation) {
        try{
            rep.put(lehrer, rep.get(lehrer) + reputation);
        } catch (NullPointerException e) {
            rep.put(lehrer, reputation);
        }
    }

    // ==================== Team Methods ====================

    /**
     * Gets the team this player belongs to.
     *
     * @return the player's team, or null if not assigned
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Sets the team this player belongs to.
     *
     * @param team the team to assign the player to
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    // ==================== Combat Methods ====================

    /**
     * Checks if the player is currently in combat.
     *
     * @return true if the player is in combat, false otherwise
     */
    public boolean isInCombat() {
        return inCombat;
    }

    /**
     * Sets the player's combat status.
     *
     * @param combat true to mark the player as in combat, false otherwise
     */
    public void setCombat(boolean combat) {
        this.inCombat = combat;
    }

    // ==================== Alive Status Methods ====================

    /**
     * Checks if the player is currently alive (not downed).
     *
     * @return true if the player is alive, false if downed
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets the player's alive status.
     *
     * @param alive true if the player is alive, false if downed
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // ==================== Verweise (Warning) Methods ====================

    /**
     * Gets the number of disciplinary warnings the player has received.
     *
     * @return the number of warnings
     */
    public int getVerweise() {
        return this.verweise;
    }

    /**
     * Sets the number of disciplinary warnings for the player.
     *
     * @param verweise the new warning count
     */
    public void setVerweise(int verweise) {
        this.verweise = verweise;
    }

    /**
     * Adds to the player's warning count.
     *
     * @param i the number of warnings to add (can be negative to remove)
     */
    public void addVerweis(int i) {
        this.verweise += i;
    }

    // ==================== Revive Methods ====================

    /**
     * Checks if the player can revive other players.
     *
     * @return true if the player can revive, false otherwise
     */
    public boolean revives() {
        return revives;
    }

    /**
     * Sets whether the player can revive other players.
     *
     * @param revives true to allow reviving, false otherwise
     */
    public void setRevives(boolean revives) {
        this.revives = revives;
    }

    // ==================== Dialogue Methods ====================

    /**
     * Gets the current step in the active dialogue sequence.
     *
     * @return the current dialogue step, or null if no dialogue is active
     */
    public Integer getCurrentDialogueStep() {
        return currentDialogueStep;
    }

    /**
     * Gets the current dialogue lines.
     *
     * @return the list of dialogue lines, or null if no dialogue is active
     */
    public ArrayList<String> getCurrentDialogue() {
        return currentDialogue;
    }

    /**
     * Sets the current dialogue step.
     *
     * @param currentDialogueStep the step to set
     */
    public void setCurrentDialogueStep(Integer currentDialogueStep) {
        this.currentDialogueStep = currentDialogueStep;
    }

    /**
     * Sets the current dialogue lines.
     *
     * @param currentDialogue the dialogue lines to set
     */
    public void setCurrentDialogue(ArrayList<String> currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

    /**
     * Clears the current dialogue, ending any active conversation.
     */
    public void clearDialogue() {
        this.currentDialogue = null;
        this.currentDialogueStep = null;
    }

    // ==================== Bossfight Methods ====================

    /**
     * Checks if the player is currently in a boss fight.
     *
     * @return true if in a boss fight, false otherwise
     */
    public boolean isInBossfight() {
        return inBossfight;
    }

    /**
     * Sets whether the player is in a boss fight.
     *
     * @param inBossfight true if entering a boss fight, false otherwise
     */
    public void setInBossfight(boolean inBossfight) {
        this.inBossfight = inBossfight;
    }

    /**
     * Gets the name of the player this mirror represents.
     *
     * @return the player's name
     */
    public String getName() {
        return playerName;
    }
}
