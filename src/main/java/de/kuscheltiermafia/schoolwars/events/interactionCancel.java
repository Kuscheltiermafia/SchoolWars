package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.Ranzen;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;



public class interactionCancel implements Listener {

    @EventHandler
    public void clickSpacer(InventoryClickEvent e) {
        if(e.getWhoClicked() instanceof Player) {
            ItemStack item = e.getCurrentItem();
            if (item != null) {
                if (item.hasItemMeta()) {
                    if(item.getItemMeta().getCustomModelData() == 2) {
                    e.setCancelled(true);
                    }else{return;}
                }else{return;}
            }else{return;}
        }else{return;}
    }

    @EventHandler
    public void OnBreakBlock(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (!p.isOp()){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§4Das darfst du nicht!"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void OnPlaceBlock(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if (!p.isOp()){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§4Das darfst du nicht!"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDismount(EntityDismountEvent e){
        if (e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if (e.getDismounted() instanceof Bat){
                e.setCancelled(true);
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

    @EventHandler
    public void onRanzenPickup(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        Entity ranzen = e.getRightClicked();

        Bukkit.broadcastMessage("Player: " + p + " Ranzen: " + ranzen);

        if(ranzen.getCustomName().equals("§2Grüner Ranzen")) {
            ranzen.remove();
            Ranzen.giveRanzen("naturwissenschaftler", p);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen aufgehoben!"));
            e.setCancelled(true);
        }else {Bukkit.broadcastMessage("Nope x1");}
        if(ranzen.getCustomName().equals("§6Gelber Ranzen")) {
            ranzen.remove();
            Ranzen.giveRanzen("sprachler", p);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen aufgehoben!"));
            e.setCancelled(true);
        }else {Bukkit.broadcastMessage("Nope x2");}
        if(ranzen.getCustomName().equals("§4Roter Ranzen")) {
            ranzen.remove();
            Ranzen.giveRanzen("sportler", p);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen aufgehoben!"));
            e.setCancelled(true);
        }else {Bukkit.broadcastMessage("Nope x3");}
    }
}
