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
import de.kuscheltiermafia.schoolwars.utils.GsonHandler;
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
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.*;

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
    public static HashMap<String, ItemStack> itemList = new HashMap<>();
    public static ArrayList<ItemStack> hiddenItems = new ArrayList<>();

    /** Map linking Interaction entities to their corresponding Item entities for pickups. */
    public static HashMap<Interaction, Item> itemHitboxes = new HashMap<>();

    /** List of all team backpack items. */
    public static ArrayList<ItemStack> ranzenList = new ArrayList<ItemStack>();

    /** Namespaced key for item id. */
    public static NamespacedKey itemIDKey = new NamespacedKey(SchoolWars.getPlugin(), "schoolwars_item");

    /** Namespaced key for damage attribute modifications. */
    public static NamespacedKey dmgAttributeKey = new NamespacedKey(SchoolWars.getPlugin(), "attribute_key_dmg");
    
    /** Namespaced key for attack speed attribute modifications. */
    public static NamespacedKey spdAttributeKey = new NamespacedKey(SchoolWars.getPlugin(), "attribute_key_spd");

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

        Map<String, ItemBuilder> storedItems = GsonHandler.fromJsonToMap("items", ItemBuilder.class);

        for (String key : storedItems.keySet()) {
            ItemBuilder builder = storedItems.get(key);
            ItemStack item = builder.setID(key).build();
        }

        // ==================== Initialize Debug Items ====================
        new ItemBuilder("strick", Material.LEAD)
                .setDisplayName(ChatColor.BOLD + "" + ChatColor.DARK_RED + "Du bist momentan nicht in der Lage dich zu bewegen!")
                .setStackSize(1)
                .setCustomModelData(1)
                .hideTooltip()
                .build();

        new ItemBuilder("spacer", Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .setDisplayName(" ")
                .setStackSize(1)
                .setCustomModelData(20)
                .hideInItemList()
                .hideTooltip()
                .build();

        new ItemBuilder("available_item", Material.LIME_STAINED_GLASS_PANE)
                .setDisplayName(ChatColor.GREEN + "Du kannst diese Quest abschließen!")
                .setStackSize(1)
                .setCustomModelData(20)
                .hideInItemList()
                .build();

        new ItemBuilder("not_available_item", Material.RED_STAINED_GLASS_PANE)
                .setDisplayName(ChatColor.DARK_RED + "Du kannst diese Quest noch nicht abschließen!")
                .setStackSize(1)
                .setCustomModelData(20)
                .hideInItemList()
                .build();

        new ItemBuilder("page_up", Material.ARROW)
                .setDisplayName("§4Next Page")
                .setStackSize(1)
                .setCustomModelData(21)
                .hideInItemList()
                .build();

        new ItemBuilder("page_down", Material.ARROW)
                .setDisplayName("§4Previous Page")
                .setStackSize(1)
                .setCustomModelData(22)
                .hideInItemList()
                .build();

        new ItemBuilder("no_page_up", Material.BARRIER)
                .setDisplayName("§4No next Page available")
                .setStackSize(1)
                .setCustomModelData(22)
                .hideInItemList()
                .build();

        new ItemBuilder("no_page_down", Material.BARRIER)
                .setDisplayName("§4No previous Page available")
                .setStackSize(1)
                .setCustomModelData(21)
                .hideInItemList()
                .build();

        new ItemBuilder("placeholder", Material.STRUCTURE_VOID)
                .setDisplayName("§5§l§kA§r§7 PLACEHOLDER §r§5§l§kA")
                .setStackSize(1)
                .setCustomModelData(1)
                .hideInItemList()
                .build();


        //startGUI
        new ItemBuilder("start_game", Material.LIME_CONCRETE)
                .setDisplayName("§aSpiel starten")
                .setStackSize(1)
                .hideInItemList()
                .build();

        new ItemBuilder("player_count_display", Material.PLAYER_HEAD)
                .setDisplayName("§eAktuelle Spieleranzahl")
                .setStackSize(64)
                .hideInItemList()
                .build();

        new ItemBuilder("gamemode_classic", Material.KNOWLEDGE_BOOK)
                .setDisplayName("§6Spielmodus: Klassisch")
                .setStackSize(1)
                .setLore("§2-> Klassischer Modus", "§7... more comming soon (maybe)™")
                .hideInItemList()
                .build();

        new ItemBuilder("team_count", Material.OAK_SIGN)
                .setDisplayName("§bAnzahl der Teams")
                .setStackSize(64)
                .hideInItemList()
                .build();

        new ItemBuilder("choose_teams", Material.CHEST)
                .setDisplayName("§bTeams auswählen")
                .setStackSize(1)
                .setLore("May come if dev not too lazy")
                .hideInItemList()
                .build();

        // ==================== Initialize Team Backpack Items ====================
        nws_ranzen = new ItemBuilder("nws_ranzen", Material.GREEN_WOOL)
                .setDisplayName("§2Grüner Ranzen")
                .setStackSize(1)
                .setLore("§7- Dies ist dein Ranzen -", "§7Beschütze und verstecke ihn", "§7unter allen Umständen!")
                .build();

        sprach_ranzen = new ItemBuilder("sprach_ranzen", Material.YELLOW_WOOL)
                .setDisplayName("§6Gelber Ranzen")
                .setStackSize(1)
                .setLore("§7- Dies ist dein Ranzen -", "§7Beschütze und verstecke ihn", "§7unter allen Umständen!")
                .build();

        sport_ranzen = new ItemBuilder("sport_ranzen", Material.RED_WOOL)
                .setDisplayName("§4Roter Ranzen")
                .setStackSize(1)
                .setLore("§7- Dies ist dein Ranzen -", "§7Beschütze und verstecke ihn", "§7unter allen Umständen!")
                .build();

        ranzenList.add(nws_ranzen);
        ranzenList.add(sprach_ranzen);
        ranzenList.add(sport_ranzen);
    }

    public static ItemStack getItem(String itemID) {
        return itemList.get(itemID);
    }

    public static boolean isSpecificItem(ItemStack item, String itemID) {
        if(item.getItemMeta() == null) return false;
        return item.getItemMeta().getPersistentDataContainer().has(itemIDKey) && Objects.equals(item.getItemMeta().getPersistentDataContainer().get(itemIDKey, PersistentDataType.STRING), itemID);
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
