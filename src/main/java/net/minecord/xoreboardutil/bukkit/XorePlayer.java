package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;

import net.minecord.xoreboardutil.bukkit.event.XoreBoardPlayerCreateEvent;
import org.jetbrains.annotations.NotNull;

@Getter
public class XorePlayer {

    private @NotNull final XoreBoard xoreBoard;
    private @NotNull final org.bukkit.entity.Player player;
    private @NotNull final String ID;

    private @NotNull PrivateSidebar privateSidebar;

    private boolean showedSharedSidebar = false;

    XorePlayer(@NotNull XoreBoard xoreBoard, @NotNull org.bukkit.entity.Player player, int ID) {
        this.xoreBoard = xoreBoard;
        this.player = player;
        this.ID = getXoreBoard().getID() + "@" + ID;

        @NotNull final XoreBoardPlayerCreateEvent event = new XoreBoardPlayerCreateEvent(getXoreBoard(), this);
        XoreBoardUtil.getPlugin(XoreBoardUtil.class).getServer().getPluginManager().callEvent(event);
        this.privateSidebar = new PrivateSidebar(getXoreBoard(), this);
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
     * public boolean hasShowedShared()
     * @return boolean
     */

    public boolean hasShowedShared() {
        return this.showedSharedSidebar;
    }

    /**
     * public boolean setShowedSharedSidebar(boolean sharedSidebar)
     * @param sharedSidebar boolean {@link Boolean {@value showedSharedSidebar}}
     * @return boolean
     */

    public boolean setShowedSharedSidebar(boolean sharedSidebar) {
        this.showedSharedSidebar = sharedSidebar;
        if(sharedSidebar) getXoreBoard().getSharedSidebar().showSidebar(this);
            else getXoreBoard().getSharedSidebar().hideSidebar(this);
        return this.showedSharedSidebar;
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
        return this.ID;
    }

    /**
     * public final org.bukkit.entity.Player getPlayer()
     * @return Player
     */

    @NotNull
    public final org.bukkit.entity.Player getPlayer() {
        return this.player;
}}