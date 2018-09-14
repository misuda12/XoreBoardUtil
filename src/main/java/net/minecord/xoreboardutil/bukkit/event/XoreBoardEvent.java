package net.minecord.xoreboardutil.bukkit.event;

import org.jetbrains.annotations.NotNull;

public class XoreBoardEvent extends org.bukkit.event.Event {
    private @NotNull static final org.bukkit.event.HandlerList handlerList = new org.bukkit.event.HandlerList();

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
