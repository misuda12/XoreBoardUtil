package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;

import net.minecord.xoreboardutil.Sidebar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class XorePlayer {

    private @NotNull final XoreBoard xoreBoard;
    private @NotNull final org.bukkit.entity.Player player;

    private @Nullable Sidebar previousSidebar;
    private PrivateSidebar privateSidebar;

    private boolean sharedSidebar = false;

    XorePlayer(@NotNull XoreBoard xoreBoard, @NotNull org.bukkit.entity.Player player) {
        this.xoreBoard = xoreBoard;
        this.player = player;

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
        return this.sharedSidebar;
    }

    /**
     * public boolean setShowedSharedSidebar(boolean sharedSidebar)
     * @param sharedSidebar boolean {@link Boolean {@value sharedSidebar}}
     * @return boolean
     */

    public boolean setShowedSharedSidebar(boolean sharedSidebar) {
        this.sharedSidebar = sharedSidebar;
        return this.sharedSidebar;
    }

    /**
     * public Sidebar getPreviousSidebar()
     * @return Sidebar
     */

    @Nullable
    public Sidebar getPreviousSidebar() {
        return this.previousSidebar;
    }

    /**
     * public void setPreviousSidebar(@NotNull Sidebar previousSidebar)
     * @param previousSidebar Sidebar {@link Sidebar {@value previousSidebar}}
     */

    public void setPreviousSidebar(@NotNull Sidebar previousSidebar) {
        this.previousSidebar = previousSidebar;
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
        return getXoreBoard().getID() + ":" + getPlayer().getEntityId();
    }

    /**
     * public final org.bukkit.entity.Player getPlayer()
     * @return Player
     */

    @NotNull
    public final org.bukkit.entity.Player getPlayer() {
        return this.player;
}}