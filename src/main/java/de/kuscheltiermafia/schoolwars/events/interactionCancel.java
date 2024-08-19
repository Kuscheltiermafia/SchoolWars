package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.gameprep.Teams;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.Ranzen;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;



public class interactionCancel implements Listener {

    @EventHandler
    public void clickSpacer(InventoryClickEvent e) {
        if(e.getWhoClicked() instanceof Player) {
            ItemStack item = e.getCurrentItem();
            if (item != null) {
                if (!item.getItemMeta().equals(null)) {
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
                Ranzen.destroyRanzen(p, ChatColor.GREEN + "naturwissenschaftler", ranzen.getLocation());
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
                Ranzen.destroyRanzen(p, ChatColor.GOLD + "sprachler", ranzen.getLocation());
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
                Ranzen.destroyRanzen(p, ChatColor.DARK_RED + "sportler", ranzen.getLocation());
                if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                    Entity display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                    display.remove();
                }
                ranzen.remove();
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (RevivePlayer.deadPlayers.containsKey(e.getPlayer().getName())) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onPunch(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (RevivePlayer.deadPlayers.containsKey(e.getDamager().getName())) {
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onClickItemframe(PlayerInteractEntityEvent e){

        Player p = e.getPlayer();
        Entity target = e.getRightClicked();
        Location loc = target.getLocation();



        if (target instanceof ItemFrame && loc.getBlock().getLocation().equals(new Location(loc.getWorld(), -31, 88, 143))){
            e.setCancelled(true);
            p.getInventory().addItem(new ItemStack(Items.kuehlpack));
        }

    }
}
