package cn.fusionfish.fusionrpg.perks.impl;

import cn.fusionfish.fusionrpg.FusionRPG;
import cn.fusionfish.fusionrpg.perks.PerkException;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class WizardFireSpell implements Perk {
    @Override
    public void execute(@NotNull Player player) throws PerkException {

        Location spirit = player.getLocation()
                .clone()
                .add(new Vector(0,player.getEyeHeight(),0));

        Vector direction = player.getLocation()
                .getDirection()
                .clone();

        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255,98,38),1.5F);
        World world = player.getWorld();

        world.playSound(spirit, Sound.ITEM_FIRECHARGE_USE, 1f, 1f);

        BukkitRunnable runnable = new BukkitRunnable() {
            int ticks;

            @Override
            public void run() {
                if (ticks == 200) {
                    this.cancel();
                }

                spirit.add(direction);

                world.spawnParticle(Particle.REDSTONE, spirit, 1, 0.1, 0.1, 0.1, 0.001, dustOptions);
                world.spawnParticle(Particle.FLAME, spirit, 2, 0.1, 0.1, 0.1, 0.001);

                Block block = spirit.getBlock();
                if (!block.isPassable()) {
                    this.cancel();
                }

                Collection<Entity> nearbyEntities = world.getNearbyEntities(spirit, 1, 1, 1, entity -> entity != player);
                if (!nearbyEntities.isEmpty()) {
                    nearbyEntities.forEach(entity -> entity.setFireTicks(100));
                    this.cancel();
                }


                ticks++;
            }
        };
        runnable.runTaskTimer(FusionRPG.getInstance(),0L,1L);
    }
}
