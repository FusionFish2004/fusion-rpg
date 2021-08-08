package cn.fusionfish.fusionrpg;

import cn.fusionfish.fusionrpg.listeners.ItemUseListener;
import cn.fusionfish.fusionrpg.listeners.PlayerJoinListener;
import cn.fusionfish.fusionrpg.listeners.PlayerQuitListener;
import cn.fusionfish.fusionrpg.perks.PerkCoolDownManager;
import cn.fusionfish.fusionrpg.players.PlayerManager;
import cn.fusionfish.fusionrpg.test.commands.TestParent;
import cn.fusionfish.libs.plugin.FusionPlugin;


public class FusionRPG extends FusionPlugin {

    private static PlayerManager playerManager;
    private static PerkCoolDownManager perkCoolDownManager;

    @Override
    protected void init() {

        registerListeners(
                PlayerJoinListener.class,
                PlayerQuitListener.class,
                ItemUseListener.class
        );

        playerManager = new PlayerManager();
        playerManager.loadData();

        perkCoolDownManager = new PerkCoolDownManager();

        getCommandManager().registerCommand(new TestParent());
    }

    public static PlayerManager getPlayerManager() {
        return playerManager;
    }

    public static PerkCoolDownManager getPerkCoolDownManager() {
        return perkCoolDownManager;
    }

    @Override
    protected void disable() {

    }
}
