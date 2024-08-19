package de.kuscheltiermafia.schoolwars.commands;

import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class adminItems implements CommandExecutor{

    public static int[] spacers = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 18, 27, 36, 45, 17, 26, 35, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player) {

            Player p = (Player) sender;

            Inventory itemList = Bukkit.createInventory(null, 9*6, "§4All Items");

            for(int i = 0; i < spacers.length; i++) {
                int slot = spacers[i];
                itemList.setItem(slot, new ItemStack(Items.spacer));
            }

            itemList.addItem(new ItemStack(Items.minas_flasche));
            itemList.addItem(new ItemStack(Items.nuke));
            itemList.addItem(new ItemStack(Items.placeholder));
            itemList.addItem(new ItemStack(Items.laptop));

            p.openInventory(itemList);

        }
        return false;
    }

}
