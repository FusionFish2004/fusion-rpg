package cn.fusionfish.fusionrpg.commands.profiles;

import cn.fusionfish.fusionrpg.perks.Perks;
import cn.fusionfish.fusionrpg.players.FusionRPGPlayer;
import cn.fusionfish.fusionrpg.players.PlayerManager;
import cn.fusionfish.libs.command.SimpleCommand;

public class SetPerkCommand extends SimpleCommand {
    protected SetPerkCommand(SimpleCommand parent) {
        super(parent, "perk");
        setAdminCommand();
    }

    @Override
    public void onCommand() {
        try {
            if (args.length != 3) {
                throw new IllegalArgumentException();
            }

            String name = args[1];
            String perkName = args[2];

            FusionRPGPlayer player = PlayerManager.getInstance().getPlayer(name);
            Perks perks = Perks.valueOf(perkName);

            player.setPerk(perks);

            player.save();
            sendMsg("§a设置成功！");


        } catch (IllegalArgumentException e) {
            sendMsg("参数错误！");
        }
    }
}
