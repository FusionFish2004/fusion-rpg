package cn.fusionfish.fusionrpg.players;

import static cn.fusionfish.libs.utils.MessageUtil.*;

import cn.fusionfish.fusionrpg.FusionRPG;
import cn.fusionfish.libs.players.AbstractPlayerFactory;
import cn.fusionfish.libs.utils.FileUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static cn.fusionfish.libs.players.AbstractPlayerFactory.PLAYER_DATA_FOLDER;

public class PlayerManager {
    private final Map<UUID, FusionRPGPlayer> playerMap = Maps.newHashMap();
    private final Map<UUID, FusionRPGPlayer> onlineMap = Maps.newHashMap();
    private final PlayerFactory factory = new PlayerFactory();

    public static PlayerManager getInstance() {
        return FusionRPG.getPlayerManager();
    }

    public void loadData() {
        log("加载玩家数据...");
        Objects.requireNonNull(FileUtil.getFiles(PLAYER_DATA_FOLDER))
                .stream()
                .map(PlayerFactory::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player -> {
                    playerMap.put(player.getUniqueId(), player);
                    log(" >>> " + player.getName() + " - " + player.getProfession());
                });
    }

    public void join(UUID uuid) {
        if (!playerMap.containsKey(uuid)) {
            playerMap.put(uuid,factory.getFusionPlayer(uuid));
        }

        FusionRPGPlayer player = playerMap.get(uuid);
        onlineMap.put(uuid, player);
    }

    public void quit(UUID uuid) {
        onlineMap.remove(uuid);
    }

    public Set<FusionRPGPlayer> getOnlinePlayers() {
        return Sets.newHashSet(onlineMap.values());
    }

    public Set<FusionRPGPlayer> getAllPlayers() {
        return Sets.newHashSet(playerMap.values());
    }

    public Set<FusionRPGPlayer> getByProfession(Profession profession) {
        return getAllPlayers()
                .stream()
                .filter(player -> player.getProfession() == profession)
                .collect(Collectors.toSet());
    }

    @Nullable
    public FusionRPGPlayer getPlayer(@NotNull UUID uuid) {
        return playerMap.get(uuid);
    }

    @NotNull
    public FusionRPGPlayer getPlayer(@NotNull Player player) {
        return Objects.requireNonNull(getPlayer(player.getUniqueId()));
    }

    public void reloadPlayer(@NotNull UUID uuid) {
        final File file = new File(AbstractPlayerFactory.PLAYER_DATA_FOLDER, uuid + ".json");

        FusionRPGPlayer player = getPlayer(uuid);
        if (player == null) return;

        log("正在更新" + player.getName() + "的玩家数据...");
        player.save();

        playerMap.put(uuid, PlayerFactory.getPlayer(file));
        join(uuid);

    }
}
