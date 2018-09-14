package net.minecord.xoreboardutil.bukkit.event;

import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.XoreBoard;
import net.minecord.xoreboardutil.bukkit.XorePlayer;
import org.jetbrains.annotations.NotNull;

@Getter
public final class XoreBoardPlayerRemoveEvent extends XoreBoardEvent {

    private @NotNull final XoreBoard xoreBoard;
    private @NotNull final XorePlayer xorePlayer;

    public XoreBoardPlayerRemoveEvent(@NotNull XoreBoard xoreBoard, @NotNull XorePlayer xorePlayer) {
        this.xoreBoard = xoreBoard;
        this.xorePlayer = xorePlayer;

        this.xorePlayer.getPrivateSidebar().clearLines();
}}