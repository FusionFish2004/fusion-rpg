package cn.fusionfish.fusionrpg.perks.impl.assassin;

import cn.fusionfish.fusionrpg.FusionRPG;
import cn.fusionfish.fusionrpg.perks.PerkException;
import cn.fusionfish.fusionrpg.perks.impl.Perk;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

public class AssassinSprint implements Perk {
    @Override
    public void execute(@NotNull Player player) throws PerkException {
        Location location = player.getLocation();
        World world = player.getWorld();
        Vector sprint = location.getDirection()
                .setY(0)
                .normalize()
                .multiply(2);
        player.setVelocity(sprint);

        world.playSound(location, Sound.ENTITY_ARROW_SHOOT, 1f, 1f);

        BukkitRunnable runnable = new BukkitRunnable() {

            int ticks = 0;

            @Override
            public void run() {

                Location loc = player.getLocation();

                if (ticks == 20) {
                    this.cancel();
                    return;
                }

                Set<LivingEntity> hitBuffer = player.getNearbyEntities(1,1,1)
                        .stream()
                        .filter(entity -> entity.getUniqueId() != player.getUniqueId())
                        .filter(entity -> entity instanceof LivingEntity)
                        .map(entity -> (LivingEntity) entity)
                        .collect(Collectors.toSet());

                if (!hitBuffer.isEmpty()) {
                    this.cancel();
                    //TODO 撞击粒子，声效

                    player.setVelocity(new Vector().zero());
                    PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 10, 2, false, false);
                    player.addPotionEffect(effect);

                    hitBuffer.forEach(entity -> {
                        Vector vector = entity.getLocation()
                                .subtract(loc)
                                .toVector()
                                .normalize()
                                .multiply(2);
                        entity.damage(1, player);
                        entity.setVelocity(vector);
                    });

                    return;
                }

                ticks++;
            }
        };
        runnable.runTaskTimer(FusionRPG.getInstance(), 0L, 1L);
    }
}
