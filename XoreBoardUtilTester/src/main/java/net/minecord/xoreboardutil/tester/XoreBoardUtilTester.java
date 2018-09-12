package net.minecord.xoreboardutil.tester;

import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.XoreBoard;
import net.minecord.xoreboardutil.bukkit.XoreBoardUtil;
import net.minecord.xoreboardutil.bukkit.event.XoreBoardSendPacketEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.logging.Level;

@Getter
public final class XoreBoardUtilTester extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener {
    private @NotNull XoreBoard xoreBoard;

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);

        this.xoreBoard = XoreBoardUtil.getNextXoreBoard();
        @NotNull String defaultTitle = this.xoreBoard.setDefaultTitle("<3 Unicorns, do we ?");
    }

    @org.bukkit.event.EventHandler
    public void on(@NotNull final org.bukkit.event.player.PlayerJoinEvent event) {
        this.xoreBoard.addPlayer(event.getPlayer());
        @NotNull HashMap<String, Integer> lines = new HashMap<String, Integer>();
        lines.put("packetID: " + this.xoreBoard.getPlayer(event.getPlayer()).getID(), 1);
            lines.put("name: " + event.getPlayer().getName(), 2);
                lines.put("pID: " + event.getPlayer().getEntityId(), 3);
        this.xoreBoard.getSharedSidebar().putLines(lines);
        this.xoreBoard.getSharedSidebar().showSidebar();
    }

    @org.bukkit.event.EventHandler
    public void packet(@NotNull final XoreBoardSendPacketEvent event) {
        getLogger().log(Level.CONFIG, event.getPacketObject().toString());
}}