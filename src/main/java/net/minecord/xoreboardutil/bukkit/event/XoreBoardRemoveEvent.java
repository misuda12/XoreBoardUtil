package net.minecord.xoreboardutil.bukkit.event;

import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.XoreBoard;
import org.jetbrains.annotations.NotNull;

@Getter
public final class XoreBoardRemoveEvent extends org.bukkit.event.Event {

    private @NotNull final XoreBoard xoreBoard;
    private static final org.bukkit.event.HandlerList handlerList = new org.bukkit.event.HandlerList();

    public XoreBoardRemoveEvent(@NotNull XoreBoard xoreBoard) {
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