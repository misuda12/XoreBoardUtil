package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.Sidebar;
import net.minecord.xoreboardutil.SidebarType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import static net.minecord.xoreboardutil.bukkit.XoreBoard.XoreBoardPackets.getPacket;

@Getter
public class XoreBoardSharedSidebar implements Sidebar {

    private String displayName;
    private ConcurrentHashMap<String, Integer> lineKeys = new ConcurrentHashMap<String, Integer>();

    public XoreBoardSharedSidebar(@NotNull XoreBoard xoreBoard) {

    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public void setDisplayName(@NotNull String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void putLine(@NotNull String lineKey, int value) {

    }

    @Override
    public void setLines(HashMap<String, Integer> lineKeys) {

    }

    @Override
    public HashMap<String, Integer> getLines() {
        return new HashMap<String, Integer>(this.lineKeys);
    }

    @Override
    public void rewriteLine(@NotNull String lineKey, int value) {

    }

    @Override
    public void rewriteLines(HashMap<String, Integer> lineKeys) {

    }

    @Override
    public void clearLine(@NotNull String lineKey) {

    }

    @Override
    public void clearLines() {

    }

    @Override
    public void hideSidebar() {

    }

    public void hideSidebar(@NotNull XorePlayer xorePlayer) {

    }

    @Override
    public void showSidebar() {

    }

    @Override
    public boolean isShowed() {
        return false;
    }

    @Override
    public void setShowedSidebar(boolean statement) {

}

    @Override
    public SidebarType getType() {
        return SidebarType.SHARED;
    }
}