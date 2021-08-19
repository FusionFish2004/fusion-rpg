package cn.fusionfish.fusionrpg.perks;

import cn.fusionfish.fusionrpg.FusionRPG;
import com.google.common.collect.Maps;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;

import java.util.Map;
import java.util.function.Consumer;

public class ProjectileManager {
    private final Map<Projectile, Consumer<LivingEntity>> consumerMap = Maps.newHashMap();

    public void launch(Projectile projectile, Consumer<LivingEntity> consumer) {
        consumerMap.put(projectile, consumer);
    }

    public void hit(Projectile projectile, Entity entity) {
        if (!consumerMap.containsKey(projectile)) {
            return;
        }

        if (!(entity instanceof LivingEntity)) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) entity;

        Consumer<LivingEntity> consumer = consumerMap.get(projectile);
        consumer.accept(livingEntity);
        consumerMap.remove(projectile);
    }

    public void dead(Projectile projectile) {
        consumerMap.remove(projectile);
    }

    public static ProjectileManager getInstance() {
        return FusionRPG.getProjectileManager();
    }
}
