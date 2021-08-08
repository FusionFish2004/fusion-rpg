package cn.fusionfish.fusionrpg.players.professions;

import cn.fusionfish.fusionrpg.perks.Perks;
import cn.fusionfish.fusionrpg.players.FusionRPGPlayer;
import cn.fusionfish.fusionrpg.players.Profession;

public class NoobPlayer extends FusionRPGPlayer {

    public NoobPlayer() {
        setProfession(Profession.Noob);
        setLvl(0);
        setPerk(Perks.NOOB);
    }

}
