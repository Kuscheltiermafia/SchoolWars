package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.Team;
import de.kuscheltiermafia.schoolwars.items.Items;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import static de.kuscheltiermafia.schoolwars.win_conditions.Ranzen.*;

public class RanzenEvents implements Listener {

    @EventHandler
    public void onRanzenPickup(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (!(e.getRightClicked() instanceof Interaction ranzen)) return;

        for (String metadata : ranzenMetadatas) {
            if (ranzen.hasMetadata(metadata)) {
                ranzenPickup(p, ranzen, e);
            }
        }

    }

    @EventHandler
    public void onRanzenPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        ItemStack ranzen = e.getItemInHand();
        if (Items.ranzenList.contains(ranzen)) {
            if (!SchoolWars.gameStarted) {
                p.sendMessage(ChatColor.RED + "Ranzen können nur während dem Spiel platziert werden!");
                e.setCancelled(true);
                return;
            }
            BlockState replcaedState =  e.getBlockReplacedState();
            if(replcaedState.getType() != Material.AIR && replcaedState.getType() != Material.CAVE_AIR && replcaedState.getType() != Material.VOID_AIR) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "Du kannst hier nichts platzieren!");
                return;
            }

            for (Team team : Team.values()) {
                if (ranzen.equals(team.ranzen_item)) {
                    createRanzen(team, e.getBlock().getLocation());
                    p.getInventory().setItem(e.getHand(), new ItemStack(Material.AIR));
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen plaziert!"));
                    e.setCancelled(true);
                }
            }
        }
    }

}
