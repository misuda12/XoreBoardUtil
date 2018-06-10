package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.Sidebar;
import net.minecord.xoreboardutil.SidebarType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class XoreBoardSharedSidebar implements Sidebar {

    private @NotNull XoreBoard xoreBoard;
    private String displayName;
    private ConcurrentHashMap<String, Integer> lineKeys = new ConcurrentHashMap<String, Integer>();

    private boolean showedStatus = false;

    public XoreBoardSharedSidebar(@NotNull XoreBoard xoreBoard) {
        this.xoreBoard = xoreBoard;

        this.displayName = (org.bukkit.ChatColor.translateAlternateColorCodes('&', xoreBoard.getName())).length() > 32 ? (org.bukkit.ChatColor.translateAlternateColorCodes('&', xoreBoard.getName())).substring(0, 32) : (org.bukkit.ChatColor.translateAlternateColorCodes('&', xoreBoard.getName()));
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
        String tempDisplayName = (org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName)).length() > 32 ? (org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName)).substring(0, 32) : (org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName));
        if(getDisplayName().equals(tempDisplayName)) return;
        getXoreBoard().getXorePlayers().forEach(xorePlayer -> {
            if(xorePlayer.getPlayer().isOnline()) {
                if(xorePlayer.hasShowedShared() && xorePlayer.getPrivateSidebar().isShowed() == false) sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardObjective", xorePlayer.getID(), tempDisplayName, XoreBoard.XoreBoardPackets.EnumScoreboardHealthDisplay.INTEGER.toNamespace(), 2));
        }});

        this.displayName = tempDisplayName;
    }

    @Override
    public void putLine(@NotNull String lineKey, int value) {
        getXoreBoard().getXorePlayers().forEach(xorePlayer -> {
            if(xorePlayer.getPlayer().isOnline()) {
                if(xorePlayer.hasShowedShared() && xorePlayer.getPrivateSidebar().isShowed() == false) sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardScore", (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).substring(0, 48), xorePlayer.getID(), value, XoreBoard.XoreBoardPackets.EnumScoreboardAction.CHANGE.toNamespace()));
        }});
        this.lineKeys.put(lineKey, value);
    }

    @Override
    public void setLines(HashMap<String, Integer> lineKeys) {
        this.lineKeys.forEach((lineKey, value) -> {
            if(lineKeys.containsKey(lineKey) && lineKeys.get(lineKey).equals(value)) lineKeys.remove(lineKey);
        });
        getXoreBoard().getXorePlayers().forEach(xorePlayer -> {
            if(xorePlayer.getPlayer().isOnline()) {
                if(xorePlayer.hasShowedShared() && xorePlayer.getPrivateSidebar().isShowed() == false) lineKeys.forEach((lineKey, value) -> sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardScore", (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).substring(0, 48), xorePlayer.getID(), value, XoreBoard.XoreBoardPackets.EnumScoreboardAction.CHANGE.toNamespace())));
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
            if(this.lineKeys.containsKey(lineKey) == false || this.lineKeys.get(lineKey).equals(lineValue) == false) putLine(lineKey, lineValue);
        });
    }

    @Override
    public void clearLine(@NotNull String lineKey) {
        if(this.lineKeys.containsKey(lineKey)) {
            getXoreBoard().getXorePlayers().forEach(xorePlayer -> {
                if(xorePlayer.getPlayer().isOnline()) {
                    if(xorePlayer.hasShowedShared() && xorePlayer.getPrivateSidebar().isShowed() == false) sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardScore", (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).substring(0, 48), xorePlayer.getID(), 0, XoreBoard.XoreBoardPackets.EnumScoreboardAction.REMOVE.toNamespace()));
        }});
    }}

    @Override
    public void clearLines() {
        getXoreBoard().getXorePlayers().forEach(xorePlayer -> {
            if(xorePlayer.getPlayer().isOnline()) {
                if(xorePlayer.hasShowedShared() && xorePlayer.getPrivateSidebar().isShowed() == false) this.lineKeys.forEach((lineKey, value) -> sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardScore", (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).substring(0, 48), xorePlayer.getID(), 0, XoreBoard.XoreBoardPackets.EnumScoreboardAction.REMOVE.toNamespace())));
        }});

        this.lineKeys.clear();
    }

    @Override
    public void hideSidebar() {
        getXoreBoard().getXorePlayers().forEach(xorePlayer -> {
            if(xorePlayer.getPlayer().isOnline()) {
                if(xorePlayer.getPrivateSidebar().isShowed() == false && isShowed()) {
                    sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardObjective", xorePlayer.getID(), null, null, 1));

                    this.showedStatus = false;

                    if(xorePlayer.getPreviousSidebar() != null) {
                        if(xorePlayer.getPreviousSidebar() instanceof XoreBoardPrivateSidebar && xorePlayer.getPreviousSidebar().isShowed()) {
                            xorePlayer.setPreviousSidebar(this);
                                xorePlayer.getPrivateSidebar().showSidebar();
                    }} else xorePlayer.setPreviousSidebar(this);
                    xorePlayer.setShowedSharedSidebar(false);
                    return;
            }
            showSidebar(xorePlayer);
                hideSidebar(xorePlayer);
        }});
    }

    /**
     * public void hideSidebar(@NotNull XorePlayer xorePlayer)
     * @param xorePlayer XorePlayer {@link XorePlayer}
     */

    public void hideSidebar(@NotNull XorePlayer xorePlayer) {
        if(xorePlayer.getPlayer().isOnline() == false) return;
        if(xorePlayer.getPrivateSidebar().isShowed() == false && isShowed()) {
            sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardObjective", xorePlayer.getID(), null, null, 1));

            if(xorePlayer.getPreviousSidebar() != null) {
                if(xorePlayer.getPreviousSidebar() instanceof XoreBoardPrivateSidebar && xorePlayer.getPreviousSidebar().isShowed()) {
                        xorePlayer.setPreviousSidebar(this);
                            xorePlayer.getPrivateSidebar().showSidebar();
            }} else xorePlayer.setPreviousSidebar(this);
            xorePlayer.setShowedSharedSidebar(false);
            return;
        }
        showSidebar(xorePlayer);
            hideSidebar(xorePlayer);
    }

    @Override
    public void showSidebar() {
        getXoreBoard().getXorePlayers().forEach(xorePlayer -> {
            if(xorePlayer.getPlayer().isOnline()) {
                if(xorePlayer.getPrivateSidebar().isShowed()) xorePlayer.getPrivateSidebar().hideSidebar();
                if(xorePlayer.getPrivateSidebar().isShowed() == false && xorePlayer.hasShowedShared() == false) {
                    sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardObjective", xorePlayer.getID(), this.displayName, XoreBoard.XoreBoardPackets.EnumScoreboardHealthDisplay.INTEGER.toNamespace(), 0));
                        sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardDisplayObjective", 1, xorePlayer.getID()));
                    this.lineKeys.forEach((lineKey, value) -> sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardScore", (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).substring(0, 48), xorePlayer.getID(), value, XoreBoard.XoreBoardPackets.EnumScoreboardAction.CHANGE.toNamespace())));

                    xorePlayer.setShowedSharedSidebar(true);
                    this.showedStatus = true;
                    return;
                }
                hideSidebar();
                    showSidebar();
        }});
    }

    /**
     * public void showSidebar(@NotNull XorePlayer xorePlayer)
     * @param xorePlayer XorePlayer {@link XorePlayer}
     */

    public void showSidebar(@NotNull XorePlayer xorePlayer) {
        if(xorePlayer.getPlayer().isOnline() == false) return;
        if(xorePlayer.getPrivateSidebar().isShowed()) xorePlayer.getPrivateSidebar().hideSidebar();
        if(xorePlayer.getPrivateSidebar().isShowed() == false && xorePlayer.hasShowedShared() == false) {
            sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardObjective", xorePlayer.getID(), this.displayName, XoreBoard.XoreBoardPackets.EnumScoreboardHealthDisplay.INTEGER.toNamespace(), 0));
                sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardDisplayObjective", 1, xorePlayer.getID()));
            this.lineKeys.forEach((lineKey, value) -> sendPacket(xorePlayer, prepareVanillaPacket("PacketPlayOutScoreboardScore", (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).substring(0, 48), xorePlayer.getID(), value, XoreBoard.XoreBoardPackets.EnumScoreboardAction.CHANGE.toNamespace())));

            xorePlayer.setShowedSharedSidebar(true);
            return;
        }
        hideSidebar(xorePlayer);
            showSidebar(xorePlayer);
    }

    @Override
    public boolean isShowed() {
        return this.showedStatus;
    }

    @Override
    public SidebarType getType() {
        return SidebarType.SHARED;
}}