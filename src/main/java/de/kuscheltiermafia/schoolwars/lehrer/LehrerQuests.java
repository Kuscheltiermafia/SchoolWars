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

package de.kuscheltiermafia.schoolwars.lehrer;

import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.ParticleHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

import static de.kuscheltiermafia.schoolwars.SchoolWars.playerMirror;
import static de.kuscheltiermafia.schoolwars.lehrer.LehrerHandler.repReward;

public class LehrerQuests implements Listener {

    static int[] questSpacers = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 17, 18, 22, 27, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 17, 26, 35, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
    static int[] neededIndicator = new int[]{10, 11, 12, 19, 21, 28, 29, 30};
    static int[] rewardIndicator = new int[]{14, 15, 16, 23, 25, 32, 33, 34};

    public HashMap<Player, Villager> openQuest = new HashMap<>();

    @EventHandler
    public void onLehrerClick(PlayerInteractEntityEvent e) {
        if(e.getRightClicked() instanceof Villager && LehrerHandler.questLehrerList.contains(e.getRightClicked())) {
            e.setCancelled(true);
            Player p = e.getPlayer();
            Villager l = (Villager) e.getRightClicked();

            Inventory questMenu = Bukkit.createInventory(null, 9*6, ChatColor.DARK_RED + l.getCustomName() + "'s Aufgabe:");
            for(int i = 0; i < questSpacers.length; i++) {
                int slot = questSpacers[i];
                questMenu.setItem(slot, new ItemStack(Items.spacer));
            }

            questMenu.setItem(20, LehrerHandler.requieredLehrerItems.get(l.getCustomName().substring(5)));
            questMenu.setItem(24, LehrerHandler.rewardLehrerItems.get(l.getCustomName().substring(5)));

            if(p.getInventory().contains(LehrerHandler.requieredLehrerItems.get(l.getCustomName().substring(5)))) {
                for(int i = 0; i < neededIndicator.length; i++) {
                    int slot = neededIndicator[i];
                    questMenu.setItem(slot, new ItemStack(Items.available_item));
                }
            }else {
                for(int i = 0; i < neededIndicator.length; i++) {
                    int slot = neededIndicator[i];
                    questMenu.setItem(slot, new ItemStack(Items.not_available_item));
                }
            }

            if(p.getInventory().contains(LehrerHandler.requieredLehrerItems.get(l.getCustomName().substring(5)))) {
                for(int i = 0; i < rewardIndicator.length; i++) {
                    int slot = rewardIndicator[i];
                    questMenu.setItem(slot, new ItemStack(Items.available_item));
                }
            }else {
                for(int i = 0; i < rewardIndicator.length; i++) {
                    int slot = rewardIndicator[i];
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

                    Lehrer.getLehrerByName(lehrerName);

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
