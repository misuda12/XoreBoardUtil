package net.minecord.xoreboardutil.tester;

import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.XoreBoard;
import net.minecord.xoreboardutil.bukkit.XoreBoardUtil;
import net.minecord.xoreboardutil.bukkit.event.XoreBoardSendPacketEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

@Getter
public final class XoreBoardUtilTester extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener {
    private @NotNull XoreBoard xoreBoard;

    @Override
    public void onEnable() {

        this.xoreBoard = XoreBoardUtil.getNextXoreBoard();
        getServer().getPluginManager().registerEvents(this, this);
        @NotNull String defaultTitle = this.xoreBoard.setDefaultTitle("${project.name}");
        getLogger().info("connected=" + getServer().getOnlinePlayers().size() + 1 + ";adding=0;created=0");
    }

    @org.bukkit.event.EventHandler
    public void on(@NotNull final org.bukkit.event.player.PlayerJoinEvent event) {
        getLogger().info("connected=1;adding=1;created=0");
        this.xoreBoard.addPlayer(event.getPlayer());
        @NotNull HashMap<String, Integer> lines = new HashMap<String, Integer>();
        lines.put("packetID: " + this.xoreBoard.getPlayer(event.getPlayer()).getID(), 1);
            lines.put("name: " + event.getPlayer().getName(), 2);
                lines.put("pID: " + event.getPlayer().getEntityId(), 3);
        this.xoreBoard.getSharedSidebar().putLines(lines);
        this.xoreBoard.getSharedSidebar().showSidebar();
    }

    @org.bukkit.event.EventHandler
    public void on(@NotNull final org.bukkit.event.player.PlayerQuitEvent event) {
        getLogger().info("online=" + event.getPlayer().isOnline());
    }

    @org.bukkit.event.EventHandler
    public void on(@NotNull final XoreBoardSendPacketEvent event) {
        getLogger().info("packet=" + event.getPacketObject().toString());
}}