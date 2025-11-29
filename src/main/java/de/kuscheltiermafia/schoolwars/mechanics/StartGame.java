package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.PlayerMirror;
import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.Team;
import de.kuscheltiermafia.schoolwars.commands.Debug;
import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.lehrer.Lehrer;
import de.kuscheltiermafia.schoolwars.lehrer.Stundenplan;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;
import static de.kuscheltiermafia.schoolwars.SchoolWars.WORLD;

/**
 * Handles the game start menu GUI and game initialization.
 * <p>
 * This class provides:
 * <ul>
 *   <li>A GUI for configuring and starting games</li>
 *   <li>Team count adjustment</li>
 *   <li>Game initialization and player preparation</li>
 * </ul>
 * </p>
 * <p>
 * Only one player can have the start menu open at a time to prevent
 * conflicting configurations.
 * </p>
 */
public class StartGame implements Listener {

    /** Whether the start menu is currently open. */
    public static boolean menuOpen = false;
    
    /** The player who currently has the menu open. */
    public static Player menuOpener;

    /**
     * Opens the game start GUI for a player.
     * <p>
     * If another player already has the menu open, the request is denied.
     * </p>
     *
     * @param p the player to open the GUI for
     * @param triggerByLeaveEvent whether this was triggered by a player leave event
     */
    public static void openGUI(Player p, boolean triggerByLeaveEvent) {

        if(menuOpen && p != menuOpener) {
            p.sendMessage("Es ist bereits ein Spieler dabei das Spiel zu starten.");
            return;
        }

        menuOpen = true;
        menuOpener = p;

        ItemStack player_count = Items.player_count_display;
        if (triggerByLeaveEvent) {
            player_count.setAmount(Bukkit.getOnlinePlayers().size() - 1);
        }else {
            player_count.setAmount(Bukkit.getOnlinePlayers().size());
        }

        ItemStack team_count = Items.team_count;
        team_count.setAmount(Team.teamCount);

        Inventory startGUI = Bukkit.createInventory(null, 9 * 3, "Spiel starten");
        startGUI.setItem(10, player_count);
        startGUI.setItem(12, Items.gamemode_classic);
        startGUI.setItem(14, Items.team_count);
        startGUI.setItem(16, Items.start_game);
        startGUI.setItem(23, Items.choose_teams);

        for (int i = 0; i < 27; i++) {
            if (startGUI.getItem(i) == null) {
                startGUI.setItem(i, Items.spacer);
            }
        }

        p.openInventory(startGUI);

    }

    /**
     * Handles the menu close event to reset menu state.
     *
     * @param e the inventory close event
     */
    @EventHandler
    public static void onCloseMenu(InventoryCloseEvent e){
        if (!e.getView().getTitle().equals("Spiel starten")) return;
        if(e.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW)) return;
        menuOpen = false;
        menuOpener = null;
    }

    /**
     * Handles clicks on the team count item to adjust the number of teams.
     * Left-click increases, right-click decreases.
     *
     * @param e the inventory click event
     */
    @EventHandler
    public static void onClickTeamCount(InventoryClickEvent e) {
        if (e.getView().getTitle() .equals("Spiel starten")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (!e.getCurrentItem().equals(Items.team_count)) return;

            if(e.getClick().isLeftClick()) {
                if (Team.teamCount < Team.values().length) {
                    Team.teamCount++;
                }
            } else if (e.getClick().isRightClick()) {
                if (Team.teamCount > 2) {
                    Team.teamCount--;
                }
            }

            ItemStack team_count = Items.team_count;
            team_count.setAmount(Team.teamCount);
            e.getInventory().setItem(14, team_count);


        }
    }

    /**
     * Handles clicks on the start game button.
     *
     * @param e the inventory click event
     */
    @EventHandler
    public static void onClickStart(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("Spiel starten")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (!e.getCurrentItem().equals(Items.start_game)) return;
            start();
            e.getWhoClicked().closeInventory();
        }
    }

    /**
     * Starts the SchoolWars game.
     * <p>
     * This method:
     * <ul>
     *   <li>Validates that the game can start</li>
     *   <li>Clears existing game state</li>
     *   <li>Initializes the class schedule and teachers</li>
     *   <li>Assigns players to teams</li>
     *   <li>Prepares all players for battle</li>
     * </ul>
     * </p>
     *
     * @return true if the game started successfully, false otherwise
     */
    public static boolean start() {

        // ========== Validate game can start ==========
        if (!Debug.startWorks || SchoolWars.gameStarted) {
            return false;
        }

        // ========== Reset existing game state ==========
        playerMirror.clear();

        // Remove existing item entities
        try {
            for (Item item : WORLD.getEntitiesByClass(Item.class)) {
                item.remove();
            }
        } catch (Exception ignored) {}

        // Remove existing interaction entities
        try {
            for (Interaction interaction : WORLD.getEntitiesByClass(Interaction.class)) {
                interaction.remove();
            }
        } catch (Exception ignored) {}

        // Remove existing villager entities
        try {
            for (Villager villager : WORLD.getEntitiesByClass(Villager.class)) {
                villager.remove();
            }
        }catch (Exception ignored) {}

        // ========== Initialize game systems ==========
        
        // Initialize class schedule and teacher positions
        Stundenplan.updateStundenplan(true);
        Stundenplan.updateStundenplan(false);

        Lehrer.updateLehrerPosition(false);

        // ========== Prepare players ==========
        
        // Reset player names and clear teams
        Team.clearTeams();
        for (Player p : Bukkit.getOnlinePlayers()) {
            Team.resetPlayer(p);
            p.getInventory().clear();
            p.setGameMode(GameMode.SURVIVAL);
            p.setHealth(20);
            p.getActivePotionEffects().clear();
        }

        // Create player mirrors for tracking game state
        playerMirror.clear();
        for (Player p : Bukkit.getOnlinePlayers()) {
            playerMirror.put(p.getName(), new PlayerMirror(p.getName()));
        }

        // ========== Assign and prepare teams ==========
        Team.joinTeams();
        Team.prepare();

        // Ensure all players are alive
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!playerMirror.get(p.getName()).isAlive()) {
                RevivePlayer.revivePlayer(p, p);
            }
        }

        SchoolWars.gameStarted = true;
        return true;
    }

}
