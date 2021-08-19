package cn.fusionfish.fusionrpg.commands.wizard;

import cn.fusionfish.libs.command.SimpleCommand;

public class WizardCommand extends SimpleCommand {

    public WizardCommand() {
        super("wizard", "wz");
        new WizardElementCommand(this);
        setPlayerOnly();
    }

    @Override
    public void onCommand() {

    }
}
