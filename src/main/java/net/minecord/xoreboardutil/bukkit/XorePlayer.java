package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;

import org.jetbrains.annotations.NotNull;

@Getter
public class XorePlayer {

    private @NotNull final XoreBoard xoreBoard;
    private @NotNull final org.bukkit.entity.Player player;

    private PrivateSidebar privateSidebar;

    private boolean sharedSidebar = false;

    XorePlayer(@NotNull XoreBoard xoreBoard, @NotNull org.bukkit.entity.Player player) {
        this.xoreBoard = xoreBoard;
        this.player = player;

        if(this.privateSidebar == null) this.privateSidebar = new PrivateSidebar(getXoreBoard(), this);
    }

    /**
     * public PrivateSidebar getPrivateSidebar()
     * @return PrivateSidebar
     */

    @NotNull
    public PrivateSidebar getPrivateSidebar() {
        return this.privateSidebar;
    }

    /**
     * public void setPrivateSidebar(@NotNull PrivateSidebar privateSidebar)
     * @param privateSidebar PrivateSidebar {@link PrivateSidebar {@value privateSidebar}}
     */

    public void setPrivateSidebar(@NotNull PrivateSidebar privateSidebar) {
        this.privateSidebar = privateSidebar;
    }

    /**
     * public boolean isShowedShared()
     * @return boolean
     */

    public boolean isShowedShared() {
        return this.sharedSidebar;
    }

    /**
     * public final XoreBoard getXoreBoard()
     * @return XoreBoard
     */

    @NotNull
    public final XoreBoard getXoreBoard() {
        return this.xoreBoard;
    }

    /**
     * public public String getID()
     * @return int
     */

    @NotNull
    @org.jetbrains.annotations.Contract(pure = true)
    public String getID() {
        return getXoreBoard().getID() + "" + getPlayer().getEntityId();
    }

    /**
     * public final org.bukkit.entity.Player getPlayer()
     * @return Player
     */

    @NotNull
    public final org.bukkit.entity.Player getPlayer() {
        return this.player;
}}