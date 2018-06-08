package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.Sidebar;
import net.minecord.xoreboardutil.SidebarType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class XoreBoardPrivateSidebar implements Sidebar {

    private @NotNull final XorePlayer xorePlayer;

    private @NotNull XoreBoard xoreBoard;
    private String displayName;
    private ConcurrentHashMap<String, Integer> lineKeys = new ConcurrentHashMap<String, Integer>();

    private boolean showedStatus = false;

    XoreBoardPrivateSidebar(@NotNull XoreBoard xoreBoard, @NotNull XorePlayer xorePlayer) {
        this.xoreBoard = xoreBoard;
        this.xorePlayer = xorePlayer;

        this.displayName = (org.bukkit.ChatColor.translateAlternateColorCodes('&', xoreBoard.getName())).length() > 32 ? (org.bukkit.ChatColor.translateAlternateColorCodes('&', xoreBoard.getName())).substring(0, 32) : (org.bukkit.ChatColor.translateAlternateColorCodes('&', xoreBoard.getName()));
    }

    @NotNull
    @Override
    public final XoreBoard getXoreBoard() {
        return this.xoreBoard;
    }

    /**
     *public final XorePlayer getXorePlayer()
     * @return XorePlayer
     */

    @NotNull
    public final XorePlayer getXorePlayer() {
        return this.xorePlayer;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public void setDisplayName(@NotNull String displayName) {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
        String tempDisplayName = (org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName)).length() > 32 ? (org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName)).substring(0, 32) : (org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName));
        if(this.displayName.equals(tempDisplayName)) return;
        if(getXorePlayer().hasShowedShared() == false && isShowed()) sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardObjective", getXorePlayer().getID(), tempDisplayName, XoreBoard.XoreBoardPackets.EnumScoreboardHealthDisplay.INTEGER.toNamespace(), 2));

        this.displayName = tempDisplayName;
    }

    @Override
    public void putLine(@NotNull String lineKey, int value) {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
        if(getXorePlayer().hasShowedShared() == false && isShowed()) sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardScore", getXorePlayer().getID(), (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).length() > 48 ? (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).substring(0, 48) : (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)), value, XoreBoard.XoreBoardPackets.EnumScoreboardAction.CHANGE.toNamespace()));
        this.lineKeys.put(lineKey, value);
    }

    @Override
    public void setLines(HashMap<String, Integer> lineKeys) {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
        this.lineKeys.forEach((lineKey, value) -> {
            if(lineKeys.containsKey(lineKey) && lineKeys.get(lineKey).equals(value)) lineKeys.remove(lineKey);
        });
        if(getXorePlayer().hasShowedShared() == false && isShowed()) lineKeys.forEach((lineKey, value) -> sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardScore", getXorePlayer().getID(), (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).length() > 48 ? (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).substring(0, 48) : (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)), value, XoreBoard.XoreBoardPackets.EnumScoreboardAction.CHANGE.toNamespace())));
        this.lineKeys.putAll(lineKeys);
    }

    @Override
    public HashMap<String, Integer> getLines() {
        return new HashMap<String, Integer>(this.lineKeys);
    }

    @Override
    public void rewriteLine(@NotNull String lineKey, int value) {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
        if(this.lineKeys.containsKey(lineKey) == false || this.lineKeys.get(lineKey).equals(value) == false) putLine(lineKey, value);
        else {
            clearLine(lineKey);
                putLine(lineKey, value);
    }}

    @Override
    public void rewriteLines(HashMap<String, Integer> lineKeys) {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
        this.lineKeys.forEach((lineKey, lineValue) -> {
            if(lineKeys.containsKey(lineKey) == false) clearLine(lineKey);
        });
        lineKeys.forEach((lineKey, lineValue) -> {
            if(this.lineKeys.containsKey(lineKey) == false || this.lineKeys.get(lineKey).equals(lineValue) == false) putLine(lineKey, lineValue);
        });
    }

    @Override
    public void clearLine(@NotNull String lineKey) {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
        if(this.lineKeys.containsKey(lineKey)) {
            if(getXorePlayer().hasShowedShared() == false && isShowed()) sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardScore", getXorePlayer().getID(), (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).length() > 48 ? (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).substring(0, 48) : (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)), 0, XoreBoard.XoreBoardPackets.EnumScoreboardAction.REMOVE.toNamespace()));
    }}

    @Override
    public void clearLines() {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
        this.lineKeys.forEach((lineKey, value) -> {
            if(getXorePlayer().hasShowedShared() == false && isShowed()) sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardScore", getXorePlayer().getID(), (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).length() > 48 ? (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).substring(0, 48) : (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)), 0, XoreBoard.XoreBoardPackets.EnumScoreboardAction.REMOVE.toNamespace()));
        });

        this.lineKeys.clear();
    }

    @Override
    public void hideSidebar() {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
        if(getXorePlayer().hasShowedShared() == false && isShowed()) {
            sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardObjective", getXorePlayer().getID(), null, null, 1));

            this.showedStatus = false;

            if(getXorePlayer().getPreviousSidebar() != null) {
                if(getXorePlayer().getPreviousSidebar() instanceof XoreBoardSharedSidebar && getXorePlayer().getPreviousSidebar().isShowed()) {
                    getXorePlayer().setPreviousSidebar(this);
                        getXoreBoard().getSharedSidebar().showSidebar(getXorePlayer());
            }} else getXorePlayer().setPreviousSidebar(this);
            return;
        }
        showSidebar();
            hideSidebar();
    }

    @Override
    public void showSidebar() {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
        if(getXorePlayer().hasShowedShared()) getXoreBoard().getSharedSidebar().hideSidebar(getXorePlayer());
        if(getXorePlayer().hasShowedShared() == false && isShowed() == false) {
            sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardObjective", getXorePlayer().getID(), this.displayName, XoreBoard.XoreBoardPackets.EnumScoreboardHealthDisplay.INTEGER.toNamespace(), 0));
                sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardDisplayObjective", 1, getXorePlayer().getID()));
            this.lineKeys.forEach((lineKey, value) -> sendPacket(getXorePlayer(), prepareVanillaPacket("PacketPlayOutScoreboardScore", getXorePlayer().getID(), (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).length() > 48 ? (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)).substring(0, 48) : (org.bukkit.ChatColor.translateAlternateColorCodes('&', lineKey)), value, XoreBoard.XoreBoardPackets.EnumScoreboardAction.CHANGE.toNamespace())));
            System.out.println("point:1");
            this.showedStatus = true;
            return;
        }
        System.out.println("point:2");
        hideSidebar();
            showSidebar();
    }

    @Override
    public boolean isShowed() {
        return getXorePlayer().getPlayer().isOnline() && this.showedStatus;
    }

    @Override
    public SidebarType getType() {
        return SidebarType.PRIVATE;
}}