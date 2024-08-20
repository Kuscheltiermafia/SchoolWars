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

package de.kuscheltiermafia.schoolwars.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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
    public static ItemStack schulbuch;
    public static ItemStack moritz_hut;
    public static ItemStack kaputtes_ipad;
    public static ItemStack vape_fruitberry;
    public static ItemStack vape_arschwasser;
    public static ItemStack vape_mango;
    public static ItemStack vape_strawberry;
    public static ItemStack vape_triple;
    public static ItemStack vape_arabics;
    public static ItemStack vape_air;
    public static ItemStack vape_empty;
    public static ItemStack spacer;
    public static ItemStack nws_ranzen;
    public static ItemStack sprach_ranzen;
    public static ItemStack sport_ranzen;
    public static ItemStack xlr_kabel;
    public static ItemStack attack_stuhl;
    public static ItemStack leberkaese;
    public static ItemStack uranium;
    public static ItemStack versuch;
    public static ItemStack page_up;
    public static ItemStack page_down;
    public static ItemStack fischers_spiel;
    public static ItemStack karls_elexier;
    public static ItemStack baar_kaffee;
    public static ItemStack cookie;
    public static ItemStack generalSchluessel;
    public static ItemStack rollator;
    public static ItemStack no_page_down;
    public static ItemStack no_page_up;

    public static ArrayList<ItemStack> itemList = new ArrayList<ItemStack>();

    public static void initItems() {

//Waffen
        minas_flasche = createItem(Material.GLASS_BOTTLE, "§4Minas' Flasche", 1, 1, null, false, false);
        itemList.add(minas_flasche);

        schulbuch = createItem(Material.KNOWLEDGE_BOOK, ChatColor.BLUE + "Fokus Kampfkunst", 1, 1, null, false, false);
        itemList.add(schulbuch);

        xlr_kabel = createItem(Material.LEAD, ChatColor.DARK_GRAY + "XLR Kabel", 1, 16, null, false, false);
        itemList.add(xlr_kabel);

        attack_stuhl = createItem(Material.OAK_STAIRS, ChatColor.WHITE + "Stuhl", 1, 1, null, false, false);
        itemList.add(attack_stuhl);

//Support
        ArrayList<String> kuehlpack_lore = new ArrayList<String>();
        kuehlpack_lore.add("§7Kühle hiermit die Verletzungen");
        kuehlpack_lore.add("§7deiner Mitstreiter und hilf ihnen so.");
        kuehlpack = createItem(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, "§bKühlpack", 1, 1, kuehlpack_lore, false, false);
        itemList.add(kuehlpack);

        leberkaese = createItem(Material.COOKED_PORKCHOP, "§4Leberkäse", 1, 1, null, false, false);
        itemList.add(leberkaese);

        cookie = createItem(Material.COOKIE, "§6Cookie", 1, 16, null, false, false);
        itemList.add(cookie);

//Win condition
        //nukes
        nuke = createItem(Material.TNT, "§4Atombombe", 1, 1, null, true, false);
        itemList.add(nuke);

        uranium = createItem(Material.GREEN_DYE, "§aAngereichertes Uran-235", 64, 1, null, true, false);
        itemList.add(uranium);

        versuch = createItem(Material.HEAVY_CORE, ChatColor.DARK_GRAY + "Versuchsaufbau", 16, 1, null, false, false);
        itemList.add(versuch);



//Miscellaneous
        ArrayList<String> moritz_hut_lore = new ArrayList<String>();
        moritz_hut_lore.add("§7Dieses Item representiert die Stärke");
        moritz_hut_lore.add("§7und Macht unseres Schulleiters Morgon!");
        moritz_hut_lore.add("§7Geehrt sei Morgon!");
        moritz_hut_lore.add("§7Victory at all costs!");
        moritz_hut_lore.add("§4Heil Morgon!");
        moritz_hut = createItem(Material.NETHERITE_HELMET, ChatColor.BLACK + "Moritz' Hut", 1, 1, moritz_hut_lore, true, false);
        itemList.add(moritz_hut);

        kaputtes_ipad = createItem(Material.NETHERITE_SCRAP, ChatColor.WHITE + "Kaputtes iPad", 1, 1, null, false, false);
        itemList.add(kaputtes_ipad);

        laptop = createItem(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, "§8iFake Laptop", 1, 1, null, false, false);
        itemList.add(laptop);

        fischers_spiel = createItem(Material.JUKEBOX, "§8Fischers Spielzeug", 1, 1, null, false, false);
        itemList.add(fischers_spiel);

        baar_kaffee = createItem(Material.FLOWER_POT, ChatColor.GRAY + "Baars Kaffee", 1, 1, null, false, false);
        itemList.add(baar_kaffee);

        karls_elexier = createItem(Material.POTION,ChatColor.LIGHT_PURPLE + "Elexir Karls des Kleinen", 1, 1, null, false, false);
        itemList.add(karls_elexier);

        generalSchluessel = createItem(Material.TRIPWIRE_HOOK, ChatColor.GOLD + "Generalschlüssel", 1, 1, null, false, false);
        itemList.add(generalSchluessel);

        rollator = createItem(Material.NETHER_BRICK_FENCE, ChatColor.GRAY + "Rollator", 1, 1, null, false, false);
        itemList.add(rollator);

//Vapes
        vape_fruitberry = createItem(Material.PINK_CANDLE, ChatColor.LIGHT_PURPLE + "Vape Fruitberry Punch", 1, 1, null, false, false);
        itemList.add(vape_fruitberry);

        vape_arschwasser = createItem(Material.BROWN_CANDLE, ChatColor.GREEN + "Vape Tropical Arschwasser", 1, 1, null, false, false);
        itemList.add(vape_arschwasser);

        vape_strawberry = createItem(Material.RED_CANDLE, ChatColor.RED + "Vape Strawberry Burst ", 1, 1, null, false, false);
        itemList.add(vape_strawberry);

        vape_mango = createItem(Material.ORANGE_CANDLE, ChatColor.YELLOW + "Vape Chewy Mango ", 1, 1, null, false, false);
        itemList.add(vape_mango);

        vape_triple = createItem(Material.BLUE_CANDLE, ChatColor.BLUE + "Vape Triple Fruit Combo", 1, 1, null, false, false);
        itemList.add(vape_triple);

        vape_arabics = createItem(Material.GRAY_CANDLE, ChatColor.GRAY + "Vape Double Arabics", 1, 1, null, false, false);
        itemList.add(vape_arabics);

        vape_air = createItem(Material.WHITE_CANDLE, ChatColor.YELLOW + "Vape AIR", 1, 1, null, false, false);
        itemList.add(vape_air);

        vape_empty = createItem(Material.BLACK_CANDLE, ChatColor.BLACK + "Leere Vape", 1, 1, null, false, false);
        itemList.add(vape_empty);

//Programming Utils
        spacer = createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ", 20, 1, null, false, true);
        page_up = createItem(Material.ARROW, "§4Next Page", 21, 1, null, false, false);
        page_down = createItem(Material.ARROW, "§4Previous Page", 22, 1, null, false, false);
        no_page_down = createItem(Material.BARRIER, "§4No previous Page available", 21, 1, null, false, false);
        no_page_up = createItem(Material.BARRIER, "§4No next Page available", 22, 1, null, false, false);
        placeholder = createItem(Material.STRUCTURE_VOID, "§5§l§kA§r§7 PLACEHOLDER §r§5§l§kA", 1, 1, null, true, false);
        itemList.add(placeholder);

//Ranzen
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

//Create item method

    public static ItemStack createItem(Material material, String name, int id, int stackSize, List<String> lore, boolean glint, boolean hideTooltip) {

        ItemStack genItem = new ItemStack(material);
        ItemMeta meta = genItem.getItemMeta();

        meta.setMaxStackSize(stackSize);
        meta.setCustomModelData(id);
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setEnchantmentGlintOverride(glint);
        meta.setHideTooltip(hideTooltip);

        genItem.setItemMeta(meta);

        return genItem;
    }


}
