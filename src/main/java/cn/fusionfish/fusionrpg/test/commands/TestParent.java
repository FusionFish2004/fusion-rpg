package cn.fusionfish.fusionrpg.test.commands;

import cn.fusionfish.fusionrpg.perks.Perks;
import cn.fusionfish.fusionrpg.players.FusionRPGPlayer;
import cn.fusionfish.fusionrpg.players.PlayerManager;
import cn.fusionfish.fusionrpg.players.Profession;
import cn.fusionfish.libs.command.SimpleCommand;
import org.bukkit.entity.Player;

public class TestParent extends SimpleCommand {

    public TestParent() {
        super("test");
        setPlayerOnly();
        setAdminCommand();
    }

    @Override
    public void onCommand() {

        FusionRPGPlayer player = PlayerManager.getInstance().getPlayer(((Player) sender).getUniqueId());
        assert player != null;

        player.setProfession(Profession.Warrior);
        player.setPerk(Perks.WARRIOR_DEFEND);
        player.save();

        player.getInfo().forEach(sender::sendMessage);


    }
}
