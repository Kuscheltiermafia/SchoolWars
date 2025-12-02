package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.mechanics.StartGame;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class StartGameEvent implements Listener {

    Location startButtonLocation = new Location(SchoolWars.WORLD, -32.0, 81.0, 179.0);

    @EventHandler
    public void onPressStartButton(PlayerInteractEvent event) {
        try {
            if (event.getClickedBlock().getLocation().equals(startButtonLocation)) {
                StartGame.openGUI(event.getPlayer(), false);
            }
        }catch (NullPointerException ignored) {
        }
    }

}
