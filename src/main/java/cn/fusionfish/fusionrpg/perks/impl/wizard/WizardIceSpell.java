package cn.fusionfish.fusionrpg.perks.impl.wizard;

import cn.fusionfish.fusionrpg.FusionRPG;
import cn.fusionfish.fusionrpg.players.professions.WizardPlayer;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class WizardIceSpell extends WizardSpell {

    private final Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(38,255,255),1.5F);
    private final PotionEffect slowEffect = new PotionEffect(PotionEffectType.SLOW, 50, 255, false);
    private final PotionEffect jumpEffect = new PotionEffect(PotionEffectType.JUMP, 50, 128, false);
    private final PotionEffect fallEffect = new PotionEffect(PotionEffectType.SLOW_FALLING, 50, 255, false);
    private final BlockData blockData = Bukkit.createBlockData(Material.BLUE_ICE);

    public WizardIceSpell() {
        setConsumer(consumer);
        setDustOptions(dustOptions);
        setElement(WizardPlayer.Element.ICE);
    }

    private final Consumer<LivingEntity> consumer = entity -> {

        Location buffer = entity.getLocation().clone();
        World world = buffer.getWorld();

        entity.addPotionEffect(slowEffect);
        entity.addPotionEffect(jumpEffect);
        entity.addPotionEffect(fallEffect);

        BukkitRunnable r = new BukkitRunnable() {
            int i;
            @Override
            public void run() {
                if (i == 50) {
                    this.cancel();
                }
                entity.setVelocity(new Vector().zero());
                world.spawnParticle(Particle.REDSTONE, entity.getLocation(), 5, 0.5,0.5,0.5,0.001,dustOptions);
                entity.setFireTicks(0);

                i++;
            }
        };
        r.runTaskTimer(FusionRPG.getInstance(),0L,1L);


        world.spawnParticle(Particle.BLOCK_CRACK,buffer, 5, 1, 1, 1, 0.001, blockData);
    };

    @Override
    protected void dead(@NotNull Snowball snowball) {
        World world = snowball.getWorld();
        Location location = snowball.getLocation();
        world.playSound(location, Sound.BLOCK_GLASS_BREAK, 1f,1f);
        world.spawnParticle(Particle.BLOCK_CRACK,location, 5, 0.5, 0.5, 0.5, 0.001, blockData);
    }
}
