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

package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.commands.ItemList;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.ParticleHandler;
import de.kuscheltiermafia.schoolwars.mechanics.PlayerStun;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;



public class InteractionEvent implements Listener {

//Prevent players from clicking spacers, allow moving between UI pages
    @EventHandler
    public void clickSpacer(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        try {
            if (e.getCurrentItem().equals(Items.spacer)) {
                e.setCancelled(true);
            }else if(e.getCurrentItem().equals(Items.page_up)) {
                e.setCancelled(true);
                p.getInventory().remove(Items.page_up);
                ItemList.itemListPage.put(p, ItemList.itemListPage.get(p) + 1);
                ItemList.fillItemlist(e.getInventory(), ItemList.itemListPage.get(p), p);
            }else if(e.getCurrentItem().equals(Items.page_down)) {
                e.setCancelled(true);
                p.getInventory().remove(Items.page_down);
                ItemList.itemListPage.put(p, ItemList.itemListPage.get(p) - 1);
                ItemList.fillItemlist(e.getInventory(), ItemList.itemListPage.get(p), p);
            }else if(e.getCurrentItem().equals(Items.no_page_down) || e.getCurrentItem().equals(Items.no_page_up) || e.getCurrentItem().equals(new ItemStack(Items.createItem(Material.BOOK, ChatColor.DARK_RED + "Current Page: " + ItemList.itemListPage.get(p), 20, 1, null, false, false, false)))) {
                e.setCancelled(true);
            }
        }catch (Exception ignored){}
    }

//Prevent Players from breaking Blocks
    @EventHandler
    public void OnBreakBlock(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE){
            //p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§4Das darfst du nicht!"));
            e.setCancelled(true);
        }
    }

//Prevent Players from placing Blocks
    @EventHandler
    public void OnPlaceBlock(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE){
            //p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§4Das darfst du nicht!"));
            e.setCancelled(true);
        }
    }

    //Prevent Itemframe from being destroyed
    @EventHandler
    public void onDamageItemFrame(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof ItemFrame) {
            e.setCancelled(true);
        }
    }

    //Minas Stuhl hit
    @EventHandler
    public void OnStuhlHit(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player hitter && e.getEntity() instanceof Player hit) {

            if(hitter.getInventory().getItemInMainHand().equals(Items.buffed_stuhl) && hitter.getCooldown(Material.OAK_STAIRS) < 2) {
                hitter.setCooldown(Material.OAK_STAIRS, 20 * 6);

                ParticleHandler.createParticles(hit.getLocation().add(0, 1, 0), Particle.CRIT, 20, 0.2, true, null);
                ParticleHandler.createParticles(hit.getLocation().add(0, 1, 0), Particle.CLOUD, 30, 0.2, true, null);
                PlayerStun.stunPlayer(hit, 3, true);
            }
        }
    }

//Brot hier, willkommen zu einem neuem GROOVETUBE VIDEO. Heute BROTEN WIR mein riesiges Brot, man könnte es auch *****, das wäre aber etwas brot (aka gay)
// Mein Bro heißt BERND das Brot, was ein ziemlich schöner Name ist. Aber ich heiße ANTON VIVALDI KLEMMT IM BROT™, ich weiß, ein viel schöneren Namen.
// **** KiKa, ich bin viel cooler und grooovicker, warum haben die diesen blöden narzasistischen Bernd genommen.
// Also leute, wenn ihr mik supporten wollt, folgt mir einfach ma auf Forza, dann können ma auch ma zusammen ein groovickes Rennen fahren.
// Aber ihr werdet nie gewinnen, weil wie alle kennen bin ich ANTON VIVALDI KLEMMT IM BROT™ der beste im KLATSCHEN! Oder war es CLUTCHEN? Ach egal, ich brotshippe jetzt.
// Denn alle 11 Minuten verbrennt ein Brot wegen Bernd, also lasst uns alle zusammenhalten und ANTON VIVALDI KLEMMT IM BROT supporten.
// OH! Na sie mal einer an! Das ist ja ein schönes Dinkelbrot!😏 Für das würde ich verbrennen ... Aber meine Mama hat mich immer gelehrt. Brenne nie für ein Dinkelbrot.
//Aber wenn ich mir so die Bilder von diesem Dinkelbrot anschaue, bemerkte ich, dass sie einen 10/10 BRAYT HAT! Also brottete ich sie, bis ich nicht mehr konnte.
// Und so kamen ANTON VIVALDI KLEMMT IM BROT und das liebe Dinkelbrot zusammen. Happy End!
// Bis die Nutella kam ... TO BE BROTINUED
    //
//*nebenstory folgt also so übergang hahahahahahahahahar*
//YO LEUTE, TOMATE HIER! Heute hier auf meinem Podcast auf Spotomat hab ich meinen Brod ANTON VIVALDI KLEMMT IM BROT™, heute reden wir über CAPREEEEEESE!!!!
//Wir ihr alle wissen ist es der Traum von jeder Tomate einmal mit einem nassen😏 Mozarella zusammenzukummen und gegessen zu werden. In Fachsprache heißt dies CAPRESEEEEEE!!!
//
    // Die Nutella betratt den Raum... Nutella: "Ich werde dich beschmieren👿" Sofort warf die Nutella ihr braunes Goo auf das Brot!
    // ABA im letzten Moment betratt die WINDOWS DEFENSE MECHANISM FIREWALL DEFENDER
//https://youtu.be/FuCvyB1FvF4
//
//@EventHandler
//public void JulisuCapped(JuliusCapEvent e) { //<--- Ik bin nik Julisu
//    e.setCancelled(true);
//    Bukkit.broadcastMessage("Julius wollte capen, aber er wurde gecappt");}
////
//ANTON HAT EINEN KLEINEN OpenMBeanInfoSupport#
//ICH BIN EIN KRASSER JavaxSecurityAuthKerberosAccess
//warum ist mein cursor ein fucking lightbulb huhuhuhu

//Es war einmal ein Julius in einem Teich!
//Er fiel in den Teich!
//Er starb!



    
}
