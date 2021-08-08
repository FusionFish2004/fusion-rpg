package cn.fusionfish.fusionrpg.listeners;

import cn.fusionfish.fusionrpg.players.FusionRPGPlayer;
import cn.fusionfish.fusionrpg.players.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Date;

public class ItemUseListener implements Listener {
    @EventHandler
    public void onUse(PlayerInteractEvent event) {

        if (event.getAction() == Action.PHYSICAL) {
            return;
        }

        Player player = event.getPlayer();
        FusionRPGPlayer fusionRPGPlayer = PlayerManager.getInstance().getPlayer(player);

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            long lastInteract = fusionRPGPlayer.getLastInteract();
            long now = new Date().getTime();
            if (now - lastInteract <= 200) {
                //TODO 释放技能
                fusionRPGPlayer.getPerk().execute(player);
                event.setCancelled(true);
            }
            fusionRPGPlayer.setLastInteract(now);
        }
    }
}
