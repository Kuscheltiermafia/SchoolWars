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

package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.*;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class Ranzen implements Listener {

    public static HashMap<Location, BlockDisplay> displayPositions = new HashMap<Location, BlockDisplay>();

    public static void createRanzen(String team, Location loc) {

        if (team.equals("naturwissenschaftler")) {

            Interaction nws_ranzen_hb = Bukkit.getServer().getWorld("schoolwars").spawn(loc.add(0.5, 0, 0.5), Interaction.class);
            BlockDisplay nws_ranzen = Bukkit.getServer().getWorld("schoolwars").spawn(loc.subtract(0.5, 0, 0.5), BlockDisplay.class);

            nws_ranzen.setBlock(Bukkit.createBlockData(Material.GREEN_WOOL));

            nws_ranzen.setCustomName("§2Grüner Ranzen");

            nws_ranzen.setDisplayHeight(0.9f);
            nws_ranzen.setDisplayWidth(0.9f);

            displayPositions.put(loc, nws_ranzen);

            nws_ranzen_hb.setInteractionHeight(1);
            nws_ranzen_hb.setInteractionWidth(1);

            nws_ranzen_hb.setMetadata("naturwissenschaftler", new FixedMetadataValue(SchoolWars.getPlugin(), nws_ranzen.getCustomName()));

        }
        if (team.equals("sprachler")) {
            Interaction sprach_ranzen_hb = Bukkit.getServer().getWorld("schoolwars").spawn(loc.add(0.5, 0, 0.5), Interaction.class);
            BlockDisplay sprach_ranzen = Bukkit.getServer().getWorld("schoolwars").spawn(loc.subtract(0.5, 0, 0.5), BlockDisplay.class);

            sprach_ranzen.setBlock(Bukkit.createBlockData(Material.YELLOW_WOOL));

            sprach_ranzen.setCustomName("§6Gelber Ranzen");

            sprach_ranzen.setDisplayHeight(0.9f);
            sprach_ranzen.setDisplayWidth(0.9f);

            displayPositions.put(loc, sprach_ranzen);

            sprach_ranzen_hb.setInteractionHeight(1);
            sprach_ranzen_hb.setInteractionWidth(1);

            sprach_ranzen_hb.setMetadata("sprachler", new FixedMetadataValue(SchoolWars.getPlugin(), sprach_ranzen.getCustomName()));

        }
        if (team.equals("sportler")) {
            Interaction sport_ranzen_hb = Bukkit.getServer().getWorld("schoolwars").spawn(loc.add(0.5, 0, 0.5), Interaction.class);
            BlockDisplay sport_ranzen = Bukkit.getServer().getWorld("schoolwars").spawn(loc.subtract(0.5, 0, 0.5), BlockDisplay.class);

            sport_ranzen.setBlock(Bukkit.createBlockData(Material.RED_WOOL));

            sport_ranzen.setCustomName("§4Roter Ranzen");

            sport_ranzen.setDisplayHeight(0.9f);
            sport_ranzen.setDisplayWidth(0.9f);

            displayPositions.put(loc, sport_ranzen);

            sport_ranzen_hb.setInteractionHeight(1);
            sport_ranzen_hb.setInteractionWidth(1);

            sport_ranzen_hb.setMetadata("sportler", new FixedMetadataValue(SchoolWars.getPlugin(), sport_ranzen.getCustomName()));

        }
    }

    public static HashMap<String, Integer> ranzenAmount = new HashMap<String, Integer>();

    public static void generateRanzenCounter(){
        ranzenAmount.put(SchoolWars.nameSportler, 0);
        ranzenAmount.put(SchoolWars.nameNaturwissenschaftler, 0);
        ranzenAmount.put(SchoolWars.nameSprachler, 0);
    }

    public static void giveRanzen(String team, Player p) {
        if (team.equals("sprachler")) {
            p.getInventory().addItem(new ItemStack(Items.sprach_ranzen));
            ranzenAmount.put(SchoolWars.nameSprachler, ranzenAmount.get(SchoolWars.nameSprachler) + 1);
        }
        if (team.equals("naturwissenschaftler")) {
            p.getInventory().addItem(new ItemStack(Items.nws_ranzen));
            ranzenAmount.put(SchoolWars.nameNaturwissenschaftler, ranzenAmount.get(SchoolWars.nameNaturwissenschaftler) + 1);
        }
        if (team.equals("sportler")) {
            p.getInventory().addItem(new ItemStack(Items.sport_ranzen));
            ranzenAmount.put(SchoolWars.nameSportler, ranzenAmount.get(SchoolWars.nameSportler) + 1);
        }
    }

    public static void destroyRanzen(Player p, String team, Location loc) {

        for(Player online : Bukkit.getOnlinePlayers()) {
            online.spawnParticle(Particle.EXPLOSION, loc, 5, 0, 0, 0);

            //todo: remove prefix

            online.sendTitle(ChatColor.DARK_RED + p.getDisplayName() + " hat einen Ranzen zerstört!", ChatColor.DARK_GRAY + "- Es war ein Ranzen von den " + team.substring(0, 1).toUpperCase() + team.substring(1) + "n " + ChatColor.DARK_GRAY + "-", 10, 50, 10);

        }
        Bukkit.broadcastMessage(p.getDisplayName() + ChatColor.DARK_GRAY + " hat einen Ranzen der " + team.substring(0, 1).toUpperCase() + team.substring(1) + ChatColor.DARK_GRAY + " zerstört!");

        ranzenAmount.put(team, ranzenAmount.get(team) - 1);

        if (ranzenAmount.get(team) == 0) {
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "Das Team der " + team.substring(0, 1).toUpperCase() + team.substring(1) + ChatColor.DARK_RED + " hat keinen Ranzen mehr!");
        }

    }

    public static void clearRanzen(){
        for(Interaction interaction : Bukkit.getWorld("schoolwars").getEntitiesByClass(Interaction.class)) {
            if(Ranzen.displayPositions.containsKey(interaction.getLocation().subtract(0.5, 0, 0.5))) {
                BlockDisplay display = Ranzen.displayPositions.get(interaction.getLocation().subtract(0.5, 0, 0.5));
                display.remove();
                interaction.remove();
            }
        }
        for(Player p : Bukkit.getOnlinePlayers()) {
           p.getInventory().remove(new ItemStack(Items.nws_ranzen));
           p.getInventory().remove(new ItemStack(Items.sport_ranzen));
           p.getInventory().remove(new ItemStack(Items.sprach_ranzen));
        }
    }
}
