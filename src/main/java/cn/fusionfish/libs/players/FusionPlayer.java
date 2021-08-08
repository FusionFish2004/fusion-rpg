package cn.fusionfish.libs.players;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class FusionPlayer {

    @SerializedName("name")
    private String name;

    @SerializedName("uniqueId")
    private UUID uniqueId;

    protected FusionPlayer() {

    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull UUID getUniqueId() {
        return uniqueId;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setUniqueId(@NotNull UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Nullable
    public Player getPlayer() {
        return Bukkit.getPlayer(uniqueId);
    }

    public boolean isOnline() {
        return Bukkit.getPlayer(uniqueId) != null;
    }

}
