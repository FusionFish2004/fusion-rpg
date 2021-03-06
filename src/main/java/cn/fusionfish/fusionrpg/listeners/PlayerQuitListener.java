package cn.fusionfish.fusionrpg.listeners;

import cn.fusionfish.fusionrpg.players.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        PlayerManager.getInstance().quit(uuid);
    }
}
