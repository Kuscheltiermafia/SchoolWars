package de.kuscheltiermafia.schoolwars.reputation;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerRepModel {

    String playerName;
    HashMap<String, Double> rep = new HashMap<>();

    public PlayerRepModel(String playerName) {
        this.playerName = playerName;
    }

    public void setReputation(String lehrer, Double reputation) {
        rep.put(lehrer, reputation);
    }

    public Double getReputation(String lehrer) {
        return rep.get(lehrer);
    }

    public void addReputation(String lehrer, Double reputation) {
        try{
            rep.put(lehrer, rep.get(lehrer) + reputation);
        } catch (NullPointerException e) {
            rep.put(lehrer, reputation);
        }
    }

}
