package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

/**
 * Handles timed block interactions with progress feedback.
 * <p>
 * Displays a progress bar in the action bar and plays sounds at intervals
 * while a player interacts with a block over a duration.
 * </p>
 */
public class BlockInteraction {

    public static void progressBlock(Block b, int duration, String actionBarText, String sound, int soundInverval) {
        Location loc = b.getLocation();


        for (int i = 0; i < duration; i++) {
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    double calcProgress = (double) finalI / duration * 100;

                    Collection<Entity> nearbyPlayers = loc.getWorld().getNearbyEntities(loc, 5, 5, 5);

                    for (Entity p : nearbyPlayers) {
                        if (p instanceof Player) {
                            ((Player) p).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "-- " + actionBarText + ": " + ProgressBarHandler.progressBarsUpdate(calcProgress, ChatColor.DARK_GREEN) + ChatColor.GRAY + " --"));
                            if (finalI % soundInverval == 0) {
                                ((Player) p).playSound(loc, sound, SoundCategory.BLOCKS, 100, 1);
                            }
                        }
                    }
                }
            }.runTaskLater(SchoolWars.getPlugin(), finalI);
        }
    }

}
