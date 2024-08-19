package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.items.Items;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RevivePlayer implements Listener {

    static HashMap<String, UUID> deadPlayers = new HashMap<>();

    @EventHandler
    public static void onClickPlayer(PlayerInteractAtEntityEvent e) {

        if (e.getRightClicked() instanceof Player) {

            Player player = e.getPlayer();
            Player target = (Player) e.getRightClicked();

            if (target.getLocation().distance(player.getLocation()) <= 3 && deadPlayers.containsKey(target.getName()) && player.getInventory().getItemInMainHand().getItemMeta().getItemName().equals("§bKühlpack") && !deadPlayers.containsKey(player.getName()) ) {

                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "Du hast " + target.getName() + " wiederbelebt."));

                Bukkit.getEntity(deadPlayers.get(target.getName())).remove();
                target.teleport(target.getLocation().add(0, 1, 0));
                target.removePotionEffect(PotionEffectType.SLOWNESS);
                target.removePotionEffect(PotionEffectType.RESISTANCE);
                target.setHealth(20);
                target.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 255, true, true, true));

                deadPlayers.remove(target.getName());

                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

                target.sendTitle("§aDu wurdest wiederbelebt!", player.getDisplayName() + ChatColor.GRAY + " hat dir geholfen", 10, 70, 20);

            }
        }

    }

}
