package cn.fusionfish.libs.players;

import cn.fusionfish.fusionrpg.FusionRPG;
import cn.fusionfish.libs.utils.FileUtil;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.UUID;

public interface AbstractPlayerFactory {

    FusionPlayer getFusionPlayer(@NotNull UUID uuid);

    File PLAYER_DATA_FOLDER = new File(FusionRPG.getInstance().getDataFolder(), "players");

    default File getPlayerDataFile(@NotNull UUID uuid) {
        File file = new File(PLAYER_DATA_FOLDER, uuid + ".json");
        try {
            if (!file.exists()) {
                //文件不存在
                boolean result = file.createNewFile();
                if (!result) throw new RuntimeException();
                FileUtil.saveJson(file, getDefaultString(uuid));
            }
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    default JsonObject getJson(@NotNull UUID uuid) {
        File file = getPlayerDataFile(uuid);
        return FileUtil.getJson(file);
    }

    String getDefaultString(@NotNull UUID uuid);
}
