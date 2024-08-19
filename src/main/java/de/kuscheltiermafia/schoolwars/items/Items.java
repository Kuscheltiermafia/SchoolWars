package de.kuscheltiermafia.schoolwars.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Items {

    public static ItemStack minas_flasche;
    public static ItemStack laptop;
    public static ItemStack placeholder;
    public static ItemStack nuke;
    public static ItemStack kuehlpack;

    public static ItemStack spacer;

    public static ItemStack nws_ranzen;
    public static ItemStack sprach_ranzen;
    public static ItemStack sport_ranzen;

    public static ArrayList<ItemStack> itemList = new ArrayList<ItemStack>();

    public static void initItems() {

        minas_flasche = createItem(Material.GLASS_BOTTLE, "§4Minas' Flasche", 1, 1, null, false, false);
        itemList.add(minas_flasche);
        placeholder = createItem(Material.STRUCTURE_VOID, "§5§l§kA§r§7 PLACEHOLDER §r§5§l§kA", 1, 1, null, true, false);
        itemList.add(placeholder);
        nuke = createItem(Material.TNT, "§4Atombombe", 1, 1, null, true, false);
        itemList.add(nuke);
        laptop = createItem(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, "§8iFake Laptop", 1, 1, null, false, false);
        itemList.add(laptop);

        ArrayList<String> kuehlpack_lore = new ArrayList<String>();
        kuehlpack_lore.add("§7Kühle hiermit die Verletzungen");
        kuehlpack_lore.add("§7deiner Mitstreiter und hilf ihnen so.");

        kuehlpack = createItem(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, "§bKühlpack", 1, 1, kuehlpack_lore, false, false);
        itemList.add(kuehlpack);

        spacer = createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ", 2, 1, null, false, true);

        ArrayList<String> ranzen_lore = new ArrayList<String>();
        ranzen_lore.add("§7- Dies ist dein Ranzen -");
        ranzen_lore.add("§7Beschütze und verstecke ihn");
        ranzen_lore.add("§7unter allen Umständen!");

        nws_ranzen = createItem(Material.GREEN_WOOL, "§2Grüner Ranzen", 1, 1, ranzen_lore, false, false);
        itemList.add(nws_ranzen);
        sprach_ranzen = createItem(Material.YELLOW_WOOL, "§6Gelber Ranzen", 1, 1, ranzen_lore, false, false);
        itemList.add(sprach_ranzen);
        sport_ranzen = createItem(Material.RED_WOOL, "§4Roter Ranzen", 1, 1, ranzen_lore, false, false);
        itemList.add(sport_ranzen);

    }

    public static ItemStack createItem(Material material, String name, int id, int stackSize, List<String> lore, boolean glint, boolean hideTooltip) {

        ItemStack genItem = new ItemStack(material);
        ItemMeta meta = genItem.getItemMeta();

        meta.setMaxStackSize(stackSize);
        meta.setCustomModelData(id);
        meta.setItemName(name);
        meta.setLore(lore);
        meta.setEnchantmentGlintOverride(glint);
        meta.setHideTooltip(hideTooltip);

        genItem.setItemMeta(meta);

        return genItem;
    }


}
