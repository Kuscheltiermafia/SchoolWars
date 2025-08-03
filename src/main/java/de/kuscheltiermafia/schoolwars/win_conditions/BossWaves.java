package de.kuscheltiermafia.schoolwars.win_conditions;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class BossWaves implements Listener {

    public static BossBar raithHealth;

    public void onFightStart(Player[] fighters) {

        raithHealth.setColor(org.bukkit.boss.BarColor.RED);
        raithHealth.setTitle(ChatColor.DARK_RED + "Raith Shadow Legends");
        raithHealth.setProgress(1.0);

        BossEntity.createRaithShadowLegends(new Location(SchoolWars.WORLD, 15.0, 31.0, 182.0));

        Arrays.stream(fighters).forEach(fighter -> {raithHealth.addPlayer(fighter);});

        new BukkitRunnable() {
            @Override
            public void run() {
                Arrays.stream(SchoolWars.WORLD.getNearbyEntities(BossEntity.RaithShadowLegends.getLocation(), 4, 4, 4).toArray()).forEach(entity -> {
                    if(entity instanceof Player) {
                        ((Player) entity).damage(3);
                    }
                });
                if(BossEntity.RaithShadowLegends.getHealth() <= 333 && BossEntity.RaithShadowLegends.getHealth() > 0) {

                }
            }
        }.runTaskTimer(SchoolWars.getPlugin(), 1, 25);
    }

    public void onBossHit(EntityDamageByEntityEvent e) {
        if(e.getEntity().equals(BossEntity.RaithShadowLegends)) {
            raithHealth.setProgress(BossEntity.RaithShadowLegends.getHealth() / 666);
        }
    }

}
