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

package de.kuscheltiermafia.schoolwars.items;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Item;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Central registry and factory for all custom items in SchoolWars.
 * <p>
 * This class manages all game items including weapons, support items, win condition items,
 * miscellaneous items, vapes, UI elements, and team backpacks (Ranzen).
 * Items are initialized once during plugin startup via {@link #initItems()}.
 * </p>
 * <p>
 * The class also provides utility methods for:
 * <ul>
 *   <li>Creating custom items with specific properties</li>
 *   <li>Adding weapon attributes to items</li>
 *   <li>Spawning items as entities in the world</li>
 * </ul>
 * </p>
 */
public class Items {

    /** List of all registered game items for the item list GUI. */
    public static ArrayList<ItemStack> itemList = new ArrayList<ItemStack>();
    
    /** Map linking Interaction entities to their corresponding Item entities for pickups. */
    public static HashMap<Interaction, Item> itemHitboxes = new HashMap<>();

    /** List of all team backpack items. */
    public static ArrayList<ItemStack> ranzenList = new ArrayList<ItemStack>();

    /** Namespaced key for damage attribute modifications. */
    public static NamespacedKey dmgAttributeKey = new NamespacedKey(SchoolWars.getPlugin(), "attribute_key_dmg");
    
    /** Namespaced key for attack speed attribute modifications. */
    public static NamespacedKey spdAttributeKey = new NamespacedKey(SchoolWars.getPlugin(), "attribute_key_spd");

    // ==================== Weapon Items ====================

    public static ItemStack minas_flasche;
    public static ItemStack buffed_minas_flasche;
    public static ItemStack schulbuch1;
    public static ItemStack schulbuch2;
    public static ItemStack schulbuch3;
    public static ItemStack schulbuch4;
    public static ItemStack schulbuch5;
    public static ItemStack xlr_kabel;
    public static ItemStack attack_stuhl;
    public static ItemStack buffed_stuhl;
    public static ItemStack machete;

    // ==================== Support Items ====================
    public static ItemStack kuehlpack;
    public static ItemStack leberkaese;
    public static ItemStack cookie;

    // ==================== Win Condition Items ====================
    public static ItemStack stairway_heaven;
    //nukes
    public static ItemStack nuke;
    public static ItemStack uranium;
    public static ItemStack useless_uranium;
    public static ItemStack fluor;
    public static ItemStack versuch;
    public static ItemStack emilia_ausland_brief;
    public static ItemStack baar_kaffee;
    public static ItemStack kaffeebohnen;
    public static ItemStack verbrannte_bohnen;
    public static ItemStack keks;
    public static ItemStack aquatisiertes_fluor;
    public static ItemStack natrium_fluorid;

    // ==================== Miscellaneous Items ====================
    public static ItemStack moritz_hut;
    public static ItemStack kaputtes_ipad;
    public static ItemStack ipad;
    public static ItemStack laptop;
    public static ItemStack zwiebel;
    public static ItemStack geschimmelte_zwiebel;
    public static ItemStack geschnittene_zwiebel;
    public static ItemStack ausleihschein;
    public static ItemStack fischers_spiel;
    public static ItemStack leere_tasse;
    public static ItemStack karls_elexier;
    public static ItemStack general_schluessel;
    public static ItemStack fachraum_schrank_schluessel;
    public static ItemStack rollator;
    public static ItemStack nobel_praemie;
    public static ItemStack focken;
    public static ItemStack script;
    public static ItemStack stroke_master;
    public static ItemStack peilsender;
    public static ItemStack bound_peilsender;
    public static ItemStack kerze;
    public static ItemStack zehn_boehm_gutschein;

    // ==================== Vape Items ====================
    public static ItemStack vape_fruitberry;
    public static ItemStack vape_arschwasser;
    public static ItemStack vape_strawberry;
    public static ItemStack vape_mango;
    public static ItemStack vape_triple;
    public static ItemStack vape_arabics;
    public static ItemStack vape_air;
    public static ItemStack vape_empty;

    // ==================== UI Items ====================
    public static ItemStack strick;
    public static ItemStack spacer;
    public static ItemStack available_item;
    public static ItemStack not_available_item;
    public static ItemStack page_up;
    public static ItemStack page_down;
    public static ItemStack no_page_up;
    public static ItemStack no_page_down;
    public static ItemStack placeholder;
    //startGUI
    public static ItemStack start_game;
    public static ItemStack player_count_display;
    public static ItemStack gamemode_classic;
    public static ItemStack team_count;
    public static ItemStack choose_teams;

    // ==================== Team Backpack (Ranzen) Items ====================
    public static ItemStack nws_ranzen;
    public static ItemStack sprach_ranzen;
    public static ItemStack sport_ranzen;

    /**
     * Initializes all game items.
     * <p>
     * This method creates and registers all custom items used in the game.
     * Should be called once during plugin startup.
     * </p>
     */
    public static void initItems() {

        // ==================== Initialize Weapon Items ====================
        minas_flasche = weaponizeItem(createItem(Material.GLASS_BOTTLE, "§4Minas' Flasche", 1, 1, null, false, false, false), 3, 1, EquipmentSlot.HAND);
        itemList.add(minas_flasche);

        buffed_minas_flasche = weaponizeItem(createItem(Material.GLASS_BOTTLE, "§5§l§kA§r§4 Minas' Flasche §r§5§l§kA", 1, 1, null, true, false, false), 10, 0.95, EquipmentSlot.HAND);
        itemList.add(buffed_minas_flasche);

        schulbuch1 = weaponizeItem(createItem(Material.KNOWLEDGE_BOOK, ChatColor.BLUE + "Fokus Kampfkunst", 1, 1, null, false, false, false), 3, 1, EquipmentSlot.HAND);
        itemList.add(schulbuch1);

        schulbuch2 = weaponizeItem(createItem(Material.KNOWLEDGE_BOOK, ChatColor.DARK_BLUE + "Diercke Slam", 1, 1, null, false, false, false), 6, 1.1, EquipmentSlot.HAND);
        itemList.add(schulbuch2);

        schulbuch3 = weaponizeItem(createItem(Material.KNOWLEDGE_BOOK, ChatColor.RED + "Dornbader Schlag", 1, 1, null, false, false, false), 9, 1.2, EquipmentSlot.HAND);
        itemList.add(schulbuch3);

        schulbuch4 = weaponizeItem(createItem(Material.KNOWLEDGE_BOOK, ChatColor.GREEN + "Green Line to Hell", 1, 1, null, false, false, false), 12, 1.3, EquipmentSlot.HAND);
        itemList.add(schulbuch4);

        schulbuch5 = weaponizeItem(createItem(Material.KNOWLEDGE_BOOK, ChatColor.DARK_RED + "C.C.Buchner CHAOS", 1, 1, null, false, false, false), 15, 1.4, EquipmentSlot.HAND);
        itemList.add(schulbuch5);

        xlr_kabel = createItem(Material.LEAD, ChatColor.DARK_GRAY + "XLR Kabel", 1, 16, null, false, false, false);
        itemList.add(xlr_kabel);

        attack_stuhl = weaponizeItem(createItem(Material.OAK_STAIRS, ChatColor.WHITE + "Stuhl", 1, 1, null, false, false, false), 2, 0.5, EquipmentSlot.HAND);
        itemList.add(attack_stuhl);

        buffed_stuhl = weaponizeItem(createItem(Material.OAK_STAIRS, "§5§l§kA§r§f Stuhl §r§5§l§kA", 1, 1, null, true, false, false), 1, 0.5, EquipmentSlot.HAND);
        itemList.add(buffed_stuhl);

        machete = weaponizeItem(createItem(Material.IRON_SWORD, ChatColor.GRAY + "Pazifisten-Machete", 1, 1, null, true, false, false), 4, 0.1, EquipmentSlot.HAND);
        itemList.add(machete);

        // ==================== Initialize Support Items ====================
        ArrayList<String> kuehlpack_lore = new ArrayList<String>();
        kuehlpack_lore.add("§7Kühle hiermit die Verletzungen");
        kuehlpack_lore.add("§7deiner Mitstreiter und hilf ihnen so.");
        kuehlpack = createItem(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, "§bKühlpack", 1, 1, kuehlpack_lore, false, false, true);
        itemList.add(kuehlpack);

        leberkaese = createItem(Material.COOKED_PORKCHOP, "§4Leberkäse", 1, 1, null, false, false, false);
        itemList.add(leberkaese);

        cookie = createItem(Material.COOKIE, "§6Cookie", 1, 16, null, false, false, false);
        itemList.add(cookie);

        // ==================== Initialize Win Condition Items ====================
        stairway_heaven = createItem(Material.QUARTZ_STAIRS, ChatColor.LIGHT_PURPLE + "Stairway to heaven", 1, 1, null, true, false, false);
        itemList.add(stairway_heaven);

        //nukes
        nuke = createItem(Material.TNT, "§4Atombombe", 1, 1, null, true, false, false);
        itemList.add(nuke);

        uranium = createItem(Material.GREEN_DYE, "§aAngereichertes Uran-235", 1, 64, null, true, false, false);
        itemList.add(uranium);

        useless_uranium = createItem(Material.LIME_DYE, ChatColor.GREEN + "Uran", 1, 64, null, false, false, false);
        itemList.add(useless_uranium);

        fluor = createItem(Material.SUGAR, ChatColor.GRAY + "Fluor", 1, 64, null, false, false, false);
        itemList.add(fluor);

        versuch = createItem(Material.HEAVY_CORE, ChatColor.DARK_GRAY + "Taktischer Versuchsaufbau", 1, 16, null, false, false, false);
        itemList.add(versuch);

        emilia_ausland_brief = createItem(Material.PAPER, ChatColor.WHITE + "(Arbeitstitel)Emilia Ausland Brief", 1, 1, null, true, false, false);
        itemList.add(emilia_ausland_brief);

        baar_kaffee = createItem(Material.FLOWER_POT, ChatColor.GRAY + "Baars Kaffee", 1, 1, null, false, false, false);
        itemList.add(baar_kaffee);

        kaffeebohnen = createItem(Material.COCOA_BEANS, ChatColor.WHITE + "Kaffeebohnen", 1, 16, null, false, false, false);
        itemList.add(kaffeebohnen);

        verbrannte_bohnen = createItem(Material.COCOA_BEANS, ChatColor.WHITE + "Verbrannte Kaffeebohnen", 1, 16, null, false, false, false);
        itemList.add(verbrannte_bohnen);

        ArrayList<String> keks_lore = new ArrayList<String>();
        keks_lore.add(ChatColor.WHITE + "Keks von Herr Kesselring");
        keks_lore.add(ChatColor.WHITE + "Herr Geitners lieblings Snack");
        keks = createItem(Material.COOKIE, ChatColor.GOLD + "Keks", 1, 1, keks_lore, false, false, false);
        itemList.add(keks);

        aquatisiertes_fluor = createItem(Material.MILK_BUCKET, ChatColor.WHITE + "Aquatisiertes Fluor", 1, 1, null, false, false, false);
        itemList.add(aquatisiertes_fluor);

        natrium_fluorid = createItem(Material.CLAY_BALL, ChatColor.GRAY + "Natriumfluorid", 1, 1, null, false, false, false);
        itemList.add(natrium_fluorid);


        // ==================== Initialize Miscellaneous Items ====================
        ArrayList<String> moritz_hut_lore = new ArrayList<String>();
        moritz_hut_lore.add("§7Dieses Item representiert die Stärke");
        moritz_hut_lore.add("§7und Macht unseres Schulleiters Morgon!");
        moritz_hut_lore.add("§7Geehrt sei Morgon!");
        moritz_hut_lore.add("§7Victory at all costs!");
        moritz_hut_lore.add("§4Heil Morgon!");
        moritz_hut = createItem(Material.NETHERITE_HELMET, ChatColor.WHITE + "Moritz' Hut", 1, 1, moritz_hut_lore, true, false, false);
        itemList.add(moritz_hut);

        kaputtes_ipad = createItem(Material.NETHERITE_SCRAP, ChatColor.WHITE + "Kaputtes iPad", 1, 1, null, false, false, false);
        itemList.add(kaputtes_ipad);

        ipad = createItem(Material.IRON_INGOT, ChatColor.WHITE + "iPad", 1, 1, null, false, false, false);
        itemList.add(ipad);

        laptop = createItem(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, "§8iFake Laptop", 1, 1, null, false, false, false);
        itemList.add(laptop);

        zwiebel = createItem(Material.FERMENTED_SPIDER_EYE, ChatColor.LIGHT_PURPLE + "Zwiebel", 1, 16, null, false, false, false);
        itemList.add(zwiebel);

        geschimmelte_zwiebel = createItem(Material.FERMENTED_SPIDER_EYE, ChatColor.GREEN + "geschimmelte Zwiebel", 1, 16, null, false, false, false);
        itemList.add(geschimmelte_zwiebel);

        geschnittene_zwiebel = createItem(Material.NETHER_WART, ChatColor.LIGHT_PURPLE + "Ziebelstücke", 1, 16, null, false, false, false);
        itemList.add(geschnittene_zwiebel);

        ausleihschein = createItem(Material.PAPER, ChatColor.GRAY + "Bücherei Ausleihe", 1, 16, null, false, false, false);
        itemList.add(ausleihschein);

        fischers_spiel = createItem(Material.JUKEBOX, "§8Fischers Spielzeug", 1, 1, null, false, false, false);
        itemList.add(fischers_spiel);

        leere_tasse = createItem(Material.FLOWER_POT, ChatColor.WHITE + "Leere Tasse", 1, 1, null, false, false, false);
        itemList.add(leere_tasse);

        ArrayList<String> karls_elexir_lore = new ArrayList<String>();
        karls_elexir_lore.add(ChatColor.WHITE + "Nomen est omen");
        karls_elexir_lore.add(ChatColor.WHITE + "Mit Bedacht zu verwenden");
        karls_elexir_lore.add(ChatColor.WHITE + "Mögliche Nebenwirkungen: Tod");
        karls_elexier = createItem(Material.POTION,ChatColor.LIGHT_PURPLE + "Elexir Karls des Kleinen", 1, 1, karls_elexir_lore, false, false, true);
        itemList.add(karls_elexier);

        general_schluessel = createItem(Material.TRIPWIRE_HOOK, ChatColor.GOLD + "Generalschlüssel", 1, 1, null, false, false, false);
        itemList.add(general_schluessel);

        ArrayList<String> physikSchlüssel_lore = new ArrayList<String>();
        physikSchlüssel_lore.add(ChatColor.WHITE + "Öffnet die Blaue Box in der Physik Vorbereitung");
        fachraum_schrank_schluessel = createItem(Material.TRIPWIRE_HOOK, ChatColor.GOLD + "Fachraumschrankschlüssel", 1, 1, physikSchlüssel_lore, false, false, false);
        itemList.add(fachraum_schrank_schluessel);

        rollator = createItem(Material.NETHER_BRICK_FENCE, ChatColor.GRAY + "Rollator", 1, 1, null, false, false, false);
        itemList.add(rollator);

        nobel_praemie = createItem(Material.SUNFLOWER, ChatColor.GOLD + "Nōbelprämie", 1, 1, null, true, false, false);
        itemList.add(nobel_praemie);

        ArrayList<String> focken_lore = new ArrayList<String>();
        focken_lore.add("§7- " + ChatColor.LIGHT_PURPLE + "Fantastisches orientiertes cooles kurioses entanguliertes Neutron" + "§7 -");
        focken_lore.add("§7oder kurz, FOCKEN!");
        focken_lore.add("§7Keine Ahnung was das ist...");
        focken = createItem(Material.DARK_OAK_BUTTON, ChatColor.LIGHT_PURPLE + "Focken", 1, 1, focken_lore, true, false, false);
        itemList.add(focken);

        ArrayList<String> script_lore = new ArrayList<String>();
        script_lore.add("§l§f                   - Prolog -");
        script_lore.add("§fRegisseurin geht nachdenklich auf der Bühne auf und ab,");
        script_lore.add("§fsich gelegentlich die Hände reibend bzw. am Kopf kratzend.");
        script_lore.add("§fKritikerin liest am vorderen rechten Bühnenrand");
        script_lore.add("§fvöllig unbewegt „Die Kritik der reinen Vernunft“.");
        script = createItem(Material.PAPER, ChatColor.GOLD + "Script: Alice im Wunderland", 1, 1, script_lore, true, false, false);
        itemList.add(script);

        stroke_master = createItem(Material.ARMADILLO_SCUTE, ChatColor.DARK_PURPLE + "Stroke Master 3000", 1, 1, null, false, false, false);
        itemList.add(stroke_master);

        ArrayList<String> peilsender_lore = new ArrayList<>();
        peilsender_lore.add(ChatColor.DARK_GRAY + "Herr Fischer kann sich nicht länger verstecken!");
        peilsender = createItem(Material.COMPASS, ChatColor.DARK_GRAY + "Peilsender", 1, 1, peilsender_lore, false, false, false);
        itemList.add(peilsender);

        bound_peilsender = createItem(Material.COMPASS, ChatColor.DARK_GRAY + "Peilsender", 1, 1, peilsender_lore, true, false, false);
        itemList.add(bound_peilsender);

        kerze = createItem(Material.RED_CANDLE, ChatColor.RED + "Kerze", 1, 1, null, false, false, false);
        itemList.add(kerze);

        zehn_boehm_gutschein = createItem(Material.PAPER, ChatColor.WHITE + "10€ Böhms Gutschein", 1, 96, null, false, false, false);
        itemList.add(zehn_boehm_gutschein);

        // ==================== Initialize Vape Items ====================
        vape_fruitberry = createItem(Material.PINK_CANDLE, ChatColor.LIGHT_PURPLE + "Vape Fruitberry Punch", 1, 1, null, false, false, false);
        itemList.add(vape_fruitberry);

        vape_arschwasser = createItem(Material.LIME_CANDLE , ChatColor.GREEN + "Vape Tropical Arschwasser", 1, 1, null, false, false, false);
        itemList.add(vape_arschwasser);

        vape_strawberry = createItem(Material.RED_CANDLE, ChatColor.RED + "Vape Strawberry Burst", 1, 1, null, false, false, false);
        itemList.add(vape_strawberry);

        vape_mango = createItem(Material.ORANGE_CANDLE, ChatColor.YELLOW + "Vape Chewy Mango", 1, 1, null, false, false, false);
        itemList.add(vape_mango);

        vape_triple = createItem(Material.BLUE_CANDLE, ChatColor.BLUE + "Vape Triple Fruit Combo", 1, 1, null, false, false, false);
        itemList.add(vape_triple);

        vape_arabics = createItem(Material.BROWN_CANDLE, ChatColor.GRAY + "Vape Double Arabics", 1, 1, null, false, false, false);
        itemList.add(vape_arabics);

        vape_air = createItem(Material.WHITE_CANDLE, ChatColor.YELLOW + "Vape AIR", 1, 1, null, false, false, false);
        itemList.add(vape_air);

        vape_empty = createItem(Material.BLACK_CANDLE, ChatColor.WHITE + "Leere Vape", 1, 1, null, false, false, false);
        itemList.add(vape_empty);

        // ==================== Initialize UI Items ====================
        strick = createItem(Material.LEAD, ChatColor.BOLD + "" + ChatColor.DARK_RED + "Du bist momentan nicht in der Lage dich zu bewegen!", 2, 1, null, false, false, false);
        spacer = createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ", 20, 1, null, false, true, false);
        available_item = createItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Du kannst diese Quest abschließen!", 20, 1, null, false, false, false);
        not_available_item = createItem(Material.RED_STAINED_GLASS_PANE, ChatColor.DARK_RED + "Du kannst diese Quest noch nicht abschließen!", 20, 1, null, false, false, false);
        page_up = createItem(Material.ARROW, "§4Next Page", 21, 1, null, false, false, false);
        page_down = createItem(Material.ARROW, "§4Previous Page", 22, 1, null, false, false, false);
        no_page_up = createItem(Material.BARRIER, "§4No next Page available", 22, 1, null, false, false, false);
        no_page_down = createItem(Material.BARRIER, "§4No previous Page available", 21, 1, null, false, false, false);
        placeholder = createItem(Material.STRUCTURE_VOID, "§5§l§kA§r§7 PLACEHOLDER §r§5§l§kA", 1, 1, null, true, false, false);
        itemList.add(placeholder);
        //startGUI
        start_game = createItem(Material.LIME_CONCRETE, "§aSpiel starten", 1, 1, null, false, false, false);
        player_count_display = createItem(Material.PLAYER_HEAD, "§eAktuelle Spieleranzahl", 1, 64, null, false, false, false);

        ArrayList<String> gamemode_classic_lore = new ArrayList<String>();
        gamemode_classic_lore.add("§2-> Klassischer Modus");
        gamemode_classic_lore.add("§7... more comming soon (maybe)™");
        gamemode_classic = createItem(Material.KNOWLEDGE_BOOK, "§6Spielmodus: Klassisch", 1, 1, gamemode_classic_lore, false, false, false);
        team_count = createItem(Material.OAK_SIGN, "§bAnzahl der Teams", 1, 64, null, false, false, false);

        ArrayList<String> choose_teams_lore = new ArrayList<String>();
        choose_teams_lore.add("May come if dev not too lazy");
        choose_teams = createItem(Material.CHEST, "§bTeams auswählen", 1, 1, choose_teams_lore, false, false, false);

        // ==================== Initialize Team Backpack Items ====================
        ArrayList<String> ranzen_lore = new ArrayList<String>();
        ranzen_lore.add("§7- Dies ist dein Ranzen -");
        ranzen_lore.add("§7Beschütze und verstecke ihn");
        ranzen_lore.add("§7unter allen Umständen!");

        nws_ranzen = createItem(Material.GREEN_WOOL, "§2Grüner Ranzen", 1, 1, ranzen_lore, false, false, false);
        itemList.add(nws_ranzen);
        ranzenList.add(nws_ranzen);

        sprach_ranzen = createItem(Material.YELLOW_WOOL, "§6Gelber Ranzen", 1, 1, ranzen_lore, false, false, false);
        itemList.add(sprach_ranzen);
        ranzenList.add(sprach_ranzen);

        sport_ranzen = createItem(Material.RED_WOOL, "§4Roter Ranzen", 1, 1, ranzen_lore, false, false, false);
        itemList.add(sport_ranzen);
        ranzenList.add(sport_ranzen);

    }

    // ==================== Item Creation Utility Methods ====================

    /**
     * Creates a custom ItemStack with specified properties.
     *
     * @param material the material type for the item
     * @param name the display name of the item
     * @param customModelData custom model data for resource pack textures
     * @param stackSize maximum stack size for this item
     * @param lore list of lore lines, or null for no lore
     * @param glint whether the item should have an enchantment glint
     * @param hideTooltip whether to hide the entire tooltip
     * @param hideAdditionalTooltip whether to hide additional tooltip info
     * @return the created ItemStack
     */
    public static ItemStack createItem(Material material, String name, int customModelData, int stackSize, List<String> lore, boolean glint, boolean hideTooltip, boolean hideAdditionalTooltip) {

        ItemStack genItem = new ItemStack(material);
        ItemMeta meta = genItem.getItemMeta();

        meta.setMaxStackSize(stackSize);
        meta.setCustomModelData(customModelData);
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setEnchantmentGlintOverride(glint);
        meta.setHideTooltip(hideTooltip);

        if (hideAdditionalTooltip) meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);


        genItem.setItemMeta(meta);

        return genItem;
    }

    /**
     * Adds weapon attributes (damage and attack speed) to an item.
     *
     * @param item the item to modify
     * @param damage the attack damage value
     * @param attackSpeed the attack speed value
     * @param slot the equipment slot where the attributes apply
     * @return the modified item with weapon attributes
     */
    public static ItemStack weaponizeItem(ItemStack item, double damage, double attackSpeed, EquipmentSlot slot) {

        ItemMeta meta = item.getItemMeta();
        AttributeModifier damageModifier = new AttributeModifier(dmgAttributeKey, damage, AttributeModifier.Operation.ADD_NUMBER);
        AttributeModifier speedModifier = new AttributeModifier(spdAttributeKey, attackSpeed, AttributeModifier.Operation.ADD_NUMBER);

        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, speedModifier);

        item.setItemMeta(meta);

        return item;
    }

    /**
     * Spawns an item entity in the world with an invisible interaction hitbox for pickup detection.
     * <p>
     * The item is made persistent, invulnerable, and cannot be picked up normally.
     * An Interaction entity is created at the same location to detect player interactions.
     * </p>
     *
     * @param item the ItemStack to spawn
     * @param loc the location to spawn the item at
     */
    public static void createItemsEntity(ItemStack item, Location loc) {
        Block b = loc.getBlock();

        Item itemEntity = b.getWorld().dropItem(loc, item);
        itemEntity.setPersistent(true);
        itemEntity.setInvulnerable(true);
        itemEntity.setVelocity(new Vector(0, 0, 0));
        itemEntity.setPickupDelay(Integer.MAX_VALUE);

        Interaction itemHitbox = itemEntity.getWorld().spawn(itemEntity.getLocation(), Interaction.class);
        itemHitbox.setInteractionHeight(1);
        itemHitbox.setInteractionWidth(1);

        itemHitboxes.put(itemHitbox, itemEntity);
    }

    /**
     * Removes all spawned item entities and their hitboxes from the world.
     * <p>
     * Should be called when ending a game to clean up the world.
     * </p>
     */
    public static void clearSpawnedItems() {
        for (Interaction interaction : itemHitboxes.keySet()) {
            Item itemEntity = itemHitboxes.get(interaction);
            if (itemEntity != null) {
                itemEntity.remove();
            }
            interaction.remove();
        }
        itemHitboxes.clear();
    }
}
