package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.Sidebar;
import org.jetbrains.annotations.NotNull;

@Getter
public class XorePlayer {

    private @NotNull final org.bukkit.entity.Player player;
    private Sidebar sidebar;

    XorePlayer(@NotNull org.bukkit.entity.Player player) {
        this.player = player;
    }

    /**
     * public Sidebar getSidebar()
     * @return Sidebar
     */

    public Sidebar getSidebar() {
        return this.sidebar;
    }

    /**
     * public void setSidebar(@NotNull Sidebar sidebar)
     * @param sidebar Sidebar
     */

    public void setSidebar(@NotNull Sidebar sidebar) {
        this.sidebar = sidebar;
    }

    /**
     * public final org.bukkit.entity.Player getPlayer()
     * @return Player
     */

    @NotNull
    public final org.bukkit.entity.Player getPlayer() {
        return this.player;
}}