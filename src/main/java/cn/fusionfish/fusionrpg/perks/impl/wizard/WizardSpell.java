package cn.fusionfish.fusionrpg.perks.impl.wizard;

import cn.fusionfish.fusionrpg.FusionRPG;
import cn.fusionfish.fusionrpg.perks.PerkException;
import cn.fusionfish.fusionrpg.perks.ProjectileManager;
import cn.fusionfish.fusionrpg.perks.impl.Perk;
import cn.fusionfish.fusionrpg.players.FusionRPGPlayer;
import cn.fusionfish.fusionrpg.players.PlayerManager;
import cn.fusionfish.fusionrpg.players.professions.WizardPlayer;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class WizardSpell implements Perk {

    private Consumer<LivingEntity> consumer;
    private Particle.DustOptions dustOptions;
    private WizardPlayer.Element element;

    @Override
    public final void execute(@NotNull Player player) throws PerkException {

        WizardPlayer wizardPlayer = (WizardPlayer) PlayerManager.getInstance().getPlayer(player);

        if (wizardPlayer.getElement() != element) {
            throw new PerkException();
        }

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
                    dead(snowball);
                }

                world.spawnParticle(Particle.REDSTONE, location, 3, 0.1,0.1,0.1,0.001,dustOptions);
            }
        };
        runnable.runTaskTimer(FusionRPG.getInstance(), 0L, 1L);

    }

    protected abstract void dead(Snowball snowball);

    public void setConsumer(Consumer<LivingEntity> consumer) {
        this.consumer = consumer;
    }

    public void setDustOptions(Particle.DustOptions dustOptions) {
        this.dustOptions = dustOptions;
    }

    public void setElement(WizardPlayer.Element element) {
        this.element = element;
    }
}
