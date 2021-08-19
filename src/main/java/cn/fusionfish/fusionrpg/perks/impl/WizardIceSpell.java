package cn.fusionfish.fusionrpg.perks.impl;

import cn.fusionfish.fusionrpg.FusionRPG;
import cn.fusionfish.fusionrpg.perks.PerkException;
import cn.fusionfish.fusionrpg.perks.ProjectileManager;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class WizardIceSpell implements Perk {

    private final Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(38,255,255),1.5F);
    private final PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 9999, 255, false);
    private final BlockData blockData = Bukkit.createBlockData(Material.BLUE_ICE);

    private final Consumer<LivingEntity> consumer = entity -> {

        Location buffer = entity.getLocation().clone();
        World world = buffer.getWorld();

        BukkitRunnable r = new BukkitRunnable() {
            int i;
            @Override
            public void run() {
                if (i == 50) {
                    this.cancel();
                }
                entity.setVelocity(new Vector().zero());
                entity.teleport(buffer);
                entity.addPotionEffect(effect);
                world.spawnParticle(Particle.REDSTONE, entity.getLocation(), 5, 0.5,0.5,0.5,0.001,dustOptions);
                i++;
            }
        };
        r.runTaskTimer(FusionRPG.getInstance(),0L,1L);

        world.playSound(buffer, Sound.BLOCK_GLASS_BREAK, 1f,1f);
        world.spawnParticle(Particle.BLOCK_CRACK,buffer, 5, 0.5, 0.5, 0.5, 0.001, blockData);
    };

    @Override
    public void execute(@NotNull Player player) throws PerkException {

        ProjectileManager projectileManager = ProjectileManager.getInstance();

        Vector direction = player.getLocation()
                .getDirection()
                .clone()
                .multiply(2);

        World world = player.getWorld();

        world.playSound(player.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1f, 1f);

        Snowball snowball = player.launchProjectile(Snowball.class, direction);

        projectileManager.launch(snowball, consumer);

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {

                Location location = snowball.getLocation().add(0,1,0);

                if (snowball.isDead()) {
                    this.cancel();
                    projectileManager.dead(snowball);
                    world.playSound(location, Sound.BLOCK_GLASS_BREAK, 1f,1f);
                    world.spawnParticle(Particle.BLOCK_CRACK,location, 5, 0.5, 0.5, 0.5, 0.001, blockData);
                }

                world.spawnParticle(Particle.REDSTONE, location, 1, 0,0,0,0.001,dustOptions);
            }
        };
        runnable.runTaskTimer(FusionRPG.getInstance(), 0L, 1L);

    }
}
