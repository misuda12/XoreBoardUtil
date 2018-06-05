package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;

import org.jetbrains.annotations.NotNull;

@Getter
public class XorePlayer {

    private @NotNull final XoreBoard xoreBoard;
    private @NotNull final org.bukkit.entity.Player player;

    private XoreBoardPrivateSidebar privateSidebar;

    private boolean sharedSidebar = false;

    XorePlayer(@NotNull XoreBoard xoreBoard, @NotNull org.bukkit.entity.Player player) {
        this.xoreBoard = xoreBoard;
        this.player = player;
    }

    /**
     * public XoreBoardPrivateSidebar getPrivateSidebar()
     * @return XoreBoardPrivateSidebar
     */

    public XoreBoardPrivateSidebar getPrivateSidebar() {
        return this.privateSidebar;
    }

    /**
     * public void setPrivateSidebar(@NotNull XoreBoardPrivateSidebar privateSidebar)
     * @param privateSidebar XoreBoardPrivateSidebar {@link XoreBoardPrivateSidebar {@value privateSidebar}}
     */

    public void setPrivateSidebar(@NotNull XoreBoardPrivateSidebar privateSidebar) {
        this.privateSidebar = privateSidebar;
    }

    /**
     * public boolean hasSharedSidebar()
     * @return boolean
     */

    public boolean hasSharedSidebar() {
        return this.sharedSidebar;
    }

    /**
     * public XoreBoard getXoreBoard()
     * @return XoreBoard
     */

    @NotNull
    public XoreBoard getXoreBoard() {
        return this.xoreBoard;
    }

    /**
     * public final org.bukkit.entity.Player getPlayer()
     * @return Player
     */

    @NotNull
    public final org.bukkit.entity.Player getPlayer() {
        return this.player;
}}