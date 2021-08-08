package cn.fusionfish.fusionrpg.perks;

import cn.fusionfish.fusionrpg.FusionRPG;
import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class PerkCoolDownManager {
    private final Map<UUID, Integer> coolDownMap = Maps.newHashMap();

    public PerkCoolDownManager() {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                coolDownMap.forEach((uuid, cd) -> {
                    if (cd == 0) return;
                    coolDownMap.merge(uuid, -1, Integer::sum);
                });
            }
        };
        runnable.runTaskTimer(FusionRPG.getInstance(), 0, 1);
    }

    public int getCoolDown(UUID uuid) {
        coolDownMap.putIfAbsent(uuid, 0);
        return coolDownMap.get(uuid);
    }

    public void setCoolDown(UUID uuid, int cd) {
        coolDownMap.put(uuid, cd);
    }

    public boolean isInCoolDown(UUID uuid) {
        return getCoolDown(uuid) != 0;
    }

    public boolean isInCoolDown(@NotNull Player player) {
        return isInCoolDown(player.getUniqueId());
    }

    public void setCoolDown(@NotNull Player player, int cd) {
        this.setCoolDown(player.getUniqueId(), cd);
    }
}
