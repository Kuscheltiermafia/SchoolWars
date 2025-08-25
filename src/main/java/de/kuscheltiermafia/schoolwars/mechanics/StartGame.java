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

public class StartGame implements Listener {

    public static boolean menuOpen = false;
    public static Player menuOpener;

    public static void openGUI(Player p, boolean triggerByLeaveEvent) {

        if(menuOpen && p != menuOpener) {
            p.sendMessage("Es ist bereits ein Spieler dabei das Spiel zu starten.");
            return;
        }

        menuOpen = true;
        menuOpener = p;

        ItemStack player_count = Items.player_count_display;
        if (triggerByLeaveEvent) {
            player_count.setAmount(52);
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

    @EventHandler
    public static void onCloseMenu(InventoryCloseEvent e){
        menuOpen = false;
        menuOpener = null;
    }

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

    public static boolean start() {

        if (!Debug.startWorks || SchoolWars.gameStarted) {
            return false;
        }

//reset
        playerMirror.clear();

        try {
            for (Item item : WORLD.getEntitiesByClass(Item.class)) {
                item.remove();
            }
        } catch (Exception ignored) {}

        try {
            for (Interaction interaction : WORLD.getEntitiesByClass(Interaction.class)) {
                interaction.remove();
            }
        } catch (Exception ignored) {}

        try {
            for (Villager villager : WORLD.getEntitiesByClass(Villager.class)) {
                villager.remove();
            }
        }catch (Exception ignored) {}

//prepare game

        Stundenplan.updateStundenplan(true);
        Stundenplan.updateStundenplan(false);

        Lehrer.updateLehrerPosition(false);

//Set Playernames and ready them for battle
        Team.clearTeams();
        for (Player p : Bukkit.getOnlinePlayers()) {
            Team.resetPlayer(p);
            p.getInventory().clear();
            p.setGameMode(GameMode.SURVIVAL);
            p.setHealth(20);
            p.getActivePotionEffects().clear();
        }

//prepare player Mirrors
        playerMirror.clear();
        for (Player p : Bukkit.getOnlinePlayers()) {
            playerMirror.put(p.getName(), new PlayerMirror(p.getName()));
        }

//prepare Teams
        Team.joinTeams();
        Team.NWS.prepare();
        Team.SPORTLER.prepare();
        Team.SPRACHLER.prepare();

//revive Player
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!playerMirror.get(p.getName()).isAlive()) {
                RevivePlayer.revivePlayer(p, p);
            }
        }

        SchoolWars.gameStarted = true;
        return true;
    }

}
