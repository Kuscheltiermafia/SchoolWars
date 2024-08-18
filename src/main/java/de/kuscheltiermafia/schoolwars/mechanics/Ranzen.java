package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;

public class Ranzen implements Listener {

    public static void createRanzen(String team, Location loc) {
        if (team.equals("naturwissenschaftler")) {
            BlockDisplay nws_ranzen = Bukkit.getServer().getWorld("schoolwars").spawn(loc, BlockDisplay.class);

            Transformation transformation = nws_ranzen.getTransformation();
            transformation.getScale().set(0.9);

            nws_ranzen.setBlock(Bukkit.createBlockData(Material.GREEN_WOOL));

            nws_ranzen.setGlowing(true);
            nws_ranzen.setCustomName("§2Grüner Ranzen");
            nws_ranzen.setCustomNameVisible(true);
        }
        if (team.equals("sprachler")) {
            BlockDisplay sprach_ranzen = Bukkit.getServer().getWorld("schoolwars").spawn(loc, BlockDisplay.class);

            Transformation transformation = sprach_ranzen.getTransformation();
            transformation.getScale().set(0.9);

            sprach_ranzen.setBlock(Bukkit.createBlockData(Material.YELLOW_WOOL));

            sprach_ranzen.setGlowing(true);
            sprach_ranzen.setCustomName("§6Gelber Ranzen");
            sprach_ranzen.setCustomNameVisible(true);
        }
        if (team.equals("sportler")) {
            BlockDisplay sport_ranzen = Bukkit.getServer().getWorld("schoolwars").spawn(loc, BlockDisplay.class);

            Transformation transformation = sport_ranzen.getTransformation();
            transformation.getScale().set(0.9);

            sport_ranzen.setBlock(Bukkit.createBlockData(Material.RED_WOOL));

            sport_ranzen.setGlowing(true);
            sport_ranzen.setCustomName("§4Roter Ranzen");
            sport_ranzen.setCustomNameVisible(true);
        }
    }

    public static void giveRanzen(String team, Player p) {
        if (team.equals("sprachler")) {
            p.getInventory().addItem(new ItemStack(Items.sprach_ranzen));
        }
        if (team.equals("naturwissenschaftler")) {
            p.getInventory().addItem(new ItemStack(Items.nws_ranzen));
        }
        if (team.equals("sportler")) {
            p.getInventory().addItem(new ItemStack(Items.sport_ranzen));
        }
    }
}
