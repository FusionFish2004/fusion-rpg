package cn.fusionfish.fusionrpg.perks.impl;

import cn.fusionfish.fusionrpg.FusionRPG;
import cn.fusionfish.fusionrpg.perks.PerkException;
import com.google.common.collect.Maps;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WizardIceSpell implements Perk {
    @Override
    public void execute(@NotNull Player player) throws PerkException {

        Location spirit = player.getLocation()
                .clone()
                .add(new Vector(0,player.getEyeHeight(),0));

        Vector direction = player.getLocation()
                .getDirection()
                .clone();

        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(38,255,255),1.5F);
        World world = player.getWorld();

        world.playSound(spirit, Sound.ENTITY_SNOWBALL_THROW, 1f, 1f);
        BlockData blockData = Bukkit.createBlockData(Material.BLUE_ICE);

        BukkitRunnable runnable = new BukkitRunnable() {
            int ticks;

            @Override
            public void run() {
                if (ticks == 200) {
                    this.cancel();
                }

                spirit.add(direction);

                world.spawnParticle(Particle.REDSTONE, spirit, 1, 0.1, 0.1, 0.1, 0.001, dustOptions);

                Block block = spirit.getBlock();

                if (!block.isPassable()) {
                    world.playSound(spirit, Sound.BLOCK_GLASS_BREAK, 1f, 1f);
                    world.spawnParticle(Particle.BLOCK_CRACK, spirit, 5, 0.5,0.5,0.5,0.001,blockData);
                    this.cancel();
                }

                Set<LivingEntity> nearbyEntities = world.getNearbyEntities(spirit, 1, 1, 1, entity -> entity != player)
                        .stream()
                        .filter(entity -> entity instanceof LivingEntity)
                        .map(entity -> (LivingEntity) entity)
                        .collect(Collectors.toSet());

                if (!nearbyEntities.isEmpty()) {
                    world.playSound(spirit, Sound.BLOCK_GLASS_BREAK, 1f, 1f);
                    PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 50, 255, false, false);

                    nearbyEntities.forEach(e -> {
                        Block b = e.getLocation().getBlock();
                        if (b.isEmpty()) {
                            //b.setType(Material.FROSTED_ICE);
                        }
                    });

                    Map<LivingEntity, Location> entityLocationMap = Maps.asMap(nearbyEntities, Entity::getLocation);

                    BukkitRunnable r = new BukkitRunnable() {
                        int i;
                        @Override
                        public void run() {
                            if (i == 50) {
                                this.cancel();
                            }
                            nearbyEntities.forEach(entity -> {
                                entity.setVelocity(new Vector().zero());
                                entity.teleport(entityLocationMap.get(entity));
                                entity.addPotionEffect(effect);
                                world.spawnParticle(Particle.REDSTONE, entity.getLocation(), 5, 0.5,0.5,0.5,0.001,dustOptions);
                            });
                            i++;
                        }
                    };
                    r.runTaskTimer(FusionRPG.getInstance(),0L,1L);

                    world.spawnParticle(Particle.BLOCK_CRACK, spirit, 5, 0.1,0.1,0.1,0.001,blockData);
                    this.cancel();
                }


                ticks++;
            }
        };
        runnable.runTaskTimer(FusionRPG.getInstance(),0L,1L);
    }
}
