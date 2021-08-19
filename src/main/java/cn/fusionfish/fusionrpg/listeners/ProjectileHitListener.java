package cn.fusionfish.fusionrpg.listeners;

import cn.fusionfish.fusionrpg.perks.ProjectileManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.jetbrains.annotations.NotNull;

public class ProjectileHitListener implements Listener {
    @EventHandler
    public void onHit(@NotNull ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        Entity entity = event.getHitEntity();

        if (entity == null) return;
        ProjectileManager.getInstance().hit(projectile, entity);
    }
}
