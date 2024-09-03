package de.kuscheltiermafia.schoolwars.mechanics;

import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class LehrerHandler {

    public static Villager blumpfi;
    public static Villager schneider;
    public static Villager fischer;
    public static ArrayList<Villager> lehrerList = new ArrayList<>();

    public static HashMap<String, ItemStack> requieredLehrerItems = new HashMap<>();
    public static HashMap<String, ItemStack> rewardLehrerItems = new HashMap<>();
    public static HashMap<String, Double> repReward = new HashMap<>();

    public static void initLehrer(double x, double y, double z) {
        Location lehrerStorage = new Location(Bukkit.getWorld("schoolwars"),x, y, z);

        blumpfi = createLehrer(lehrerStorage, "Blumpfi",Villager.Type.SWAMP, true, false, true);
        lehrerList.add(blumpfi);
    }

    public static void initLehrerQuests() {
        requieredLehrerItems.put("Fischer", Items.stroke_master);
        rewardLehrerItems.put("Fischer", Items.fischers_spiel);
        repReward.put("Fischer", 1d);

        requieredLehrerItems.put("Schneider", Items.kaputtes_ipad);
        rewardLehrerItems.put("Schneider", Items.rollator);
        repReward.put("Schneider", 1d);
    }

    public static void removeLehrer() {
        for (Villager currentLehrer : Bukkit.getWorld("schoolwars").getEntitiesByClass(Villager.class)) {
            if(currentLehrer.getCustomName() != null) {
                lehrerList.remove(currentLehrer);
                currentLehrer.remove();
            }
        }
    }

    static Villager createLehrer(Location location, String name, Villager.Type type,boolean hasAI, boolean isBaby, boolean isSilent) {

        Villager currentLehrer = (Villager) Bukkit.getWorld("schoolwars").spawnEntity(location, EntityType.VILLAGER);

        currentLehrer.setCustomName(name);
        currentLehrer.setCustomNameVisible(true);
        currentLehrer.setVillagerType(type);
        currentLehrer.setProfession(Villager.Profession.NITWIT);
        currentLehrer.setAI(hasAI);
        currentLehrer.setInvulnerable(true);
        currentLehrer.setSilent(false);
        currentLehrer.setCanPickupItems(false);
        currentLehrer.setCollidable(true);
        currentLehrer.setRemoveWhenFarAway(false);
        currentLehrer.setPersistent(true);
        currentLehrer.setSilent(isSilent);

        if (isBaby){
            currentLehrer.setBaby();
        }

        return currentLehrer;
    }

    public static void createFrauSchneider(Location loc) {
        schneider = createLehrer(loc, "Frau Schneider",Villager.Type.SWAMP, true, false, true);
        lehrerList.add(schneider);
    }

    public static void createHerrFischer(Location loc) {
        fischer = createLehrer(loc, "Herr Fischer",Villager.Type.PLAINS, true, false, true);
        lehrerList.add(fischer);
    }

}
