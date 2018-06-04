package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.Sidebar;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

@Getter
public class XoreBoardPlayerSidebar implements Sidebar {

    private String displayName;

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
        return null;
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

    @Override
    public void showSidebar() {

    }

    @Override
    public boolean isShowed() {
        return false;
    }

    @Override
    public void setShowedSidebar(boolean statement) {

}}