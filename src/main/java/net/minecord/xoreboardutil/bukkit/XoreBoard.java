package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.event.XoreBoardCreateEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
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

        if(getEntries() != null && this.xorePlayers.size() > 0) {
            org.bukkit.Bukkit.getScheduler().runTaskTimerAsynchronously(XoreBoardUtil.getPlugin(XoreBoardUtil.class), () -> {
                this.xorePlayers.forEach((player, xorePlayer) -> {
                    if(player.isOnline() == false) this.xorePlayers.remove(player);
            });
        }, 0, 20);
    }}

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
        player.setScoreboard(this.scoreboard);

        final XorePlayer xorePlayer = new XorePlayer(this, player);

        this.xorePlayers.put(player, xorePlayer);
        if(this.sharedSidebar != null) getSharedSidebar().showSidebar(xorePlayer);
    }

    /**
     * public XorePlayer getPlayer(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     * @return XorePlayer
     */

    public XorePlayer getPlayer(@NotNull org.bukkit.entity.Player player) {
        if(this.xorePlayers.containsKey(player)) return xorePlayers.get(player);
        else {
            player.setScoreboard(this.scoreboard);
            this.xorePlayers.put(player, new XorePlayer(this, player));
                return this.xorePlayers.get(player);
    }}

    /**
     * public void removePlayer(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     */

    public void removePlayer(@NotNull org.bukkit.entity.Player player) {
        if(player.isOnline() == false) return;
        if(this.xorePlayers.containsKey(player)) {
            final XorePlayer xorePlayer = this.xorePlayers.get(player);
                if(xorePlayer.getPrivateSidebar().isShowed()) xorePlayer.getPrivateSidebar().hideSidebar();
                    if(xorePlayer.hasShowedShared()) getSharedSidebar().hideSidebar(xorePlayer);

            this.xorePlayers.remove(player);
    }}

    /**
     * public void hideSidebar(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     */

    public void hideSidebar(@NotNull org.bukkit.entity.Player player) {
        if(player.isOnline() == false) return;
        if(this.xorePlayers.containsKey(player)) {
            this.xorePlayers.get(player).setPreviousSidebar(null);
                this.xorePlayers.get(player).getPrivateSidebar().hideSidebar();
                    getSharedSidebar().hideSidebar(this.xorePlayers.get(player));
    }}

    /**
     * public XoreBoardSharedSidebar getSidebar()
     * @return XoreBoardSharedSidebar
     */

    public XoreBoardSharedSidebar getSharedSidebar() {
        if(this.sharedSidebar == null) this.sharedSidebar = new XoreBoardSharedSidebar(this);
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
    }

    @Getter
    static class XoreBoardPackets {
        public enum EnumScoreboardAction {

            CHANGE, REMOVE;

            /**
             * public Object toNamespace()
             * @return Object
             */

            public Object toNamespace() {
                try {
                    Method method = Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".PacketPlayOutScoreboardScore$EnumScoreboardAction").getDeclaredMethod("valueOf", String.class);
                    method.setAccessible(true);
                    return method.invoke(null, name());
                }
                catch(final @NotNull Exception ignored) {}
                return null;
        }}

        public enum EnumScoreboardHealthDisplay {

            INTEGER, HEARTS;

            /**
             * public Object toNamespace()
             * @return Object
             */

            public Object toNamespace() {
                try {
                    Method method = Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".IScoreboardCriteria$EnumScoreboardHealthDisplay").getDeclaredMethod("valueOf", String.class);
                    method.setAccessible(true);
                    return method.invoke(null, name());
                }
                catch(final @NotNull Exception ignored) {}
                return null;
}}}}