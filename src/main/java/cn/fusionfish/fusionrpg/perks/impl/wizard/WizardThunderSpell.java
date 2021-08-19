package cn.fusionfish.fusionrpg.perks.impl.wizard;

import cn.fusionfish.fusionrpg.players.professions.WizardPlayer;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class WizardThunderSpell extends WizardSpell {

    private final Particle.DustOptions dustOptions = new Particle.DustOptions(Color.WHITE,1.5F);
    private final PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 20, 255, false);

    private final Consumer<LivingEntity> consumer = entity -> {
        World world = entity.getWorld();
        Location location = entity.getLocation();
        world.strikeLightning(location);
        entity.addPotionEffect(effect);
    };

    public WizardThunderSpell() {
        setConsumer(consumer);
        setDustOptions(dustOptions);
        setElement(WizardPlayer.Element.THUNDER);
    }

    @Override
    protected void dead(@NotNull Snowball snowball) {

    }

}
