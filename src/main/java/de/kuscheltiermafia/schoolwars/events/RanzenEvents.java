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

package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.Ranzen;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class RanzenEvents implements Listener {

    @EventHandler
    public void onRanzenPickup(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        Entity ranzen = e.getRightClicked();

        if(ranzen.hasMetadata("naturwissenschaftler")) {
            if(Teams.naturwissenschaftler.contains(p.getName())) {
                if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                    Entity display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                    display.remove();
                }
                ranzen.remove();
                Ranzen.giveRanzen("naturwissenschaftler", p);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen aufgehoben!"));
                e.setCancelled(true);
            }else if(!Teams.naturwissenschaftler.contains(p.getName())) {
                Ranzen.destroyRanzen(p, SchoolWars.nameNaturwissenschaftler, ranzen.getLocation());
                if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                    Entity display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                    display.remove();
                }
                ranzen.remove();
            }
        }
        if(ranzen.hasMetadata("sprachler")) {
            if(Teams.sprachler.contains(p.getName())) {
                if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                    Entity display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                    display.remove();
                }
                ranzen.remove();
                Ranzen.giveRanzen("sprachler", p);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen aufgehoben!"));
                e.setCancelled(true);
            }else if(!Teams.sprachler.contains(p.getName())) {
                Ranzen.destroyRanzen(p, SchoolWars.nameSprachler, ranzen.getLocation());
                if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                    Entity display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                    display.remove();
                }
                ranzen.remove();
            }
        }
        if(ranzen.hasMetadata("sportler")) {
            if(Teams.sportler.contains(p.getName())) {
                if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                    BlockDisplay display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                    display.remove();
                }
                ranzen.remove();
                Ranzen.giveRanzen("sportler", p);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen aufgehoben!"));
                e.setCancelled(true);
            }else if(!Teams.sportler.contains(p.getName())) {
                Ranzen.destroyRanzen(p, SchoolWars.nameSportler, ranzen.getLocation());
                if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                    Entity display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                    display.remove();
                }
                ranzen.remove();
            }
        }
    }

    @EventHandler
    public void onRanzenPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        ItemStack ranzen = e.getItemInHand();
        if(ranzen.equals(Items.nws_ranzen)) {
            Ranzen.createRanzen("naturwissenschaftler", e.getBlock().getLocation());
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen plaziert!"));
            e.setCancelled(true);
        }
        if(ranzen.equals(Items.sprach_ranzen)) {
            Ranzen.createRanzen("sprachler", e.getBlock().getLocation());
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen plaziert!"));
            e.setCancelled(true);
        }
        if(ranzen.equals(Items.sport_ranzen)) {
            Ranzen.createRanzen("sportler", e.getBlock().getLocation());
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen plaziert!"));
            e.setCancelled(true);
        }
    }

}
