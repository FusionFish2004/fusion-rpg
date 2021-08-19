package cn.fusionfish.fusionrpg.players.professions;

import cn.fusionfish.fusionrpg.perks.Perks;
import cn.fusionfish.fusionrpg.players.FusionRPGPlayer;
import cn.fusionfish.fusionrpg.players.Profession;

public class WarriorPlayer extends FusionRPGPlayer {
    public WarriorPlayer() {
        setProfession(Profession.Warrior);
        setPerk(Perks.WARRIOR_SMASH);
    }
}
