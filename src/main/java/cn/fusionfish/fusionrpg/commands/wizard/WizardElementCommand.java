package cn.fusionfish.fusionrpg.commands.wizard;

import cn.fusionfish.fusionrpg.players.FusionRPGPlayer;
import cn.fusionfish.fusionrpg.players.PlayerManager;
import cn.fusionfish.fusionrpg.players.Profession;
import cn.fusionfish.fusionrpg.players.professions.WizardPlayer;
import cn.fusionfish.libs.command.SimpleCommand;

public class WizardElementCommand extends SimpleCommand {

    protected WizardElementCommand(SimpleCommand parent) {
        super(parent, "element");
    }

    @Override
    public void onCommand() {
        try {
            if (args.length != 3) {
                throw new IllegalArgumentException();
            }

            String name = args[1];
            String element = args[2];

            FusionRPGPlayer player = PlayerManager.getInstance().getPlayer(name);

            if (player.getProfession() != Profession.Wizard) {
                sendMsg("玩家不是法师职业！");
                return;
            }

            WizardPlayer.Element e = WizardPlayer.Element.valueOf(element);
            WizardPlayer wizardPlayer = (WizardPlayer) player;

            wizardPlayer.setElement(e);
            wizardPlayer.save();

            PlayerManager.getInstance().reloadPlayer(player.getUniqueId());

            sendMsg("§a设置成功！");


        } catch (IllegalArgumentException e) {
            sendMsg("参数错误！");
        }
    }
}
