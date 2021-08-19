package cn.fusionfish.fusionrpg.perks.impl.warrior;

import cn.fusionfish.fusionrpg.FusionRPG;
import cn.fusionfish.fusionrpg.perks.PerkException;
import cn.fusionfish.fusionrpg.perks.impl.Perk;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class WarriorSmash implements Perk {

    @Override
    public void execute(@NotNull Player player) {

        final World world = player.getWorld();
        Location location = player.getLocation();

        //如果玩家在空中，不释放技能
        if (location.clone().add(0,-1,0).getBlock().isEmpty()) {
            throw new PerkException();
        }

        //起跳时播放声音
        world.playSound(location, Sound.ENTITY_BAT_TAKEOFF, 1f, 1f);

        //给予起跳矢量
        Vector vector = location
                .getDirection()
                .clone()
                .setY(0)
                .normalize()
                .add(new Vector(0,1,0))
                .multiply(0.6);
        player.setVelocity(vector);

        new BukkitRunnable() {

            int ticks;

            @Override
            public void run() {

                Location loc = player.getLocation().add(0,-1,0);
                Block block = loc.getBlock();

                if (ticks == 50) {
                    this.cancel();
                }

                //落地结束runnable
                if (ticks > 5 && !block.isPassable()) {
                    world.spawnParticle(Particle.EXPLOSION_LARGE, player.getLocation(), 2, 1, 1, 1, 0.01);
                    world.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
                    world.getNearbyEntities(player.getLocation(), 3, 3, 3, entity -> entity.getUniqueId() != player.getUniqueId())
                            .stream()
                            .filter(entity -> entity instanceof LivingEntity)
                            .map(entity -> (LivingEntity) entity)
                            .forEach(entity -> {
                                Vector kb = entity.getLocation()
                                        .subtract(player.getLocation())
                                        .toVector()
                                        .setY(0)
                                        .normalize()
                                        .multiply(1.5)
                                        .add(new Vector(0, 1, 0));
                                entity.setVelocity(kb);
                                entity.damage(5, player);
                            });
                    this.cancel();
                }

                world.spawnParticle(Particle.SMOKE_NORMAL, player.getLocation(), 1, 0, 0, 0, 0.01);

                ticks++;
            }
        }.runTaskTimer(FusionRPG.getInstance(), 0L, 1L);
    }
}
