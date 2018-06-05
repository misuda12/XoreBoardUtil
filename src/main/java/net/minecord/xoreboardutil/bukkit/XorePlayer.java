package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class XorePlayer {

    private @NotNull final org.bukkit.entity.Player player;

    XorePlayer(@NotNull org.bukkit.entity.Player player) {
        this.player = player;
    }

    /**
     * public final org.bukkit.entity.Player getPlayer()
     * @return Player
     */

    @NotNull
    public final org.bukkit.entity.Player getPlayer() {
        return this.player;
}}