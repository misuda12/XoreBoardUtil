package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.Sidebar;
import net.minecord.xoreboardutil.SidebarType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class PrivateSidebar implements Sidebar {

    private @NotNull final XorePlayer xorePlayer;

    private @NotNull XoreBoard xoreBoard;
    private String displayName;
    private @NotNull ConcurrentHashMap<String, Integer> lineKeys = new ConcurrentHashMap<String, Integer>();

    private boolean showedStatus = false;

    PrivateSidebar(@NotNull XoreBoard xoreBoard, @NotNull XorePlayer xorePlayer) {
        this.xoreBoard = xoreBoard;
        this.xorePlayer = xorePlayer;

        this.displayName = org.apache.commons.lang3.StringUtils.substring(org.bukkit.ChatColor.translateAlternateColorCodes('&', xoreBoard.getName()), 32);
    }

    @NotNull
    @Override
    public final XoreBoard getXoreBoard() {
        return this.xoreBoard;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public void setDisplayName(@NotNull String displayName) {
        if(getXorePlayer().getPlayer().isOnline() == false || getXorePlayer() == null || getXorePlayer().getPlayer() == null) return;
        @NotNull String tempDisplayName = org.apache.commons.lang3.StringUtils.substring(org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName), 32);
        if(this.displayName.equals(tempDisplayName)) return;
        if(getXorePlayer().hasShowedShared() == false && isShowed()) sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardObjective", getXorePlayer().getID(), tempDisplayName, XoreBoard.XoreBoardPackets.EnumScoreboardHealthDisplay.INTEGER.toNamespace(), 2));

        this.displayName = tempDisplayName;
    }

    @Override
    public void putLine(@NotNull String lineKey, int value) {
        if(getXorePlayer().getPlayer().isOnline() == false || getXorePlayer() == null || getXorePlayer().getPlayer() == null) return;
        if(getXorePlayer().hasShowedShared() == false && isShowed()) sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardScore", org.apache.commons.lang3.StringUtils.substring(org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey), 48), getXorePlayer().getID(), value, XoreBoard.XoreBoardPackets.EnumScoreboardAction.CHANGE.toNamespace()));
        this.lineKeys.put(lineKey, value);
    }

    @Override
    public void setLines(HashMap<String, Integer> lineKeys) {
        if(getXorePlayer().getPlayer().isOnline() == false || getXorePlayer() == null || getXorePlayer().getPlayer() == null) return;
        if(lineKeys == null || lineKeys.isEmpty()) {
            clearLines();
                return;
        }
        this.lineKeys.forEach((lineKey, value) -> {
            if(lineKeys.containsKey(lineKey) && lineKeys.get(lineKey).equals(value)) lineKeys.remove(lineKey);
        });
        if(getXorePlayer().hasShowedShared() == false && isShowed()) lineKeys.forEach((lineKey, value) -> sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardScore", org.apache.commons.lang3.StringUtils.substring(org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey), 48), getXorePlayer().getID(), value, XoreBoard.XoreBoardPackets.EnumScoreboardAction.CHANGE.toNamespace())));
        this.lineKeys.putAll(lineKeys);
    }

    @Override
    public HashMap<String, Integer> getLines() {
        return new HashMap<String, Integer>(this.lineKeys);
    }

    @Override
    public void rewriteLine(@NotNull String lineKey, int value) {
        if(getXorePlayer().getPlayer().isOnline() == false || getXorePlayer() == null || getXorePlayer().getPlayer() == null) return;
        if(this.lineKeys.containsKey(lineKey) == false || this.lineKeys.get(lineKey).equals(value) == false) putLine(lineKey, value);
        else {
            clearLine(lineKey);
                putLine(lineKey, value);
    }}

    @Override
    public void clearLine(@NotNull String lineKey) {
        if(getXorePlayer().getPlayer().isOnline() == false || getXorePlayer() == null || getXorePlayer().getPlayer() == null) return;
        if(this.lineKeys.containsKey(lineKey)) {
            if(getXorePlayer().hasShowedShared() == false && isShowed()) sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardScore", org.apache.commons.lang3.StringUtils.substring(org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey), 48), getXorePlayer().getID(), 0, XoreBoard.XoreBoardPackets.EnumScoreboardAction.REMOVE.toNamespace()));
    }}

    @Override
    public void clearLines() {
        if(getXorePlayer().getPlayer().isOnline() == false || getXorePlayer() == null || getXorePlayer().getPlayer() == null) return;
        this.lineKeys.forEach((lineKey, value) -> clearLine(lineKey));

        this.lineKeys.clear();
    }

    @Override
    public void hideSidebar() {
        if(getXorePlayer().getPlayer().isOnline() == false || getXorePlayer() == null || getXorePlayer().getPlayer() == null) return;
        if(getXorePlayer().hasShowedShared() == false && isShowed()) {
            sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardObjective", getXorePlayer().getID(), null, null, 1));

            this.showedStatus = false;
    }}

    @Override
    public void showSidebar() {
        if(getXorePlayer().getPlayer().isOnline() == false || getXorePlayer() == null || getXorePlayer().getPlayer() == null) return;
        if(getXorePlayer().hasShowedShared()) getXoreBoard().getSharedSidebar().hideSidebar(getXorePlayer());
        if(getXorePlayer().hasShowedShared() == false && isShowed() == false) {
            org.bukkit.Bukkit.getScheduler().runTaskAsynchronously(XoreBoardUtil.getPlugin(XoreBoardUtil.class), () -> {
                sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardObjective", getXorePlayer().getID(), this.displayName, XoreBoard.XoreBoardPackets.EnumScoreboardHealthDisplay.INTEGER.toNamespace(), 0));
                    sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardDisplayObjective", 1, getXorePlayer().getID()));
                        this.lineKeys.forEach((lineKey, value) -> sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardScore", org.apache.commons.lang3.StringUtils.substring(org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey), 48), getXorePlayer().getID(), value, XoreBoard.XoreBoardPackets.EnumScoreboardAction.CHANGE.toNamespace())));
            });

            this.showedStatus = true;
    }}

    @Override
    public boolean isShowed() {
        return getXorePlayer().getPlayer().isOnline() && this.showedStatus;
    }

    @Override
    public SidebarType getType() {
        return SidebarType.PRIVATE;
}}