package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Getter
public class XoreBoard {

    private final Scoreboard scoreboard;
    private @NotNull String ID, name;

    private HashSet<XorePlayer> xorePlayers = new HashSet<XorePlayer>();

    @Nullable
    private XoreBoardSharedSidebar sharedSidebar;

    XoreBoard(Scoreboard scoreboard, @NotNull String ID, @NotNull String name) {
        this.scoreboard = scoreboard;
        this.ID = ID;
        this.name = name;
    }

    /**
     * public final Scoreboard getScoreboard()
     * @return Scoreboard
     */

    public final Scoreboard getScoreboard() {
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
        getPlayers().forEach(xorePlayer -> {
            if(xorePlayer.getPlayer() == player) {
                getSharedSidebar().hideSidebar(xorePlayer);
                xorePlayer.getPrivateSidebar().hideSidebar();

                getPlayers().remove(xorePlayer);
        }});

        final XorePlayer xorePlayer = new XorePlayer(this, player);
        xorePlayer.getPlayer().setScoreboard(this.scoreboard);

        this.xorePlayers.add(xorePlayer);
    }

    /**
     * public void removePlayer(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     */

    public void removePlayer(@NotNull org.bukkit.entity.Player player) {
        if(player.isOnline() == false) return;
        getPlayers().forEach(xorePlayer -> {
            if(xorePlayer.getPlayer() == player) {
                getSharedSidebar().hideSidebar(xorePlayer);
                xorePlayer.getPrivateSidebar().hideSidebar();

                    getPlayers().remove(xorePlayer);
        }});
    }

    /**
     * public XoreBoardSharedSidebar getSidebar()
     * @return XoreBoardSharedSidebar
     */

    public XoreBoardSharedSidebar getSharedSidebar() {
        if(this.sharedSidebar == null) this.sharedSidebar = new XoreBoardSharedSidebar(this);
        this.xorePlayers.forEach((xorePlayer-> {
            if((xorePlayer.hasSharedSidebar() == false)) this.sharedSidebar.showSidebar();
        }));
        return ((XoreBoardSharedSidebar) this.sharedSidebar);
    }

    /**
     * public Collection<XorePlayer> getPlayers()
     * @return Collection<XorePlayer>
     */

    @org.jetbrains.annotations.Contract(pure = true)
    public Collection<XorePlayer> getPlayers() {
        return this.xorePlayers;
    }

    public void destroy() {
        getSharedSidebar().clearLines();
            getSharedSidebar().hideSidebar();
        java.util.List<XorePlayer> temporary = new ArrayList<XorePlayer>(getPlayers());
        temporary.forEach(xorePlayer -> removePlayer(xorePlayer.getPlayer()));
}}