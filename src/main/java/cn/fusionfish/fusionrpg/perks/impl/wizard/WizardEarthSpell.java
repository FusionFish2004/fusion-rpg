package cn.fusionfish.fusionrpg.perks.impl.wizard;

import cn.fusionfish.fusionrpg.players.professions.WizardPlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class WizardEarthSpell extends WizardSpell {

    private final Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(137,85,56),1.5F);
    private final PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 20, 255, false);
    private final Consumer<LivingEntity> consumer = entity -> entity.addPotionEffect(effect);
    private final BlockData blockData = Bukkit.createBlockData(Material.DIRT);

    public WizardEarthSpell() {
        setConsumer(consumer);
        setDustOptions(dustOptions);
        setElement(WizardPlayer.Element.EARTH);
    }

    @Override
    protected void dead(@NotNull Snowball snowball) {
        Block block = snowball.getLocation().getBlock();
        World world = snowball.getWorld();
        Location location = snowball.getLocation();
        if (block.isEmpty()) {
            block.setType(Material.DIRT);
        }
        world.playSound(location, Sound.BLOCK_GRAVEL_BREAK, 1f, 1f);
        world.spawnParticle(Particle.BLOCK_CRACK, location, 5, 1,1,1,0.001, blockData);
    }
}
