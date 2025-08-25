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
import de.kuscheltiermafia.schoolwars.lehrer.Raum;
import de.kuscheltiermafia.schoolwars.mechanics.ParticleHandler;
import de.kuscheltiermafia.schoolwars.mechanics.PlayerStun;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.management.openmbean.OpenMBeanInfoSupport;

/**
 * Handles various player interaction events to maintain game integrity.
 * Prevents unauthorized block manipulation, inventory interactions,
 * and protects decorative elements like item frames and paintings.
 */
public class InteractionEvent implements Listener {

    /**
     * Prevents players from clicking spacer items in inventories and handles pagination
     * for the item list GUI. Allows navigation between different pages of items.
     * 
     * @param e The InventoryClickEvent triggered when a player clicks in an inventory
     */
    @EventHandler
    public void clickSpacer(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        try {
            // Cancel interaction with spacer items (UI filler items)
            if (e.getCurrentItem().equals(Items.spacer)) {
                e.setCancelled(true);
                
            // Handle page up navigation in item list
            }else if(e.getCurrentItem().equals(Items.page_up) && e.getWhoClicked().getOpenInventory().getTitle().equals("§4Itemlist")) {
                e.setCancelled(true);
                p.getInventory().remove(Items.page_up);
                ItemList.itemListPage.put(p, ItemList.itemListPage.get(p) + 1);
                ItemList.fillItemlist(e.getInventory(), ItemList.itemListPage.get(p), p);
                
            // Handle page down navigation in item list
            }else if(e.getCurrentItem().equals(Items.page_down) && e.getWhoClicked().getOpenInventory().getTitle().equals("§4Itemlist")) {
                e.setCancelled(true);
                p.getInventory().remove(Items.page_down);
                ItemList.itemListPage.put(p, ItemList.itemListPage.get(p) - 1);
                ItemList.fillItemlist(e.getInventory(), ItemList.itemListPage.get(p), p);
                
            // Prevent interaction with disabled navigation buttons and page indicators
            }else if(e.getCurrentItem().equals(Items.no_page_down) || e.getCurrentItem().equals(Items.no_page_up) || e.getCurrentItem().equals(new ItemStack(Items.createItem(Material.BOOK, ChatColor.DARK_RED + "Current Page: " + ItemList.itemListPage.get(p), 20, 1, null, false, false, false)))) {
                e.setCancelled(true);
            }
        }catch (Exception ignored){}
    }

    /**
     * Prevents players from breaking blocks unless they are in creative mode.
     * This maintains the integrity of the game world and prevents griefing.
     * 
     * @param e The BlockBreakEvent triggered when a player tries to break a block
     */
    @EventHandler
    public void OnBreakBlock(BlockBreakEvent e){
        Player p = e.getPlayer();
        // Only allow block breaking for creative mode players (admins/builders)
        if (p.getGameMode() != GameMode.CREATIVE){
            e.setCancelled(true);
        }
    }

    /**
     * Prevents players from placing blocks unless they are in creative mode.
     * This maintains the integrity of the game world and prevents unauthorized building.
     * 
     * @param e The BlockPlaceEvent triggered when a player tries to place a block
     */
    @EventHandler
    public void OnPlaceBlock(BlockPlaceEvent e){
        Player p = e.getPlayer();
        // Only allow block placing for creative mode players (admins/builders)
        if (p.getGameMode() != GameMode.CREATIVE){
            e.setCancelled(true);
        }
    }

    /**
     * Prevents item frames from being destroyed by players unless they are in creative mode.
     * Item frames are used for decoration and game functionality.
     * 
     * @param e The HangingBreakByEntityEvent triggered when an entity tries to break a hanging entity
     */
    @EventHandler
    public void onBreakItemFrame(HangingBreakByEntityEvent e) {
        if (e.getEntity() instanceof ItemFrame && ((Player) e.getRemover()).getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    //Prevent players from taking items out of item frames
    @EventHandler
    public void onDamageItemFrame(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof ItemFrame && ((Player) e.getDamager()).getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    //Prevent Paintings from being destroyed
    @EventHandler
    public void onBreakPainting(HangingBreakByEntityEvent e) {
        if (e.getEntity() instanceof Painting && ((Player) e.getRemover()).getGameMode() != GameMode.CREATIVE) {
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

    /*
Brot hier, willkommen zu einem neuem GROOVETUBE VIDEO. Heute BROTEN WIR mein riesiges Brot, man könnte es auch *****, das wäre aber etwas brot (aka gay)
 Mein Bro heißt BERND das Brot, was ein ziemlich schöner Name ist. Aber ich heiße ANTON VIVALDI KLEMMT IM BROT™, ich weiß, ein viel schöneren Namen.
 **** KiKa, ich bin viel cooler und grooovicker, warum haben die diesen blöden narzasistischen Bernd genommen.
 Also leute, wenn ihr mik supporten wollt, folgt mir einfach ma auf Forza, dann können ma auch ma zusammen ein groovickes Rennen fahren.
 Aber ihr werdet nie gewinnen, weil wie alle kennen bin ich ANTON VIVALDI KLEMMT IM BROT™ der beste im KLATSCHEN! Oder war es CLUTCHEN? Ach egal, ich brotshippe jetzt.
 Denn alle 11 Minuten verbrennt ein Brot wegen Bernd, also lasst uns alle zusammenhalten und ANTON VIVALDI KLEMMT IM BROT supporten.
 OH! Na sie mal einer an! Das ist ja ein schönes Dinkelbrot!😏 Für das würde ich verbrennen ... Aber meine Mama hat mich immer gelehrt. Brenne nie für ein Dinkelbrot.
Aber wenn ich mir so die Bilder von diesem Dinkelbrot anschaue, bemerkte ich, dass sie einen 10/10 BRAYT HAT! Also brottete ich sie, bis ich nicht mehr konnte.
 Und so kamen ANTON VIVALDI KLEMMT IM BROT und das liebe Dinkelbrot zusammen. Happy End!
 Bis die Nutella kam ... TO BE BROTINUED

*nebenstory folgt also so übergang hahahahahahahahahar*
YO LEUTE, TOMATE HIER! Heute hier auf meinem Podcast auf Spotomat hab ich meinen Brod ANTON VIVALDI KLEMMT IM BROT™, heute reden wir über CAPREEEEEESE!!!!
Wir ihr alle wissen ist es der Traum von jeder Tomate einmal mit einem nassen😏 Mozarella zusammenzukummen und gegessen zu werden. In Fachsprache heißt dies CAPRESEEEEEE!!!

     Die Nutella betratt den Raum... Nutella: "Ich werde dich beschmieren👿" Sofort warf die Nutella ihr braunes Goo auf das Brot!
     ABA im letzten Moment betratt die WINDOWS DEFENSE MECHANISM FIREWALL DEFENDER
https://youtu.be/FuCvyB1FvF4

@EventHandler
public void JulisuCapped(JuliusCapEvent e) { //<--- Ik bin nik Julisu
    e.setCancelled(true);
    Bukkit.broadcastMessage("Julius wollte capen, aber er wurde gecappt");}

ANTON HAT EINEN KLEINEN OpenMBeanInfoSupport#
ICH BIN EIN KRASSER JavaxSecurityAuthKerberosAccess
warum ist mein cursor ein fucking lightbulb huhuhuhu

Es war einmal ein Julius in einem Teich!
Er fiel in den Teich!
Er starb!
*/


    
}
