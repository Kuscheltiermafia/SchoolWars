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

package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;

public class DialogueHandler implements Listener {

    public static ArrayList<Entity> talkedEntities = new ArrayList<>();
    public static HashMap<String, ArrayList<String>> lehrerDialogues = new HashMap<>();
    
    public static void initDialogues() {
        ArrayList<String> fischer = new ArrayList<>();
        fischer.add("Ja gut...");
        fischer.add("Müssen wir mal schauen, wann das geht");
        lehrerDialogues.put("Fischer", fischer);

        ArrayList<String> mettenleiter = new ArrayList<>();
        mettenleiter.add("Seid bitte leise");
        mettenleiter.add("Ich hab sehr feine Ohren.");
        mettenleiter.add("Das tut mir weh...");
        lehrerDialogues.put("Mettenleiter", mettenleiter);
    }

    public static void createDialogue(Player p, ArrayList<String> dialogue) {
        if(playerMirror.get(p.getName()).getCurrentDialogueStep() != null) {
            playerMirror.get(p.getName()).setCurrentDialogue(dialogue);
            playerMirror.get(p.getName()).setCurrentDialogueStep(0);
            sendDialogue(p);
        }
    }

    public static void sendDialogue(Player p) {
        if(playerMirror.get(p.getName()).getCurrentDialogueStep() != null && playerMirror.get(p.getName()).getCurrentDialogueStep() <= playerMirror.get(p.getName()).getCurrentDialogue().size()) {
           splitDialogue(p, playerMirror.get(p.getName()).getCurrentDialogue().get(playerMirror.get(p.getName()).getCurrentDialogueStep()));
           playerMirror.get(p.getName()).setCurrentDialogueStep(playerMirror.get(p.getName()).getCurrentDialogueStep() + 1);
        }else{
            playerMirror.get(p.getName()).clearDialogue();
        }
    }

    public static void splitDialogue(Player p, String dialogue) {
        for(int i = 0; i < dialogue.length(); i++) {
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.spigot().sendMessage(new TextComponent(dialogue.substring(0, finalI +1)));
                }
            }.runTaskLater(SchoolWars.plugin, i*5);
        }
    }

    /*
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if(playerMirror.get(e.getPlayer().getName()).getCurrentDialogueStep() != null) {
            e.setCancelled(true);
            sendDialogue(e.getPlayer());
        }
    }

     */

    @EventHandler
    public void onLehrerKlick(PlayerInteractEntityEvent e) {
        if(e.getRightClicked() instanceof Villager && e.getRightClicked().getCustomName() != null && !talkedEntities.contains(e.getRightClicked())) {
            createDialogue(e.getPlayer(), lehrerDialogues.get(e.getRightClicked().getCustomName().substring(6)));
            talkedEntities.add(e.getRightClicked());
        }
    }
}




