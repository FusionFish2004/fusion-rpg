package cn.fusionfish.fusionrpg.players;

import cn.fusionfish.fusionrpg.players.professions.NoobPlayer;
import cn.fusionfish.libs.players.AbstractPlayerFactory;
import cn.fusionfish.libs.utils.FileUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.UUID;

public class PlayerFactory implements AbstractPlayerFactory {

    private static final String PACKAGE_NAME = "cn.fusionfish.fusionrpg.players.professions.";

    @Override
    @Nullable
    public FusionRPGPlayer getFusionPlayer(@NotNull UUID uuid) {
        return getPlayer(getPlayerDataFile(uuid));
    }

    @Override
    public String getDefaultString(@NotNull UUID uuid) {
        NoobPlayer noobPlayer = new NoobPlayer();
        String name = Bukkit.getOfflinePlayer(uuid).getName();
        assert name != null;
        noobPlayer.setName(name);
        noobPlayer.setUniqueId(uuid);
        return new Gson().toJson(noobPlayer);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static FusionRPGPlayer getPlayer(File file) {
        JsonObject jsonObject = FileUtil.getJson(file);
        assert jsonObject != null;
        String profession = jsonObject.get("profession").getAsString();
        try {
            String className = profession + "Player";
            //使用反射获得对应的玩家类
            Class<? extends FusionRPGPlayer> clazz = (Class<? extends FusionRPGPlayer>) Class.forName(PACKAGE_NAME + className);
            //解析Json得到玩家
            return new Gson().fromJson(jsonObject, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
