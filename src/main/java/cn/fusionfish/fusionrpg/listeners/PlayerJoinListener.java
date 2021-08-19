package cn.fusionfish.fusionrpg.listeners;

import cn.fusionfish.fusionrpg.players.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        PlayerManager playerManager = PlayerManager.getInstance();

        playerManager.join(uuid);
        playerManager.reloadPlayer(uuid);

    }
}
