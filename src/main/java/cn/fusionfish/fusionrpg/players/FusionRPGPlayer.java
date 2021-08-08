package cn.fusionfish.fusionrpg.players;

import cn.fusionfish.fusionrpg.perks.Perks;
import cn.fusionfish.libs.players.AbstractPlayerFactory;
import cn.fusionfish.libs.utils.FileUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static cn.fusionfish.libs.utils.MessageUtil.log;

public abstract class FusionRPGPlayer extends cn.fusionfish.libs.players.FusionPlayer {

    @SerializedName("profession")
    private Profession profession;

    @SerializedName("lvl")
    private int lvl;

    @SerializedName("perk")
    private Perks perks = Perks.NULL;

    private transient long lastInteract;

    public long getLastInteract() {
        return lastInteract;
    }

    public void setLastInteract(long lastInteract) {
        this.lastInteract = lastInteract;
    }

    public Perks getPerk() {
        return perks;
    }

    public void setPerk(Perks perks) {
        this.perks = perks;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
        PlayerManager.getInstance().reloadPlayer(getUniqueId());
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void save() {
        final File file = new File(AbstractPlayerFactory.PLAYER_DATA_FOLDER, getUniqueId() + ".json");
        String json = new Gson().toJson(this);
        log("正在保存" + file.getName());
        FileUtil.saveJson(file, json);
    }

    public List<String> getInfo() {
        final File file = new File(AbstractPlayerFactory.PLAYER_DATA_FOLDER, getUniqueId() + ".json");
        JsonObject json = FileUtil.getJson(file);
        assert json != null;
        return json.entrySet()
                .stream()
                .map(entry -> entry.getKey() + " >>> " + entry.getValue())
                .collect(Collectors.toList());
    }

}
