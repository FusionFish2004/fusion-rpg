package cn.fusionfish.fusionrpg.perks;

import cn.fusionfish.fusionrpg.FusionRPG;
import cn.fusionfish.fusionrpg.perks.impl.*;
import cn.fusionfish.fusionrpg.players.FusionRPGPlayer;
import cn.fusionfish.fusionrpg.players.PlayerManager;
import cn.fusionfish.fusionrpg.players.Profession;
import cn.fusionfish.libs.utils.MessageUtil;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum Perks {
    //空技能
    NULL("空", player -> {}, 0, Profession.All),

    //新手技能
    NOOB("新手技能",player -> player.chat("im a noob"), 10, Profession.Noob),

    //战士
    WARRIOR_SMASH("空",new WarriorSmash(), 100, Profession.Warrior),
    WARRIOR_DEFEND("空",new WarriorDefend(), 100, Profession.Warrior),

    //刺客
    ASSASSIN_SPRINT("空",new AssassinSprint(), 100, Profession.Assassin),

    //法师
    WIZARD_FIRE_SPELL("火焰法术", new WizardFireSpell(), 10, Profession.Wizard),
    WIZARD_ICE_SPELL("冰冻法术", new WizardIceSpell(), 10, Profession.Wizard);

    private final Perk perksImpl;
    private final List<Profession> profession;
    private final int cd;
    private final String name;

    Perks(String name, Perk perkImpl, int cd, Profession... profession) {
        this.name = name;
        this.perksImpl = perkImpl;
        this.cd = cd;
        this.profession = Arrays.asList(profession);
    }

    public void execute(Player player) {
        FusionRPGPlayer fusionRPGPlayer = PlayerManager.getInstance().getPlayer(player);
        Profession profession = fusionRPGPlayer.getProfession();
        PerkCoolDownManager perkCoolDownManager = FusionRPG.getPerkCoolDownManager();

        //如果玩家正在CD中
        if (perkCoolDownManager.isInCoolDown(player)) {
            //TODO 发送在CD中消息
            return;
        }

        //如果玩家符合职业要求

        if (this.profession.contains(Profession.All) || this.profession.contains(profession)) {
            try {
                perksImpl.execute(player);
            } catch (PerkException exception) {
                //释放技能失败
                MessageUtil.warn("玩家" + player.getName() + "释放技能" + this.name() + "失败！");
                return;
            }
            perkCoolDownManager.setCoolDown(player, cd);
        }
    }

    public String getName() {
        return name;
    }
}
