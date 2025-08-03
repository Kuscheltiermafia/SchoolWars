package de.kuscheltiermafia.schoolwars.win_conditions;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class BossEntity {

    public static Villager RaithShadowLegends;
    public static ArrayList<Villager> minions = new ArrayList<>();

    public static Villager createRaithShadowLegends(Location loc) {

        Villager boss = loc.getWorld().spawn(loc, Villager.class);

        boss.setCustomName(ChatColor.DARK_RED + "Raith Shadow Legends");
        boss.setCustomNameVisible(true);

        boss.setMaxHealth(666);

        RaithShadowLegends = boss;
        return boss;
    }

    public static void minions() {
        RaithShadowLegends.setInvulnerable(true);
        RaithShadowLegends.setAI(false);

        RaithShadowLegends.teleport(RaithShadowLegends.getLocation().add(0, 4, 0));

        new BukkitRunnable() {
            final double[] y = {0};
            @Override
            public void run() {
                Location loc1 = RaithShadowLegends.getLocation();
                Location loc2 = RaithShadowLegends.getLocation();

                double x = 0.7 * Math.sin(y[0]);
                double z = 0.7 * Math.cos(y[0]);

                for (Player a: Bukkit.getOnlinePlayers()
                ) {
                    a.spawnParticle(Particle.FLAME, loc1.add(x, 0, z), 0, 0, 0.2, 0);
                    a.spawnParticle(Particle.FLAME, loc2.add(-x, 0, -z), 0, 0, 0.2, 0);

                    loc1.add(-x, 0, -z);
                    loc2.add(x, 0, z);
                }
                if (y[0] <= 2 * Math.PI) {
                    y[0] = y[0] + 0.1;
                } else y[0] = 0;
            }
        }.runTaskTimer(SchoolWars.getPlugin(), 1, 1);

        for(int i = 0; i < 5; i++) {

        }
    }
}
