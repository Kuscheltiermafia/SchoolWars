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

package de.kuscheltiermafia.schoolwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import de.kuscheltiermafia.schoolwars.items.ItemBuilder;
import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Command that opens a paginated GUI displaying all game items.
 * <p>
 * This command allows administrators to browse all available items in the game.
 * The GUI supports pagination for navigating through large item lists.
 * </p>
 */
@CommandAlias("itemlist")
@Description("Öffnet die Itemliste.")
public class ItemList extends BaseCommand {

    /** Tracks the current page each player is viewing. */
    public static HashMap<Player, Integer> itemListPage = new HashMap<Player, Integer>();

    /** Slot indices used for spacer items in the GUI. */
    public static int[] spacers = new int[]{3, 4, 5, 6, 7, 8, 9, 18, 27, 36, 45, 17, 26, 35, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};

    /**
     * Opens the item list GUI for the player.
     *
     * @param sender the command sender (must be a player)
     */
    @Default
    @CommandPermission("schoolwars.command.itemlist")
    public void onCommand(CommandSender sender) {

        if(sender instanceof Player) {
            Player p = (Player) sender;

            Inventory itemList = Bukkit.createInventory(null, 9*6, "§4Itemlist");

            itemListPage.put(p, 0);

            fillItemlist(itemList, itemListPage.get(p), p);

            p.openInventory(itemList);
        }
    }

    /**
     * Populates the item list inventory with items for the specified page.
     *
     * @param itemList the inventory to fill
     * @param currentPage the page number to display (0-indexed)
     * @param user the player viewing the inventory
     */
    public static void fillItemlist(Inventory itemList, int currentPage, Player user) {

        int pageModi = 28 * (currentPage);

        for(int i = 0; i < itemList.getSize(); i++) {
            itemList.setItem(i, new ItemStack(Material.AIR));
        }

        if(itemListPage.get(user) == 0) {
            itemList.setItem(0, Items.getItem("no_page_down"));
        }else{itemList.setItem(0, Items.getItem("page_down"));}

        if((itemListPage.get(user) + 1) * 28 <= Items.itemList.size()) {
            itemList.setItem(2, Items.getItem("page_up"));
        } else {
            itemList.setItem(2, Items.getItem("no_page_up"));
        }

        ItemStack pageIndicator = new ItemBuilder("page_indicator", Material.BOOK).hideInItemList().setDisplayName(ChatColor.DARK_RED + "Current Page: " + (itemListPage.get(user) + 1)).hasGlint().build();
        itemList.setItem(1, pageIndicator);

        for(int i = 0; i < spacers.length; i++) {
            int slot = spacers[i];
            ItemStack spacer = Items.getItem("spacer");
            if (spacer != null) itemList.setItem(slot, spacer);
        }

        //Clear-Up item list
        List<ItemStack> allItems = new ArrayList<>(Items.itemList.values());
        allItems.removeIf(s -> Items.hiddenItems.contains(s));

        for (int i = 0; i < 28; i++) {
            int idx = i + pageModi;
            if (idx >= allItems.size()) break;
            itemList.addItem(allItems.get(idx));
        }

    }

}
