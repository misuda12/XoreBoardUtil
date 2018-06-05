package net.minecord.xoreboardutil.bukkit.event;

import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.XoreBoard;
import org.jetbrains.annotations.NotNull;

@Getter
public final class XoreBoardCreateEvent extends org.bukkit.event.Event implements org.bukkit.event.Cancellable {

    private @NotNull final XoreBoard xoreBoard;
    private boolean cancelledStatus = false;

    private static final org.bukkit.event.HandlerList handlerList = new org.bukkit.event.HandlerList();

    public XoreBoardCreateEvent(@NotNull XoreBoard xoreBoard) {
        this.xoreBoard = xoreBoard;
    }

    /**
     * public final XoreBoard getXoreBoard()
     * @return XoreBoard
     */

    @NotNull
    public final XoreBoard getXoreBoard() {
        return this.xoreBoard;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelledStatus;
    }

    @Override
    public void setCancelled(boolean cancelledStatus) {
        this.cancelledStatus = cancelledStatus;
    }

    @Override
    public org.bukkit.event.HandlerList getHandlers() {
        return handlerList;
    }

    /**
     * public static org.bukkit.event.HandlerList getHandlerList()
     * @return org.bukkit.event.HandlerList
     */

    public static org.bukkit.event.HandlerList getHandlerList() {
        return handlerList;
}}