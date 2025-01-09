package de.kuscheltiermafia.schoolwars.teams;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

import static de.kuscheltiermafia.schoolwars.SchoolWars.world;
import static de.kuscheltiermafia.schoolwars.mechanics.Ranzen.ranzenAmount;

public enum Team {
    SPORTLER(ChatColor.DARK_RED + "sportler", ChatColor.DARK_RED + "[Sport] ", ChatColor.DARK_RED + "Sportler", Items.sport_ranzen, new Location(Bukkit.getWorld("schoolwars"), 68.5, 80.0, 167.0, 90, 0)),
    SPRACHLER(ChatColor.GOLD + "sprachler", ChatColor.GOLD + "[Sprache] ", ChatColor.GOLD + "Sprachler", Items.sprach_ranzen, new Location(Bukkit.getWorld("schoolwars"), -21.5, 88.0, 146.0, -90, 0)),
    NWS(ChatColor.GREEN + "Naturwissenschaftler", ChatColor.GREEN + "[NWS] ", ChatColor.GREEN + "Naturwissenschaftler", Items.nws_ranzen, new  Location(Bukkit.getWorld("schoolwars"), 4.5, 81.0, 191.5, 90, 0));

    public final String teamName;
    public final String prefix;
    public final String joinMessage;
    public final ItemStack ranzen;
    public final Location spawn;

    public ArrayList<String> mitglieder;
    public double sekiRisk;

    Team(String teamName, String prefix, String joinMessage, ItemStack ranzen, Location spawn) {
        this.teamName = teamName;
        this.prefix = prefix;
        this.joinMessage = joinMessage;
        this.ranzen = ranzen;
        this.spawn = spawn;

        mitglieder = new ArrayList<>();
    }


    public void readyPlayer(Player p){
        p.sendMessage(ChatColor.YELLOW + "[SchoolWars] Du bist ein " + joinMessage);

        p.setDisplayName(prefix + p.getName());
        p.setPlayerListName(prefix + p.getName());
        p.setCustomName(prefix + p.getName());

        SchoolWars.playerMirror.get(p.getName()).setTeam(this);

        if (!SchoolWars.gameStarted) {
            p.getInventory().addItem(ranzen);
            ranzenAmount.put(teamName, ranzenAmount.get(teamName) + 1);
            p.getInventory().addItem(Items.schulbuch1);
        }


        p.teleport(spawn);
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1, false, false, false));
    }


    public void prepare(){
        for(String s : mitglieder){
            Player p = Bukkit.getPlayer(s);
            readyPlayer(p);
        }
        sekiRisk = 0.0;
    }

}

