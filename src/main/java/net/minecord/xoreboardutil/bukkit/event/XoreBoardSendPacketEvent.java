package net.minecord.xoreboardutil.bukkit.event;

import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.XoreBoard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public final class XoreBoardSendPacketEvent extends org.bukkit.event.Event implements org.bukkit.event.Cancellable {

    private @NotNull final XoreBoard xoreBoard;
    private @NotNull final org.bukkit.entity.Player player;
    private Object packet;
    private static final org.bukkit.event.HandlerList handlerList = new org.bukkit.event.HandlerList();

    private boolean cancelledStatus = false;

    public XoreBoardSendPacketEvent(@NotNull XoreBoard xoreBoard, @NotNull org.bukkit.entity.Player player, @NotNull Object packet) {
        this.xoreBoard = xoreBoard;
        this.player = player;
        this.packet = packet;
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
     * public final org.bukkit.entity.Player getPlayer()
     * @return org.bukkit.entity.Player
     */

    @NotNull
    public final org.bukkit.entity.Player getPlayer() {
        return this.player;
    }

    /**
     * public Object getPacketObject()
     * @return Object
     */

    public Object getPacketObject() {
        return this.packet;
    }

    /**
     * public Object setPacketObject(@Nullable Object object)
     * @param object Object {@link Object {@value packet}}
     * @return Object
     */

    public Object setPacketObject(@Nullable Object object) {
        this.packet = object;
        return this.packet;
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