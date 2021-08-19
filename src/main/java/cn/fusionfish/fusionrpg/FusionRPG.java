package cn.fusionfish.fusionrpg;

import cn.fusionfish.fusionrpg.commands.profiles.ProfileCommand;
import cn.fusionfish.fusionrpg.commands.wizard.WizardCommand;
import cn.fusionfish.fusionrpg.listeners.ItemUseListener;
import cn.fusionfish.fusionrpg.listeners.PlayerJoinListener;
import cn.fusionfish.fusionrpg.listeners.PlayerQuitListener;
import cn.fusionfish.fusionrpg.listeners.ProjectileHitListener;
import cn.fusionfish.fusionrpg.perks.PerkCoolDownManager;
import cn.fusionfish.fusionrpg.perks.ProjectileManager;
import cn.fusionfish.fusionrpg.players.PlayerManager;
import cn.fusionfish.fusionrpg.test.commands.TestParent;
import cn.fusionfish.libs.plugin.FusionPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class FusionRPG extends FusionPlugin {

    private static PlayerManager playerManager;
    private static PerkCoolDownManager perkCoolDownManager;
    private static ProjectileManager projectileManager;

    @Override
    protected void init() {

        registerListeners(
                PlayerJoinListener.class,
                PlayerQuitListener.class,
                ItemUseListener.class,
                ProjectileHitListener.class
        );

        playerManager = new PlayerManager();
        playerManager.loadData();

        perkCoolDownManager = new PerkCoolDownManager();
        projectileManager = new ProjectileManager();

        getCommandManager().registerCommand(new TestParent());
        getCommandManager().registerCommand(new ProfileCommand());
        getCommandManager().registerCommand(new WizardCommand());

        Bukkit.getOnlinePlayers().stream()
                .map(Player::getUniqueId)
                .forEach(playerManager::join);
    }

    public static PlayerManager getPlayerManager() {
        return playerManager;
    }

    public static PerkCoolDownManager getPerkCoolDownManager() {
        return perkCoolDownManager;
    }

    public static ProjectileManager getProjectileManager() {
        return projectileManager;
    }

    @Override
    protected void disable() {

    }
}
