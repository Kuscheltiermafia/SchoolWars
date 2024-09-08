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

import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.Ranzen;
import de.kuscheltiermafia.schoolwars.teams.Team;
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

import static de.kuscheltiermafia.schoolwars.SchoolWars.*;
import static de.kuscheltiermafia.schoolwars.mechanics.Ranzen.ranzenAmount;

public class RanzenEvents implements Listener {

    @EventHandler
    public void onRanzenPickup(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        Entity ranzen = e.getRightClicked();

        if(ranzen.hasMetadata("naturwissenschaftler")) {
            if(Team.NWS.mitglieder.contains(p.getName())) {
                if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                    Entity display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                    display.remove();
                }
                ranzen.remove();
                p.getInventory().addItem(new ItemStack(Items.nws_ranzen));
                ranzenAmount.put(Team.NWS.teamName, ranzenAmount.get(Team.NWS.teamName) + 1);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen aufgehoben!"));
                e.setCancelled(true);
            }else if(!Team.NWS.mitglieder.contains(p.getName())) {
                Ranzen.destroyRanzen(p, Team.NWS.teamName, ranzen.getLocation());
                if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                    Entity display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                    display.remove();
                }
                ranzen.remove();
            }
        }
        if(ranzen.hasMetadata("sprachler")) {
            if(Team.SPRACHLER.mitglieder.contains(p.getName())) {
                if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                    Entity display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                    display.remove();
                }
                ranzen.remove();
                p.getInventory().addItem(new ItemStack(Items.sprach_ranzen));
                ranzenAmount.put(Team.SPRACHLER.teamName, ranzenAmount.get(Team.SPRACHLER.teamName) + 1);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen aufgehoben!"));
                e.setCancelled(true);
            }else if(!Team.SPRACHLER.mitglieder.contains(p.getName())) {
                Ranzen.destroyRanzen(p, Team.SPRACHLER.teamName, ranzen.getLocation());
                if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                    Entity display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                    display.remove();
                }
                ranzen.remove();
            }
        }
        if(ranzen.hasMetadata("sportler")) {
            if(Team.SPORTLER.mitglieder.contains(p.getName())) {
                if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                    BlockDisplay display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                    display.remove();
                }
                ranzen.remove();
                p.getInventory().addItem(new ItemStack(Items.sport_ranzen));
                ranzenAmount.put(Team.SPORTLER.teamName, ranzenAmount.get(Team.SPORTLER.teamName) + 1);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen aufgehoben!"));
                e.setCancelled(true);
            }else if(!Team.SPORTLER.mitglieder.contains(p.getName())) {
                Ranzen.destroyRanzen(p, Team.SPORTLER.teamName, ranzen.getLocation());
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
