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

package de.kuscheltiermafia.schoolwars.win_conditions;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.Team;
import de.kuscheltiermafia.schoolwars.items.Items;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;


public class Ranzen implements Listener {

    public static HashMap<Location, BlockDisplay> displayPositions = new HashMap<>();
    public static HashMap<BlockDisplay, Interaction> placedRanzen = new HashMap<>();
    public static HashMap<Interaction, Team> ranzenTeam = new HashMap<>();
    public static HashMap<Team, Integer> ranzenAmount = new HashMap<>();


    public static void placeRanzen(Team team, Location loc) {

        if (team == Team.NWS) createRanzen(team, "§2Grüner Ranzen", Material.GREEN_WOOL, loc);
        else if (team == Team.SPRACHLER) createRanzen(team, "§6Gelber Ranzen", Material.YELLOW_WOOL, loc);
        else if (team == Team.SPORTLER) createRanzen(team, "§4Roter Ranzen", Material.RED_WOOL, loc);

    }

    public static void generateRanzenCounter(){
        ranzenAmount.put(Team.SPORTLER, 0);
        ranzenAmount.put(Team.NWS, 0);
        ranzenAmount.put(Team.SPRACHLER, 0);
    }

    public static void destroyRanzen(Player p, Team team, Location loc) {

        for(Player online : Bukkit.getOnlinePlayers()) {
            online.spawnParticle(Particle.EXPLOSION, loc, 5, 0, 0, 0);

            //todo: remove prefix

            online.sendTitle(ChatColor.DARK_RED + p.getDisplayName() + " hat einen Ranzen zerstört!", ChatColor.DARK_GRAY + "- Es war ein Ranzen von den " + team.name().substring(0, 1).toUpperCase() + team.name().substring(1) + "n " + ChatColor.DARK_GRAY + "-", 10, 50, 10);

        }
        Bukkit.broadcastMessage(p.getDisplayName() + ChatColor.DARK_GRAY + " hat einen Ranzen der " + team.name().substring(0, 1).toUpperCase() + team.name().substring(1) + ChatColor.DARK_GRAY + " zerstört!");

        ranzenAmount.put(team, ranzenAmount.get(team) - 1);

        if (ranzenAmount.get(team) == 0) {
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "Das Team der " + team.name().substring(0, 1).toUpperCase() + team.name().substring(1) + ChatColor.DARK_RED + " hat keinen Ranzen mehr!");
        }

    }

    public static void clearRanzen(){
        for(BlockDisplay display : placedRanzen.keySet()) {
            placedRanzen.get(display).remove();
            display.remove();
        }
        for(Player p : Bukkit.getOnlinePlayers()) {

            for (ItemStack ranzen : Items.ranzenList) {
                if (p.getInventory().contains(ranzen)) {
                    p.getInventory().remove(ranzen);
                }
            }
        }
    }

    static void createRanzen(Team team, String ranzenName, Material ranzenMaterial, Location loc) {

        Interaction ranzen_hb = Bukkit.getServer().getWorld("schoolwars").spawn(loc.add(0.5, 0, 0.5), Interaction.class);
        BlockDisplay ranzen = Bukkit.getServer().getWorld("schoolwars").spawn(loc.subtract(0.5, 0, 0.5), BlockDisplay.class);

        ranzen.setBlock(Bukkit.createBlockData(ranzenMaterial));

        ranzen.setCustomName(ranzenName);
        ranzen.setCustomNameVisible(true);

        ranzen.setDisplayHeight(0.9f);
        ranzen.setDisplayWidth(0.9f);

        displayPositions.put(loc, ranzen);

        ranzen_hb.setInteractionHeight(1);
        ranzen_hb.setInteractionWidth(1);

        ranzen_hb.setMetadata(team.teamName, new FixedMetadataValue(SchoolWars.getPlugin(), "dummyValue"));

        placedRanzen.put(ranzen, ranzen_hb);
        ranzenTeam.put(ranzen_hb, team);

    }

    @EventHandler
    public void onRanzenPickup(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (!(e.getRightClicked() instanceof Interaction ranzen)) return;


        if (ranzen.hasMetadata(Team.NWS.teamName) || ranzen.hasMetadata(Team.SPORTLER.teamName) || ranzen.hasMetadata(Team.SPRACHLER.teamName)) {
            ranzenPickup(p, ranzen, e);
        }

    }

    @EventHandler
    public void onRanzenPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        ItemStack ranzen = e.getItemInHand();
        if (Items.ranzenList.contains(ranzen)) {
            if (!SchoolWars.gameStarted) {
                p.sendMessage(ChatColor.RED + "Ranzen können nur während dem Spiel platziert werden!");
                e.setCancelled(true);
                return;
            }
            BlockState replcaedState =  e.getBlockReplacedState();
            if(replcaedState.getType() != Material.AIR && replcaedState.getType() != Material.CAVE_AIR && replcaedState.getType() != Material.VOID_AIR) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "Du kannst hier nichts platzieren!");
                return;
            }

            for (Team team : Team.values()) {
                if (ranzen.equals(team.ranzen)) {
                    ranzenPlace(e, p, team);
                }
            }
        }
    }

    private static void ranzenPickup(Player player, Interaction ranzen, PlayerInteractEntityEvent event) {

        if (!playerMirror.get(player.getName()).isAlive()) return;

        Team team = playerMirror.get(player.getName()).getTeam();

        if(ranzen.hasMetadata(team.teamName)) {
            if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                Entity display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                display.remove();
            }
            Ranzen.placedRanzen.remove(ranzen);
            ranzen.remove();
            player.getInventory().addItem(new ItemStack(team.ranzen));
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen aufgehoben!"));
            event.setCancelled(true);
        }else{
            Ranzen.destroyRanzen(player, ranzenTeam.get(ranzen), ranzen.getLocation());
            if (Ranzen.displayPositions.containsKey(ranzen.getLocation().subtract(0.5, 0, 0.5))) {
                Entity display = Ranzen.displayPositions.get(ranzen.getLocation().subtract(0.5, 0, 0.5));
                display.remove();
            }
            Ranzen.placedRanzen.remove(ranzen);
            ranzen.remove();
        }

    }

    private static void ranzenPlace(BlockPlaceEvent e, Player p, Team team){

        Ranzen.placeRanzen(team, e.getBlock().getLocation());
        p.getInventory().setItem(e.getHand(), new ItemStack(Material.AIR));
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2Du hast deinen Ranzen plaziert!"));
        e.setCancelled(true);

    }

}
