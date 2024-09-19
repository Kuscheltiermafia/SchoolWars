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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class LehrerHandler {

    public static ArrayList<Lehrer> lehrerList = new ArrayList<>();
    public static ArrayList<Villager> lehrerEntityList = new ArrayList<>();
    public static ArrayList<Villager> questLehrerList = new ArrayList<>();

    public static HashMap<String, ItemStack> requieredLehrerItems = new HashMap<>();
    public static HashMap<String, ItemStack> rewardLehrerItems = new HashMap<>();
    public static HashMap<String, Double> repReward = new HashMap<>();

    public static void initLehrerQuests() {
        requieredLehrerItems.put("Fischer", Items.stroke_master);
        rewardLehrerItems.put("Fischer", Items.fischers_spiel);
        repReward.put("Fischer", 1.0);

        requieredLehrerItems.put("Schneider", Items.kaputtes_ipad);
        rewardLehrerItems.put("Schneider", Items.rollator);
        repReward.put("Schneider", 1.0);

        requieredLehrerItems.put("Baar", Items.baar_kaffee);
        rewardLehrerItems.put("Baar", Items.useless_uranium);
        repReward.put("Baar", 1.0);

        requieredLehrerItems.put("Kliem", Items.placeholder);
        rewardLehrerItems.put("Kliem", Items.placeholder);
        repReward.put("Kliem", 0.0);

        requieredLehrerItems.put("Kesselring", Items.placeholder);
        rewardLehrerItems.put("Kesselring", Items.placeholder);
        repReward.put("Kesselring", 0.0);

        requieredLehrerItems.put("Vornberger", Items.geschnittene_zwiebel);
        rewardLehrerItems.put("Vornberger", Items.ausleihschein);
        repReward.put("Vornberger", 1.0);
    }

    public static void removeLehrer() {
        for (Villager currentLehrer : Bukkit.getWorld("schoolwars").getEntitiesByClass(Villager.class)) {
            if(currentLehrer.getCustomName() != null) {
                currentLehrer.remove();
            }
        }
        lehrerList.clear();
    }

    static Villager createLehrer(Location location, String name, Villager.Type type, Villager.Profession profession, boolean hasAI, double scale, boolean isSilent, boolean isMale) {

        Villager currentLehrer = (Villager) Bukkit.getWorld("schoolwars").spawnEntity(location, EntityType.VILLAGER);

        if(isMale) {
            currentLehrer.setCustomName("Herr " + name);
        }else{
            currentLehrer.setCustomName("Frau " + name);
        }
        currentLehrer.setCustomNameVisible(true);
        currentLehrer.setVillagerType(type);
        currentLehrer.setAI(hasAI);
        currentLehrer.setInvulnerable(true);
        currentLehrer.setSilent(false);
        currentLehrer.setCanPickupItems(false);
        currentLehrer.setCollidable(true);
        currentLehrer.setRemoveWhenFarAway(false);
        currentLehrer.setPersistent(true);
        currentLehrer.setSilent(isSilent);
        currentLehrer.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(scale);

        currentLehrer.setProfession(profession);

        return currentLehrer;
    }

    public static Villager summonLehrer(Location loc, Lehrer lehrer){
        Villager currentLehrer = createLehrer(loc, lehrer.name, lehrer.type, lehrer.profession, lehrer.hasAI, lehrer.scale, lehrer.isSilent, lehrer.isMale);
        currentLehrer.setProfession(lehrer.profession);
        lehrerList.add(lehrer);
        lehrerEntityList.add(currentLehrer);
        if (lehrer.hasQuest) {
            questLehrerList.add(currentLehrer);
        }
        return currentLehrer;
    }

}
