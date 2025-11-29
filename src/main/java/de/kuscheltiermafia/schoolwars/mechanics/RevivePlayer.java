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

package de.kuscheltiermafia.schoolwars.mechanics;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

/**
 * Handles player revival mechanics.
 * <p>
 * When a player is downed, they are mounted on an invisible bat to prevent movement.
 * This class provides functionality to revive those players, restoring their ability to play.
 * </p>
 */
public class RevivePlayer {

    /** Maps player names to the UUID of their mount bat entity. */
    public static HashMap<String, UUID> playerBatMap = new HashMap<>();

    /**
     * Revives a downed player.
     * <p>
     * This method:
     * <ul>
     *   <li>Removes the invisible bat mount</li>
     *   <li>Clears debuff potion effects</li>
     *   <li>Restores full health with brief invulnerability</li>
     *   <li>Notifies both the reviver and the revived player</li>
     * </ul>
     * </p>
     *
     * @param player the player performing the revival
     * @param target the downed player to revive
     */
    public static void revivePlayer(Player player, Player target) {

        if (playerMirror.get(target.getName()).isAlive()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Dieser Spieler ist nicht tot und kann nicht wiederbelebt werden."));
            return;
        }

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "Du hast " + target.getName() + " wiederbelebt."));

        Bukkit.getEntity(playerBatMap.get(target.getName())).remove();
        target.teleport(target.getLocation().add(0, 1, 0));
        target.removePotionEffect(PotionEffectType.SLOWNESS);
        target.removePotionEffect(PotionEffectType.RESISTANCE);
        target.setHealth(20);
        target.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 255, true, true, true));

        playerBatMap.remove(target.getName());

        playerMirror.get(target.getName()).setAlive(true);

        target.sendTitle("§aDu wurdest wiederbelebt!", player.getDisplayName() + ChatColor.GRAY + " hat dir geholfen", 10, 70, 20);
    }
}
