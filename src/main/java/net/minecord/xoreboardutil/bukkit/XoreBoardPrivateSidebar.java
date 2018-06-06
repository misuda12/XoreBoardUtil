package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.Sidebar;
import net.minecord.xoreboardutil.SidebarType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import static net.minecord.xoreboardutil.bukkit.XoreBoard.XoreBoardPackets.getPacket;

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

        this.displayName = (org.bukkit.ChatColor.translateAlternateColorCodes('&', xoreBoard.getName())).substring(0, 32);
    }

    /**
     * public XoreBoard getXoreBoard()
     * @return XoreBoard
     */

    @NotNull
    public XoreBoard getXoreBoard() {
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
        if(getXorePlayer().hasSharedSidebar()) return;
        String tempDisplayName = (org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName)).substring(0, 32);
        if(getDisplayName().equals(tempDisplayName)) return;

        // Packet

        this.displayName = tempDisplayName;
    }

    @Override
    public void putLine(@NotNull String lineKey, int value) {
        if(getXorePlayer().getPlayer().isOnline() == false) return;

        // Packet

        this.lineKeys.put(lineKey, value);
    }

    @Override
    public void setLines(HashMap<String, Integer> lineKeys) {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
        this.lineKeys.forEach((lineKey, value) -> {
            if(lineKeys.containsKey(lineKey) && lineKeys.get(lineKey).equals(value)) lineKeys.remove(lineKey);
        });

        // Packet

        this.lineKeys.putAll(lineKeys);
    }

    @Override
    public HashMap<String, Integer> getLines() {
        return new HashMap<String, Integer>(this.lineKeys);
    }

    @Override
    public void rewriteLine(@NotNull String lineKey, int value) {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
        if(this.lineKeys.containsKey(lineKey)) {

        } else putLine(lineKey, value);
    }

    @Override
    public void rewriteLines(HashMap<String, Integer> lineKeys) {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
    }

    @Override
    public void clearLine(@NotNull String lineKey) {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
    }

    @Override
    public void clearLines() {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
    }

    @Override
    public void hideSidebar() {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
    }

    @Override
    public void showSidebar() {
        if(getXorePlayer().getPlayer().isOnline() == false) return;
    }

    @Override
    public boolean isShowed() {
        return getXorePlayer().getPlayer().isOnline() && this.showedStatus;
    }

    @Override
    public void setShowedSidebar(boolean showedStatus) {
        if(getXorePlayer().getPlayer().isOnline() == false) return;

        this.showedStatus = showedStatus;
    }

    @Override
    public SidebarType getType() {
        return SidebarType.PRIVATE;
}}