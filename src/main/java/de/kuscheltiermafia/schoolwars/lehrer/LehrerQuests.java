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

package de.kuscheltiermafia.schoolwars.lehrer;

import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.ParticleHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

public class LehrerQuests implements Listener {

    static int[] questSpacers = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 17, 18, 22, 27, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 17, 26, 35, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
    static int[] itemsIndicator = new int[]{10, 11, 12, 14, 15, 16, 19, 21, 23, 25, 28, 29, 30, 32, 33, 34};

    public HashMap<Player, Villager> openQuest = new HashMap<>();

    public static HashMap<Lehrer, ItemStack> requieredLehrerItems = new HashMap<>();
    public static HashMap<Lehrer, ItemStack> rewardLehrerItems = new HashMap<>();
    public static HashMap<Lehrer, Double> repReward = new HashMap<>();
    public static ArrayList<Villager> questLehrerList = new ArrayList<>();

    public static void initLehrerQuests() {
        requieredLehrerItems.put(Lehrer.FISCHER, Items.stroke_master);
        rewardLehrerItems.put(Lehrer.FISCHER, Items.fischers_spiel);
        repReward.put(Lehrer.FISCHER, 1.0);

        requieredLehrerItems.put(Lehrer.SCHNEIDER, Items.kaputtes_ipad);
        rewardLehrerItems.put(Lehrer.SCHNEIDER, Items.rollator);
        repReward.put(Lehrer.SCHNEIDER, 1.0);

        requieredLehrerItems.put(Lehrer.BAAR, Items.baar_kaffee);
        rewardLehrerItems.put(Lehrer.BAAR, Items.useless_uranium);
        repReward.put(Lehrer.BAAR, 1.0);

        requieredLehrerItems.put(Lehrer.KLIEM, Items.placeholder);
        rewardLehrerItems.put(Lehrer.KLIEM, Items.placeholder);
        repReward.put(Lehrer.KLIEM, 0.0);

        requieredLehrerItems.put(Lehrer.VORNBERGER, Items.geschnittene_zwiebel);
        rewardLehrerItems.put(Lehrer.VORNBERGER, Items.ausleihschein);
        repReward.put(Lehrer.VORNBERGER, 1.0);

        requieredLehrerItems.put(Lehrer.GEITNER, Items.keks);
        rewardLehrerItems.put(Lehrer.GEITNER, Items.fachraum_schrank_schluessel);
        repReward.put(Lehrer.GEITNER, 1.0);

        requieredLehrerItems.put(Lehrer.DICKERT, Items.emilia_ausland_brief);
        rewardLehrerItems.put(Lehrer.DICKERT, Items.natrium_fluorid);
        repReward.put(Lehrer.DICKERT, 1.0);

        requieredLehrerItems.put(Lehrer.BIEBER, Items.kuehlpack);
        rewardLehrerItems.put(Lehrer.BIEBER, Items.machete);
        repReward.put(Lehrer.BIEBER, 1.0);
    }

    @EventHandler
    public void onLehrerClick(PlayerInteractEntityEvent e) {
        e.setCancelled(true);
        if(e.getRightClicked() instanceof Villager && questLehrerList.contains(e.getRightClicked())) {
            Player p = e.getPlayer();
            Villager l = (Villager) e.getRightClicked();
            Lehrer lehrer = Lehrer.getLehrerByEntity(l);

            Inventory questMenu = Bukkit.createInventory(null, 9*6, ChatColor.DARK_RED + lehrer.name + "'s Aufgabe:");
            for (int slot : questSpacers) {
                questMenu.setItem(slot, new ItemStack(Items.spacer));
            }

            questMenu.setItem(20, requieredLehrerItems.get(lehrer));
            questMenu.setItem(24, rewardLehrerItems.get(lehrer));

            if(p.getInventory().contains(requieredLehrerItems.get(lehrer))) {
                for (int slot : itemsIndicator) {
                    questMenu.setItem(slot, new ItemStack(Items.available_item));
                }
            }else {
                for (int slot : itemsIndicator) {
                    questMenu.setItem(slot, new ItemStack(Items.not_available_item));
                }
            }

            p.openInventory(questMenu);
            openQuest.put(p, l);
        }
    }

    @EventHandler
    public void onQuestMenuInteraction(InventoryClickEvent e) {
        if(e.getWhoClicked().getOpenInventory().getTitle().contains("'s Aufgabe:")) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();

            if(e.getSlot() == 24) {
                if(p.getOpenInventory().getItem(10).equals(Items.available_item)) {

                    String sub1 = p.getOpenInventory().getTitle().substring(7);
                    String lehrerName = sub1.replaceAll("'s Aufgabe:", "");

                    Lehrer lehrer = null;

                    p.sendMessage(ChatColor.GREEN + "Du hast die Aufgabe von " + lehrerName + " erledigt!");

                    p.getInventory().remove(p.getOpenInventory().getItem(20));
                    p.getInventory().addItem(p.getOpenInventory().getItem(24));

                    playerMirror.get(p.getName()).addReputation(lehrer, repReward.get(lehrerName));

                    Villager l = openQuest.get(p);

                    ParticleHandler.createParticles(l.getLocation(), Particle.EXPLOSION, 2, 0, true, null);
                    ParticleHandler.createParticles(l.getLocation(), Particle.LAVA, 4, 0.05, true, null);

                    l.remove();
                    openQuest.remove(p);

                    p.closeInventory();
                }
            }
        }
    }

    @EventHandler
    public void onCloseQuestmenu(InventoryCloseEvent e) {
        if(e.getPlayer().getOpenInventory().getTitle().contains("'s Aufgabe:")) {
            openQuest.remove(e.getPlayer());
        }
    }
}
