package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public class DialogueHandler implements Listener {

    public static ArrayList<Entity> talkedEntities = new ArrayList<>();

    public static void createDialogue(Player p, Entity e, ArrayList<String> dialogue) {
        if(SchoolWars.playerMirror.get(p.getName()).getCurrentDialogueStep() != null) {
            SchoolWars.playerMirror.get(p.getName()).setCurrentDialogue(dialogue);
            SchoolWars.playerMirror.get(p.getName()).setCurrentDialogueStep(0);
            sendDialogue(p);
        }
    }

    public static void sendDialogue(Player p) {
        if(SchoolWars.playerMirror.get(p.getName()).getCurrentDialogueStep() != null && SchoolWars.playerMirror.get(p.getName()).getCurrentDialogueStep() <= SchoolWars.playerMirror.get(p.getName()).getCurrentDialogue().size()) {
           splitDialogue(p, SchoolWars.playerMirror.get(p.getName()).getCurrentDialogue().get(SchoolWars.playerMirror.get(p.getName()).getCurrentDialogueStep()));
           SchoolWars.playerMirror.get(p.getName()).setCurrentDialogueStep(SchoolWars.playerMirror.get(p.getName()).getCurrentDialogueStep() + 1);
        }else{
            SchoolWars.playerMirror.get(p.getName()).clearDialogue();
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

    public void onSneak(PlayerToggleSneakEvent e) {
        if(SchoolWars.playerMirror.get(e.getPlayer().getName()).getCurrentDialogueStep() != null) {
            e.setCancelled(true);
            sendDialogue(e.getPlayer());
        }
    }
}




