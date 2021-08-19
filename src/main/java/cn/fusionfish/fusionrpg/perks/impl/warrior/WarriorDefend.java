package cn.fusionfish.fusionrpg.perks.impl.warrior;

import cn.fusionfish.fusionrpg.FusionRPG;
import cn.fusionfish.fusionrpg.perks.PerkException;
import cn.fusionfish.fusionrpg.perks.impl.Perk;
import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Set;

public class WarriorDefend implements Perk {
    @Override
    public void execute(@NotNull Player player) throws PerkException {
        ItemStack main = player.getInventory().getItemInMainHand();
        ItemStack off = player.getInventory().getItemInOffHand();
        World world = player.getWorld();
        Location location = player.getLocation();

        if (!(main.getType() == Material.SHIELD || off.getType() == Material.SHIELD)) {
            throw new PerkException();
        }

        world.playSound(location, Sound.BLOCK_BEACON_ACTIVATE, 1f, 1f);
        PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 50, 2, false, false);
        player.addPotionEffect(effect);

        BukkitRunnable runnable = new BukkitRunnable() {

            int ticks = 0;
            final Set<Projectile> hitBuffer = Sets.newHashSet();

            @Override
            public void run() {

                Location loc = player.getLocation();

                if (ticks == 50) {
                    this.cancel();
                    world.playSound(loc, Sound.BLOCK_GLASS_BREAK, 1f, 1f);
                    player.setCooldown(Material.SHIELD, 40);
                    player.setShieldBlockingDelay(40);
                    return;
                }

                if (hitBuffer.size() == 3) {
                    this.cancel();
                    world.playSound(loc, Sound.BLOCK_GLASS_BREAK, 1f, 1f);
                    player.removePotionEffect(PotionEffectType.SLOW);
                    player.setCooldown(Material.SHIELD, 40);
                    player.setShieldBlockingDelay(40);
                    return;
                }

                ticks++;

                Projectile projectile = world.getNearbyEntities(loc, 3, 3, 3, entity -> entity.getUniqueId() != player.getUniqueId())
                        .stream()
                        .filter(entity -> entity instanceof Projectile)
                        .filter(entity -> !entity.isOnGround())
                        .map(entity -> (Projectile) entity)
                        .min(Comparator.comparing(p -> loc.distance(p.getLocation())))
                        .orElse(null);

                if (projectile == null) return;

                if (hitBuffer.contains(projectile)) return;

                Location entityLocation = projectile.getLocation();
                world.spawnParticle(Particle.CLOUD, entityLocation, 3, 0.1, 0.1, 0.1, 0.01);
                world.playSound(entityLocation, Sound.ITEM_SHIELD_BLOCK, 0.8F, 1F);
                Vector v = entityLocation.subtract(loc)
                        .toVector()
                        .normalize()
                        .multiply(0.5);
                projectile.setVelocity(v);

                hitBuffer.add(projectile);
                //projectile.remove();


            }
        };
        runnable.runTaskTimer(FusionRPG.getInstance(), 0L, 1L);
    }
}
