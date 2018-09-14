package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.Sidebar;
import net.minecord.xoreboardutil.SidebarType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class SharedSidebar implements Sidebar {

    private @NotNull XoreBoard xoreBoard;
    private String displayName;
    private ConcurrentHashMap<String, Integer> lineKeys = new ConcurrentHashMap<String, Integer>();

    private boolean showedStatus = false;

    public SharedSidebar(@NotNull XoreBoard xoreBoard) {
        this.xoreBoard = xoreBoard;

        this.displayName = org.apache.commons.lang3.StringUtils.substring(org.bukkit.ChatColor.translateAlternateColorCodes('&', xoreBoard.getName()), 0, 32);
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
        @NotNull String tempDisplayName = org.apache.commons.lang3.StringUtils.substring(org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName), 0, 32);
        if(getDisplayName().equals(tempDisplayName)) return;
        getXoreBoard().getXorePlayers().forEach(xorePlayer -> {
            if(xorePlayer.getPlayer().isOnline() && xorePlayer != null && xorePlayer.getPlayer() != null) {
                if(xorePlayer.hasShowedShared() && xorePlayer.getPrivateSidebar().isShowed() == false) sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardObjective", xorePlayer.getID(), tempDisplayName, XoreBoard.XoreBoardPackets.EnumScoreboardHealthDisplay.INTEGER.toNamespace(), 2));
        }});

        this.displayName = tempDisplayName;
    }

    @Override
    public void putLine(@NotNull String lineKey, int value) {
        getXoreBoard().getXorePlayers().forEach(xorePlayer -> {
            if(xorePlayer.getPlayer().isOnline() && xorePlayer != null && xorePlayer.getPlayer() != null) {
                if(xorePlayer.hasShowedShared() && xorePlayer.getPrivateSidebar().isShowed() == false) sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardScore", org.apache.commons.lang3.StringUtils.substring(org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey), 0, 48), xorePlayer.getID(), value, XoreBoard.XoreBoardPackets.EnumScoreboardAction.CHANGE.toNamespace()));
        }});
        this.lineKeys.put(lineKey, value);
    }

    @Override
    public void setLines(HashMap<String, Integer> lineKeys) {
        clearLines();
        getXoreBoard().getXorePlayers().forEach(xorePlayer -> {
            if(xorePlayer.getPlayer().isOnline() && xorePlayer != null && xorePlayer.getPlayer() != null) {
                if(xorePlayer.hasShowedShared() && xorePlayer.getPrivateSidebar().isShowed() == false) lineKeys.forEach((lineKey, value) -> sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardScore", org.apache.commons.lang3.StringUtils.substring(org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey), 0, 48), xorePlayer.getID(), value, XoreBoard.XoreBoardPackets.EnumScoreboardAction.CHANGE.toNamespace())));
        }});
        this.lineKeys.putAll(lineKeys);
    }

    @Override
    public HashMap<String, Integer> getLines() {
        return new HashMap<String, Integer>(this.lineKeys);
    }

    @Override
    public void rewriteLine(@NotNull String lineKey, int value) {
        if(this.lineKeys.containsKey(lineKey) == false || this.lineKeys.get(lineKey).equals(value) == false) putLine(lineKey, value);
        else {
            clearLine(lineKey);
                putLine(lineKey, value);
    }}

    @Override
    public void rewriteLines(HashMap<String, Integer> lineKeys) {
        this.lineKeys.forEach((lineKey, lineValue) -> {
            if(lineKeys.containsKey(lineKey) == false) clearLine(lineKey);
        });

        lineKeys.forEach((lineKey, lineValue) -> {
            if(lineKeys.containsKey(lineKey) == false) putLine(lineKey, lineValue);
        });
    }

    @Override
    public void clearLine(@NotNull String lineKey) {
        if(this.lineKeys.containsKey(lineKey)) {
            getXoreBoard().getXorePlayers().forEach(xorePlayer -> {
                if(xorePlayer.getPlayer().isOnline() && xorePlayer != null && xorePlayer.getPlayer() != null) {
                    if(xorePlayer.hasShowedShared() && xorePlayer.getPrivateSidebar().isShowed() == false) sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardScore", org.apache.commons.lang3.StringUtils.substring(org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey), 0, 48), xorePlayer.getID(), 0, XoreBoard.XoreBoardPackets.EnumScoreboardAction.REMOVE.toNamespace()));
            this.lineKeys.remove(lineKey);
        }});
    }}

    @Override
    public void clearLines() {
        this.lineKeys.clear();
        getXoreBoard().getXorePlayers().forEach(this::hideSidebar);
    }

    @Override
    public void hideSidebar() {
        getXoreBoard().getXorePlayers().forEach(this::hideSidebar);
    }

    /**
     * public void hideSidebar(@NotNull XorePlayer xorePlayer)
     * @param xorePlayer XorePlayer {@link XorePlayer}
     */

    public void hideSidebar(@NotNull XorePlayer xorePlayer) {
        if(xorePlayer.getPlayer().isOnline() == false) return;
        if(xorePlayer.getPrivateSidebar().isShowed() == false && isShowed()) {
            sendPacket(xorePlayer, prepareFormattedVanillaPacket("PacketPlayOutScoreboardObjective", true, xorePlayer.getID(), null, null, 1));
            xorePlayer.setShowedSharedSidebar(false);
    }}

    @Override
    public void showSidebar() {
        getXoreBoard().getXorePlayers().forEach(this::showSidebar);
    }

    /**
     * public void showSidebar(@NotNull XorePlayer xorePlayer)
     * @param xorePlayer XorePlayer {@link XorePlayer}
     */

    public void showSidebar(@NotNull XorePlayer xorePlayer) {
        if(xorePlayer.getPlayer().isOnline() == false) return;
        if(xorePlayer.getPrivateSidebar().isShowed()) xorePlayer.getPrivateSidebar().hideSidebar();
        if(xorePlayer.getPrivateSidebar().isShowed() == false && xorePlayer.hasShowedShared() == false) {
            org.bukkit.Bukkit.getScheduler().runTaskAsynchronously(XoreBoardUtil.getPlugin(XoreBoardUtil.class), () -> {
                sendPacket(xorePlayer, prepareVanillaPacket("prepareFormattedVanillaPacket", xorePlayer.getID(), this.displayName, XoreBoard.XoreBoardPackets.EnumScoreboardHealthDisplay.INTEGER.toNamespace(), 0));
                    sendPacket(xorePlayer, prepareVanillaPacket("prepareFormattedVanillaPacket", 1, xorePlayer.getID()));
                this.lineKeys.forEach((lineKey, value) -> sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardScore", org.apache.commons.lang3.StringUtils.substring(org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey), 0, 48), xorePlayer.getID(), value, XoreBoard.XoreBoardPackets.EnumScoreboardAction.CHANGE.toNamespace())));
            });

            xorePlayer.setShowedSharedSidebar(true);
    }}

    @Override
    public boolean isShowed() {
        return this.showedStatus;
    }

    @Override
    public SidebarType getType() {
        return SidebarType.SHARED;
}}