package cn.fusionfish.fusionrpg.perks.impl;

import cn.fusionfish.fusionrpg.perks.PerkException;
import org.bukkit.entity.Player;

public interface Perk {
    void execute(Player player) throws PerkException;
}
