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

package de.kuscheltiermafia.schoolwars.player_mirror;

import de.kuscheltiermafia.schoolwars.lehrer.Lehrer;

import java.util.HashMap;

public class PlayerMirror {

    String playerName;
    HashMap<Lehrer, Double> rep = new HashMap<>();

    boolean inCombat;
    boolean revives;
    boolean alive;
    Integer verweise;

    public PlayerMirror(String playerName) {
        this.playerName = playerName;
        inCombat = false;
        alive = true;
        verweise = 0;
    }

//reputation
    public Double getReputation(String lehrer) {
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

}
