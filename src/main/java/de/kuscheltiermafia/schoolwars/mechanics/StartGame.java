package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.PlayerMirror;
import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.Team;
import de.kuscheltiermafia.schoolwars.commands.Debug;
import de.kuscheltiermafia.schoolwars.lehrer.Lehrer;
import de.kuscheltiermafia.schoolwars.lehrer.Stundenplan;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;
import static de.kuscheltiermafia.schoolwars.SchoolWars.WORLD;

public class StartGame {

    public static boolean start() {

        if (!Debug.startWorks || SchoolWars.gameStarted) {
            return false;
        }

//reset
        playerMirror.clear();

        try {
            for (Item item : Bukkit.getWorld("schoolwars").getEntitiesByClass(Item.class)) {
                item.remove();
            }
        } catch (Exception ignored) {}

        for (Entity entity : WORLD.getEntities()) {
            if (entity instanceof Villager){
                entity.remove();
            }
        }

//prepare game

        Stundenplan.updateStundenplan(true);
        Stundenplan.updateStundenplan(false);

        Lehrer.updateLehrerPosition(false);

//Set Playernames and ready them for battle
        Team.clearTeams();
        for (Player p : Bukkit.getOnlinePlayers()) {
            Team.resetPlayer(p);
            p.getInventory().clear();
            p.setGameMode(GameMode.SURVIVAL);
            p.setHealth(20);
            p.getActivePotionEffects().clear();
        }

//prepare player Mirrors
        playerMirror.clear();
        for (Player p : Bukkit.getOnlinePlayers()) {
            playerMirror.put(p.getName(), new PlayerMirror(p.getName()));
        }

//prepare Teams
        Team.joinTeams();
        Team.NWS.prepare();
        Team.SPORTLER.prepare();
        Team.SPRACHLER.prepare();

//revive Player
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!playerMirror.get(p.getName()).isAlive()) {
                RevivePlayer.revivePlayer(p, p);
            }
        }

        SchoolWars.gameStarted = true;
        return true;
    }

}
