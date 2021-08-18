package cn.fusionfish.fusionrpg.commands.profiles;

import cn.fusionfish.fusionrpg.players.FusionRPGPlayer;
import cn.fusionfish.fusionrpg.players.PlayerManager;
import cn.fusionfish.libs.command.SimpleCommand;
import org.bukkit.entity.Player;

public class ProfileCommand extends SimpleCommand {

    public ProfileCommand() {
        super("profile", "pf");

        new SetProfessionCommand(this);
        new SetPerkCommand(this);

    }

    @Override
    public void onCommand() {
        if (args.length == 0 && sender instanceof Player) {
            Player player = (Player) sender;
            FusionRPGPlayer fusionRPGPlayer = PlayerManager.getInstance().getPlayer(player);

            fusionRPGPlayer.getInfo().forEach(sender::sendMessage);
            return;
        }
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException();
            }

            String name = args[0];
            FusionRPGPlayer fusionRPGPlayer = PlayerManager.getInstance().getPlayer(name);
            fusionRPGPlayer.getInfo().forEach(sender::sendMessage);

        } catch (IllegalArgumentException e) {
            sendMsg("参数错误！");
        }
    }
}
