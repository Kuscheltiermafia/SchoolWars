package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.PlayerMirror;
import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.Team;
import de.kuscheltiermafia.schoolwars.commands.Debug;
import de.kuscheltiermafia.schoolwars.lehrer.Lehrer;
import de.kuscheltiermafia.schoolwars.lehrer.Stundenplan;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.*;

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
            for (Item item : WORLD.getEntitiesByClass(Item.class)) {
                item.remove();
            }
        } catch (Exception ignored) {}

        try {
            for (Interaction interaction : WORLD.getEntitiesByClass(Interaction.class)) {
                interaction.remove();
            }
        } catch (Exception ignored) {}

        try {
            for (Villager villager : WORLD.getEntitiesByClass(Villager.class)) {
                villager.remove();
            }
        }catch (Exception ignored) {}

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
