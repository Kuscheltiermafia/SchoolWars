package de.kuscheltiermafia.schoolwars.events;

import de.kuscheltiermafia.schoolwars.items.Items;
import de.kuscheltiermafia.schoolwars.mechanics.ParticleHandler;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.HashMap;

public class Vapes implements Listener {

    @EventHandler
    public void onVapeUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (getVapeEffects().containsKey(e.getItem())) {
                e.setCancelled(true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 1, false, false, false));
                ParticleHandler.createParticles(p.getEyeLocation(), Particle.POOF, 10, 0, true, null);

                for(ItemStack vape : getVapeEffects().keySet()) {
                    if (e.getItem().equals(vape)) {
                        PotionEffectType effectType = getVapeEffects().get(vape);

                        p.addPotionEffect(new PotionEffect(effectType, 20 * 20, 1, false, false, false));
                        p.getInventory().remove(e.getItem());
                        break;
                    }
                }
            }
        }
    }

    private HashMap<ItemStack, PotionEffectType> getVapeEffects() {
        HashMap<ItemStack, PotionEffectType> vapeEffects = new HashMap<>();
        vapeEffects.put(Items.vape_fruitberry, PotionEffectType.STRENGTH);
        vapeEffects.put(Items.vape_arschwasser, PotionEffectType.INSTANT_HEALTH);
        vapeEffects.put(Items.vape_strawberry, PotionEffectType.JUMP_BOOST);
        vapeEffects.put(Items.vape_mango, PotionEffectType.SPEED);
        vapeEffects.put(Items.vape_triple, PotionEffectType.REGENERATION);
        vapeEffects.put(Items.vape_arabics, PotionEffectType.NIGHT_VISION);
        vapeEffects.put(Items.vape_air, PotionEffectType.SLOW_FALLING);
        return vapeEffects;
    }
}
