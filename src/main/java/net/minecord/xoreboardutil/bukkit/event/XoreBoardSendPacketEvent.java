package net.minecord.xoreboardutil.bukkit.event;

import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.XoreBoard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public final class XoreBoardSendPacketEvent extends org.bukkit.event.Event implements org.bukkit.event.Cancellable {

    private @NotNull final XoreBoard xoreBoard;
    private @NotNull final org.bukkit.entity.Player player;
    private Object packetObject;
    private static final org.bukkit.event.HandlerList handlerList = new org.bukkit.event.HandlerList();

    private boolean cancelledStatus = false;

    public XoreBoardSendPacketEvent(@NotNull XoreBoard xoreBoard, @NotNull org.bukkit.entity.Player player, @NotNull Object packetObject) {
        this.xoreBoard = xoreBoard;
        this.player = player;
        this.packetObject = packetObject;
    }

    /**
     * public Object setPacketObject(@Nullable Object packetObject)
     * @param packetObject Object {@link Object {@value packetObject}}
     * @return Object
     */

    public XoreBoardSendPacketEvent setPacketObject(@Nullable Object packetObject) {
        if(packetObject != null) this.packetObject = packetObject;
        else setCancelled(true);
        return this;
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