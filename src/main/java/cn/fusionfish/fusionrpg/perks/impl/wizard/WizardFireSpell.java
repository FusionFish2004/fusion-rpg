package cn.fusionfish.fusionrpg.perks.impl.wizard;

import cn.fusionfish.fusionrpg.players.professions.WizardPlayer;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class WizardFireSpell extends WizardSpell{

    private final Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255,98,38),1.5F);

    private final Consumer<LivingEntity> consumer = entity -> entity.setFireTicks(200);

    public WizardFireSpell() {
        setConsumer(consumer);
        setDustOptions(dustOptions);
        setElement(WizardPlayer.Element.FIRE);
    }

    @Override
    protected void dead(@NotNull Snowball snowball) {
        World world = snowball.getWorld();
        Location location = snowball.getLocation();
        world.playSound(location, Sound.ITEM_FIRECHARGE_USE, 1f, 1f);
    }

}