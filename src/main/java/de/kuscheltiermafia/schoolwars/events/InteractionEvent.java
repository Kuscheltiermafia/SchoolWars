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

import de.kuscheltiermafia.schoolwars.commands.ItemList;
import de.kuscheltiermafia.schoolwars.items.Items;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;


public class InteractionEvent implements Listener {

//Prevent players from clicking spacers, allow moving between UI pages
    @EventHandler
    public void clickSpacer(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        try {
            if (e.getCurrentItem().equals(Items.spacer)) {
                e.setCancelled(true);
            }else if(e.getCurrentItem().equals(Items.page_up)) {
                Bukkit.broadcastMessage("page " + ItemList.page);
                ItemList.page++;
                ItemList.fillItemlist(e.getInventory(), ItemList.page);
                e.setCancelled(true);
            }else if(e.getCurrentItem().equals(Items.page_down)) {
                Bukkit.broadcastMessage("page " + ItemList.page);
                ItemList.page--;
                ItemList.fillItemlist(e.getInventory(), ItemList.page);
                e.setCancelled(true);
                Bukkit.broadcastMessage("page " + ItemList.page);
            }
        }catch (Exception ignored){}
    }

//Prevent Players from breaking Blocks
    @EventHandler
    public void OnBreakBlock(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (!p.isOp()){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§4Das darfst du nicht!"));
            e.setCancelled(true);
        }
    }

//Prevent Players from placing Blocks
    @EventHandler
    public void OnPlaceBlock(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if (!p.isOp()){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§4Das darfst du nicht!"));
            e.setCancelled(true);
        }
    }


//Prevent dead Players from standing up
    @EventHandler
    public void onDismount(EntityDismountEvent e){
        if (e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if (e.getDismounted() instanceof Bat){
                e.setCancelled(true);
            }
        }
    }


//Prevent dead Players from interacting
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (RevivePlayer.deadPlayers.containsKey(e.getPlayer().getName())) {
            e.setCancelled(true);
        }

    }

//Prevent dead Players from hitting
    @EventHandler
    public void onPunch(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (RevivePlayer.deadPlayers.containsKey(e.getDamager().getName())) {
                e.setCancelled(true);
            }
        }
    }


    //Prevent Itemframe from being destroyed
    @EventHandler
    public void onDamageItemFrame(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof ItemFrame) {
            e.setCancelled(true);
        }
    }



    
}
