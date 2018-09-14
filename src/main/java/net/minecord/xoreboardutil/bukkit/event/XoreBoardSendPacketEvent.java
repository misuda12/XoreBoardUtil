package net.minecord.xoreboardutil.bukkit.event;

import lombok.Getter;
import lombok.Setter;
import net.minecord.xoreboardutil.bukkit.XoreBoard;
import org.jetbrains.annotations.NotNull;

@Getter
public final class XoreBoardSendPacketEvent extends XoreBoardEvent implements org.bukkit.event.Cancellable {

    private @NotNull final XoreBoard xoreBoard;
    private @NotNull final org.bukkit.entity.Player player;

    @Setter
    private Object packetObject;

    private boolean cancelledStatus = false;

    public XoreBoardSendPacketEvent(@NotNull XoreBoard xoreBoard, @NotNull org.bukkit.entity.Player player, @NotNull Object packetObject) {
        this.xoreBoard = xoreBoard;
        this.player = player;
        this.packetObject = packetObject;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelledStatus;
    }

    @Override
    public void setCancelled(boolean cancelledStatus) {
        this.cancelledStatus = cancelledStatus;
}}