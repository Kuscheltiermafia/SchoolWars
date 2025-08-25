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

/**
 * Handles NPC dialogue interactions in SchoolWars.
 * Manages conversations with teacher villagers using a typewriter-style
 * text display system that reveals characters over time for dramatic effect.
 */
public class DialogueHandler implements Listener {

    /** Tracks which entities have already been talked to (prevents repeat conversations) */
    public static ArrayList<Entity> talkedEntities = new ArrayList<>();
    
    /** Stores dialogue lines for each teacher NPC, mapped by teacher name */
    public static HashMap<String, ArrayList<String>> lehrerDialogues = new HashMap<>();
    
    /**
     * Initializes all teacher dialogue content.
     * Sets up predefined conversations for various teacher NPCs in the game.
     */
    public static void initDialogues() {
        // Initialize Fischer teacher dialogue
        ArrayList<String> fischer = new ArrayList<>();
        fischer.add("Ja gut...");
        fischer.add("Müssen wir mal schauen, wann das geht");
        lehrerDialogues.put("Fischer", fischer);

        // Initialize Mettenleiter teacher dialogue
        ArrayList<String> mettenleiter = new ArrayList<>();
        mettenleiter.add("Seid bitte leise");
        mettenleiter.add("Ich hab sehr feine Ohren.");
        mettenleiter.add("Das tut mir weh...");
        lehrerDialogues.put("Mettenleiter", mettenleiter);
    }

    /**
     * Starts a new dialogue sequence for a player.
     * Sets up the dialogue in the player's mirror and begins the conversation.
     * 
     * @param p The player to start the dialogue with
     * @param dialogue The list of dialogue lines to display
     */
    public static void createDialogue(Player p, ArrayList<String> dialogue) {
        // Only start dialogue if player has dialogue capability initialized
        if(playerMirror.get(p.getName()).getCurrentDialogueStep() != null) {
            playerMirror.get(p.getName()).setCurrentDialogue(dialogue);
            playerMirror.get(p.getName()).setCurrentDialogueStep(0);
            sendDialogue(p);
        }
    }

    /**
     * Sends the next line of dialogue to a player.
     * Progresses through the dialogue sequence or clears it when finished.
     * 
     * @param p The player to send dialogue to
     */
    public static void sendDialogue(Player p) {
        // Check if there are more dialogue lines to display
        if(playerMirror.get(p.getName()).getCurrentDialogueStep() != null && playerMirror.get(p.getName()).getCurrentDialogueStep() <= playerMirror.get(p.getName()).getCurrentDialogue().size()) {
           // Send current dialogue line with typewriter effect
           splitDialogue(p, playerMirror.get(p.getName()).getCurrentDialogue().get(playerMirror.get(p.getName()).getCurrentDialogueStep()));
           // Move to next dialogue line
           playerMirror.get(p.getName()).setCurrentDialogueStep(playerMirror.get(p.getName()).getCurrentDialogueStep() + 1);
        }else{
            // No more dialogue lines, clear the conversation
            playerMirror.get(p.getName()).clearDialogue();
        }
    }

    /**
     * Creates a typewriter effect by revealing dialogue text character by character.
     * Each character is displayed with a small delay to create a dramatic typing animation.
     * 
     * @param p The player to display the text to
     * @param dialogue The complete dialogue line to animate
     */
    public static void splitDialogue(Player p, String dialogue) {
        // Create typewriter effect by showing one character at a time
        for(int i = 0; i < dialogue.length(); i++) {
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    // Send progressively longer substrings to create typing animation
                    p.spigot().sendMessage(new TextComponent(dialogue.substring(0, finalI +1)));
                }
            }.runTaskLater(SchoolWars.plugin, i*5); // 5 tick delay between each character
        }
    }

    /*
    // Alternative implementation using sneak to advance dialogue
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if(playerMirror.get(e.getPlayer().getName()).getCurrentDialogueStep() != null) {
            e.setCancelled(true);
            sendDialogue(e.getPlayer());
        }
    }
    */

    /**
     * Handles player interactions with teacher villager NPCs.
     * Starts dialogue when a player right-clicks on a teacher for the first time.
     * 
     * @param e The PlayerInteractEntityEvent triggered when interacting with an entity
     */
    @EventHandler
    public void onLehrerKlick(PlayerInteractEntityEvent e) {
        // Check if player right-clicked a villager with a custom name that hasn't been talked to yet
        if(e.getRightClicked() instanceof Villager && e.getRightClicked().getCustomName() != null && !talkedEntities.contains(e.getRightClicked())) {
            // Extract teacher name from villager's custom name (removing first 6 characters which are formatting)
            // Start dialogue for this teacher and mark them as talked to
            createDialogue(e.getPlayer(), lehrerDialogues.get(e.getRightClicked().getCustomName().substring(6)));
            talkedEntities.add(e.getRightClicked());
        }
    }
}




