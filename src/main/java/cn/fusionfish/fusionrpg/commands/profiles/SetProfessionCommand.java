package cn.fusionfish.fusionrpg.commands.profiles;

import cn.fusionfish.fusionrpg.players.FusionRPGPlayer;
import cn.fusionfish.fusionrpg.players.PlayerManager;
import cn.fusionfish.fusionrpg.players.Profession;
import cn.fusionfish.libs.command.SimpleCommand;

public class SetProfessionCommand extends SimpleCommand {

    protected SetProfessionCommand(SimpleCommand parent) {
        super(parent, "profession");
        setAdminCommand();
    }

    @Override
    public void onCommand() {
        try {
            if (args.length != 3) {
                throw new IllegalArgumentException();
            }

            String name = args[1];
            String professionName = args[2];

            FusionRPGPlayer player = PlayerManager.getInstance().getPlayer(name);
            Profession profession = Profession.valueOf(professionName);

            player.setProfession(profession);

            player.save();
            sendMsg("§a设置成功！");


        } catch (IllegalArgumentException e) {
            sendMsg("参数错误！");
        }
    }
}
