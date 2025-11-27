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
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
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

import java.util.HashMap;

/**
 * Command that opens a paginated GUI displaying all game items.
 * <p>
 * This command allows administrators to browse all available items in the game.
 * The GUI supports pagination for navigating through large item lists.
 * </p>
 */
@CommandAlias("itemlist")
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
    @CommandPermission("schoolwars.itemlist")
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
            itemList.setItem(0, new ItemStack(Items.no_page_down));
        }else{itemList.setItem(0, new ItemStack(Items.page_down));}

        if((itemListPage.get(user) + 1) * 28 <= Items.itemList.size()) {
            itemList.setItem(2, new ItemStack(Items.page_up));
        }else{itemList.setItem(2, new ItemStack(Items.no_page_up));}

        itemList.setItem(1, new ItemStack(Items.createItem(Material.BOOK, ChatColor.DARK_RED + "Current Page: " + itemListPage.get(user), 20, 1, null, false, false, false)));

        for(int i = 0; i < spacers.length; i++) {
            int slot = spacers[i];
            itemList.setItem(slot, new ItemStack(Items.spacer));
        }

        for (int i = 0; i < 28; i++) {
            itemList.addItem(Items.itemList.get(i + pageModi));
        }

    }

}
