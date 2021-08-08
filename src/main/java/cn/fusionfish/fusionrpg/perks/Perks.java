package cn.fusionfish.fusionrpg.perks;

import cn.fusionfish.fusionrpg.FusionRPG;
import cn.fusionfish.fusionrpg.perks.impl.AssassinSprint;
import cn.fusionfish.fusionrpg.perks.impl.Perk;
import cn.fusionfish.fusionrpg.perks.impl.WarriorDefend;
import cn.fusionfish.fusionrpg.perks.impl.WarriorSmash;
import cn.fusionfish.fusionrpg.players.FusionRPGPlayer;
import cn.fusionfish.fusionrpg.players.PlayerManager;
import cn.fusionfish.fusionrpg.players.Profession;
import cn.fusionfish.libs.utils.MessageUtil;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum Perks {
    //空技能
    NULL(player -> {}, 0, Profession.All),

    //新手技能
    NOOB(player -> player.chat("im a noob"), 10, Profession.Noob),

    //战士
    WARRIOR_SMASH(new WarriorSmash(), 100, Profession.Warrior),
    WARRIOR_DEFEND(new WarriorDefend(), 100, Profession.Warrior),

    //刺客
    ASSASSIN_SPRINT(new AssassinSprint(), 100, Profession.Assassin);

    private final Perk perksImpl;
    private final List<Profession> profession;
    private final int cd;

    Perks(Perk perkImpl, int cd, Profession... profession) {
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
}
