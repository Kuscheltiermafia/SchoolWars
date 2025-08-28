package de.kuscheltiermafia.schoolwars.win_conditions;

import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.lehrer.Area;
import de.kuscheltiermafia.schoolwars.lehrer.Lehrer;
import de.kuscheltiermafia.schoolwars.mechanics.ParticleHandler;
import de.kuscheltiermafia.schoolwars.win_conditions.events_atombombe.AtombombeEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AtombombeBossfight implements Listener {

    private static Location bossSpawnLocation = new Location(SchoolWars.WORLD, 13.0, 32.0, 180.0);
    private static double health = 150;

    private static Camel HerrRaithen;
    private static Vindicator RaithenAI;
    private static BossBar raithenHealth;

    private static int fightPhaseStep;
    private static ArrayList<Vindicator> minions = new ArrayList<>();

    private static ArrayList<Player> fighters;

    public static void startBossfight() {
        new BukkitRunnable() {
            @Override
            public void run() {
                HerrRaithen = SchoolWars.WORLD.spawn(bossSpawnLocation, Camel.class);
                fighters = Area.getPlayersInArea(new Location(SchoolWars.WORLD, -8.0, 32.0, 162.0), new Location(SchoolWars.WORLD, 32.0, 54.0, 196.0));

                RaithenAI = SchoolWars.WORLD.spawn(bossSpawnLocation, Vindicator.class);
                RaithenAI.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(4);
                RaithenAI.clearLootTable();
                RaithenAI.getEquipment().clear();
                RaithenAI.setInvisible(true);
                RaithenAI.setInvulnerable(true);

                HerrRaithen.setMaxHealth(health);
                HerrRaithen.setHealth(health);
                HerrRaithen.clearLootTable();
                HerrRaithen.setCustomName(ChatColor.DARK_RED + "Herr Raithen");
                HerrRaithen.setCustomNameVisible(true);

                HerrRaithen.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);
                HerrRaithen.addPassenger(RaithenAI);

                fightPhaseStep = (int) health;

                raithenHealth = Bukkit.createBossBar("Herr Raithen", BarColor.RED, BarStyle.SOLID);
                updateBossbar(raithenHealth, HerrRaithen);

                for (Player p : fighters) {
                    raithenHealth.addPlayer(p);
                }
            }
        }.runTaskLater(SchoolWars.getPlugin(), 20 * 5);
    }

    private static void bossPhase(){
        ArrayList<Lehrer> lehrerList = new ArrayList<>(List.of(Lehrer.values()));
        Collections.shuffle(lehrerList);

        for (int i = 0; i < 5; i++) {
            double angle = Math.random() * 2 * Math.PI;
            double xOffset = 6 * Math.cos(angle);
            double zOffset = 6 * Math.sin(angle);

            Location bossLocation = HerrRaithen.getLocation();
            Location spawnLocation = bossLocation.clone().add(xOffset, 0, zOffset);

            Vindicator minion = SchoolWars.WORLD.spawn(spawnLocation, Vindicator.class);
            minion.setMaxHealth(16);
            minion.setHealth(16);
            minion.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
            minion.setCustomNameVisible(true);
            minion.clearLootTable();

            if(lehrerList.get(i).isMale()) {
                minion.setCustomName(ChatColor.RED + "Herr " + lehrerList.get(i).getName());
                minions.add(minion);
            }else{
                minion.setCustomName(ChatColor.RED + "Frau " + lehrerList.get(i).getName());
                minions.add(minion);
            }

            ParticleHandler.createParticleCircle(spawnLocation, Particle.END_ROD, 1.75, 10);
            ParticleHandler.drawParticleLine(HerrRaithen.getLocation().add(0, 1, 0), spawnLocation.add(0, 1, 0), Particle.CRIT, 0.5);
        }
    }

    private static void onPhaseToggle(boolean direct) {
        if(direct) {
            HerrRaithen.setAI(true);
            RaithenAI.setAI(true);
            HerrRaithen.setInvulnerable(false);
        }else{
            HerrRaithen.setAI(false);
            RaithenAI.setAI(false);
            HerrRaithen.setInvulnerable(true);
        }
    }

    @EventHandler
    public void onBossHit(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        if (entity.equals(HerrRaithen)) {
            if(HerrRaithen.hasAI()) {
                Camel raithen = (Camel) entity;
                updateBossbar(raithenHealth, (Camel) raithen);
                if (raithen.getHealth() < fightPhaseStep && fightPhaseStep > 20) {
                    raithen.setHealth(fightPhaseStep);
                    onPhaseToggle(false);
                    bossPhase();
                }
            }else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMinionKill(EntityDeathEvent e) {
        if(e.getEntity() instanceof Vindicator) {
            Vindicator minion = (Vindicator) e.getEntity();
            if(minions.contains(minion)) {
                minions.remove(minion);
                if(minions.isEmpty()) {
                    fightPhaseStep -= 50;
                    onPhaseToggle(true);
                }
            }
        } else if (e.getEntity().equals(HerrRaithen)) {
            raithenHealth.removeAll();
            HerrRaithen.remove();
            RaithenAI.remove();

            AtombombeEvents.stopNuke();
        }
    }

    private static void updateBossbar(BossBar bar, Camel boss) {
        double health = boss.getHealth();
        double maxHealth = boss.getMaxHealth();
        bar.setProgress(health / maxHealth);
    }
}
