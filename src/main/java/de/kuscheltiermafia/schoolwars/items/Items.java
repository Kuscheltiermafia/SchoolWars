package de.kuscheltiermafia.schoolwars.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Items {

    public static ItemStack minas_flasche;
    public static ItemStack laptop;

    public static ItemStack placeholder;
    public static ItemStack spacer;
    public static ItemStack nuke;



    public static void initItems() {

        minas_flasche = createItem(Material.GLASS_BOTTLE, "§4Minas' Flasche", 1, 1, null, false);
        placeholder = createItem(Material.STRUCTURE_VOID, "§5§l§kA§r§7 PLACEHOLDER §r§5§l§kA", 1, 1, null, true);
        spacer = createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ", 2, 1, null, false);
        nuke = createItem(Material.TNT, "§4Atombombe", 1, 1, null, true);
        laptop = createItem(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, "§8iFake Laptop", 1, 1, null, false);

    }

    public static ItemStack createItem(Material material, String name, int id, int stackSize, List<String> lore, boolean glint) {

        ItemStack genItem = new ItemStack(material);
        ItemMeta meta = genItem.getItemMeta();

        meta.setMaxStackSize(stackSize);
        meta.setCustomModelData(id);
        meta.setItemName(name);
        meta.setLore(lore);
        meta.setEnchantmentGlintOverride(glint);

        genItem.setItemMeta(meta);

        return genItem;
    }
}
