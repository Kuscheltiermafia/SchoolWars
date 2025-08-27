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

public class PlayerMirror {

    public static HashMap<String, PlayerMirror> playerMirror = new HashMap<>();

    String playerName;
    HashMap<Lehrer, Double> rep = new HashMap<>();

    boolean inCombat;
    boolean revives;
    boolean alive;
    boolean inBossfight;
    Integer verweise;
    Team team;
    Integer currentDialogueStep;
    ArrayList<String> currentDialogue;

    public PlayerMirror(String playerName) {
        this.playerName = playerName;
        inCombat = false;
        alive = true;
        verweise = 0;
        currentDialogueStep = null;
        currentDialogue = null;
    }

//reputation
    public Double getReputation(Lehrer lehrer) {
        return rep.get(lehrer);
    }

    public void setReputation(Lehrer lehrer, Double reputation) {
        rep.put(lehrer, reputation);
    }

    public void addReputation(Lehrer lehrer, Double reputation) {
        try{
            rep.put(lehrer, rep.get(lehrer) + reputation);
        } catch (NullPointerException e) {
            rep.put(lehrer, reputation);
        }
    }

//team
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

//combat
    public boolean isInCombat() {
        return inCombat;
    }

    public void setCombat(boolean combat) {
        this.inCombat = combat;
    }

//alive
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

//verweise
    public int getVerweise() {
        return this.verweise;
    }

    public void setVerweise(int verweise) {
        this.verweise = verweise;
    }

    public void addVerweis(int i) {
        this.verweise += i;
    }

//revives
    public boolean revives() {
        return revives;
    }

    public void setRevives(boolean revives) {
        this.revives = revives;
    }

//dialogue
    public Integer getCurrentDialogueStep() {
        return currentDialogueStep;
    }

    public ArrayList<String> getCurrentDialogue() {
        return currentDialogue;
    }

    public void setCurrentDialogueStep(Integer currentDialogueStep) {
        this.currentDialogueStep = currentDialogueStep;
    }

    public void setCurrentDialogue(ArrayList<String> currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

    public void clearDialogue() {
        this.currentDialogue = null;
        this.currentDialogueStep = null;
    }

//bossfight
    public boolean isInBossfight() {
        return inBossfight;
    }

    public void setInBossfight(boolean inBossfight) {
        this.inBossfight = inBossfight;
    }

    public String getName() {
        return playerName;
    }
}
