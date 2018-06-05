package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.event.XoreBoardCreateEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Getter
public class XoreBoard {

    private final org.bukkit.scoreboard.Scoreboard scoreboard;
    private @NotNull String ID, name;

    private HashMap<org.bukkit.entity.Player, XorePlayer> xorePlayers = new HashMap<org.bukkit.entity.Player, XorePlayer>();

    @Nullable
    private XoreBoardSharedSidebar sharedSidebar;

    XoreBoard(org.bukkit.scoreboard.Scoreboard scoreboard, @NotNull String ID, @NotNull String name) {
        this.scoreboard = scoreboard;
        this.ID = ID;
        this.name = name;

        final XoreBoardCreateEvent xoreBoardCreateEvent = new XoreBoardCreateEvent(this);
            XoreBoardUtil.getPlugin(XoreBoardUtil.class).getServer().getPluginManager().callEvent(xoreBoardCreateEvent);
    }

    /**
     * public final org.bukkit.scoreboard.Scoreboard getScoreboard()
     * @return Scoreboard
     */

    public final org.bukkit.scoreboard.Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    /**
     * public final String getID()
     * @return String
     */

    @NotNull
    @org.jetbrains.annotations.Contract(pure = true)
    public final String getID() {
        return this.ID;
    }

    /**
     * public final String getName()
     * @return String
     */

    @NotNull
    @org.jetbrains.annotations.Contract(pure = true)
    public final String getName() {
        return this.name;
    }

    /**
     * public void setDefaultTitle(@NotNull String defaultTitle)
     * @param defaultTitle String {@link String {@value name}}
     */

    public void setDefaultTitle(@NotNull String defaultTitle) {
        this.name = defaultTitle;
    }

    /**
     * public void addPlayer(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     */

    public void addPlayer(@NotNull org.bukkit.entity.Player player) {
        if(player.isOnline() == false) return;
        if(this.xorePlayers.containsKey(player)) return;

        final XorePlayer xorePlayer = new XorePlayer(this, player);
            player.setScoreboard(this.scoreboard);

        this.xorePlayers.put(player, xorePlayer);
    }

    /**
     * public void removePlayer(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     */

    public void removePlayer(@NotNull org.bukkit.entity.Player player) {
        if(player.isOnline() == false) return;
        if(this.xorePlayers.containsKey(player)) {
            final XorePlayer xorePlayer = this.xorePlayers.get(player);

                getSharedSidebar().hideSidebar(xorePlayer);
    }}

    /**
     * public XoreBoardSharedSidebar getSidebar()
     * @return XoreBoardSharedSidebar
     */

    public XoreBoardSharedSidebar getSharedSidebar() {
        if(this.sharedSidebar == null) this.sharedSidebar = new XoreBoardSharedSidebar(this);
        this.xorePlayers.forEach((player, xorePlayer) -> {
            if((xorePlayer.hasSharedSidebar() == false)) this.sharedSidebar.showSidebar();
        });
        return ((XoreBoardSharedSidebar) this.sharedSidebar);
    }

    /**
     * public Collection<org.bukkit.entity.Player> getPlayers()
     * @return Collection<org.bukkit.entity.Player>
     */

    @org.jetbrains.annotations.Contract(pure = true)
    public Collection<org.bukkit.entity.Player> getPlayers() {
        return this.xorePlayers.keySet();
    }

    /**
     * public Collection<XorePlayer> getXorePlayers()
     * @return Collection<XorePlayer>
     */

    @org.jetbrains.annotations.Contract(pure = true)
    public Collection<XorePlayer> getXorePlayers() {
        return this.xorePlayers.values();
    }

    /**
     * public HashMap<org.bukkit.entity.Player, XorePlayer> getEntries()
     * @return HashMap<org.bukkit.entity.Player, XorePlayer>
     */

    @org.jetbrains.annotations.Contract(pure = true)
    public HashMap<org.bukkit.entity.Player, XorePlayer> getEntries() {
        return this.xorePlayers;
    }

    public void destroy() {
        getSharedSidebar().clearLines();
            getSharedSidebar().hideSidebar();
        java.util.List<org.bukkit.entity.Player> temporary = new ArrayList<org.bukkit.entity.Player>(getPlayers());
        temporary.forEach(this::removePlayer);
}}