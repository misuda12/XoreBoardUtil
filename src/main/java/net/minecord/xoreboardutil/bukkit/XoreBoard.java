package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.event.XoreBoardCreateEvent;
import net.minecord.xoreboardutil.bukkit.event.XoreBoardPlayerRemoveEvent;
import net.minecord.xoreboardutil.bukkit.event.XoreBoardRemoveEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Getter
public class XoreBoard {

    private final @NotNull org.bukkit.scoreboard.Scoreboard scoreboard;
    private @NotNull String ID, name;

    private @NotNull HashMap<org.bukkit.entity.Player, XorePlayer> xorePlayers = new HashMap<org.bukkit.entity.Player, XorePlayer>();
    private SharedSidebar sharedSidebar;

    XoreBoard(@NotNull org.bukkit.scoreboard.Scoreboard scoreboard, @NotNull String ID, @NotNull String name) {
        this.scoreboard = scoreboard;
        this.ID = ID;
        this.name = name;

        final @NotNull XoreBoardCreateEvent xoreBoardCreateEvent = new XoreBoardCreateEvent(this);
        XoreBoardUtil.getPlugin(XoreBoardUtil.class).getServer().getPluginManager().callEvent(xoreBoardCreateEvent);

        if(getEntries() != null && this.xorePlayers.size() > 0) {
            new org.bukkit.scheduler.BukkitRunnable() {

                @Override
                public void run() {
                    getPlayers().stream().filter(player -> player.isOnline() == false).forEach(player -> xorePlayers.remove(player));
            }}.runTaskTimerAsynchronously(XoreBoardUtil.getPlugin(XoreBoardUtil.class), 0L, 20L);
        }
        XoreBoardUtil.getPlugin().getLoggerController().info("Creating new scoreboardUID: " + name);
        this.sharedSidebar = new SharedSidebar(this);
    }

    /**
     * public final org.bukkit.scoreboard.Scoreboard getScoreboard()
     * @return Scoreboard
     */

    @NotNull
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

    @NotNull
    @org.jetbrains.annotations.Contract(pure = true)
    public String setDefaultTitle(@NotNull String defaultTitle) {
        this.name = defaultTitle;
        return this.name;
    }

    /**
     * public void addPlayer(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     */

    public void addPlayer(@NotNull org.bukkit.entity.Player player) {
        if(player.isOnline() == false || player == null) return;
        if(this.xorePlayers.containsKey(player)) return;
        player.setScoreboard(this.scoreboard);
        final @NotNull XorePlayer xorePlayer = new XorePlayer(this, player, player.getEntityId());

        this.xorePlayers.put(player, xorePlayer);
    }

    /**
     * public XorePlayer getPlayer(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     * @return XorePlayer
     */

    public XorePlayer getPlayer(@NotNull org.bukkit.entity.Player player) {
        if(this.xorePlayers.containsKey(player) && player != null && player.isOnline()) return this.xorePlayers.get(player);
        else {
            player.setScoreboard(this.scoreboard);
            this.xorePlayers.put(player, new XorePlayer(this, player, player.getEntityId()));
                return this.xorePlayers.get(player);
    }}

    /**
     * public void removePlayer(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     */

    public void removePlayer(@NotNull org.bukkit.entity.Player player) {
        if(player.isOnline() == false || player == null) return;
        if(this.xorePlayers.containsKey(player)) {
            final @NotNull XorePlayer xorePlayer = this.xorePlayers.get(player);
            hideSidebar(player);

            @NotNull final XoreBoardPlayerRemoveEvent event = new XoreBoardPlayerRemoveEvent(this, xorePlayer);
            XoreBoardUtil.getPlugin(XoreBoardUtil.class).getServer().getPluginManager().callEvent(event);
            this.xorePlayers.remove(player);
    }}

    /**
     * public void hideSidebar(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     */

    public void hideSidebar(@NotNull org.bukkit.entity.Player player) {
        if(player.isOnline() == false || player == null) return;
        if(this.xorePlayers.containsKey(player)) {

            @NotNull XorePlayer xorePlayer = this.xorePlayers.get(player);

            if(xorePlayer.getPrivateSidebar().isShowed()) xorePlayer.getPrivateSidebar().hideSidebar();
            if(xorePlayer.hasShowedShared()) getSharedSidebar().hideSidebar(xorePlayer);
    }}

    @Deprecated
    private void completeUID() {}

    /**
     * public SharedSidebar getSidebar()
     * @return SharedSidebar
     */

    public SharedSidebar getSharedSidebar() {
        return this.sharedSidebar;
    }

    /**
     * public PrivateSidebar getPrivateSidebar(@NotNull XorePlayer xorePlayer)
     * @param xorePlayer XorePlayer {@link XorePlayer}
     * @return PrivateSidebar
     */

    public PrivateSidebar getPrivateSidebar(@NotNull XorePlayer xorePlayer) {
        return xorePlayer.getPrivateSidebar();
    }

    /**
     * public PrivateSidebar getPrivateSidebar(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     * @return PrivateSidebar
     */

    public PrivateSidebar getPrivateSidebar(@NotNull org.bukkit.entity.Player player) {
        return getPlayer(player).getPrivateSidebar();
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
        @NotNull java.util.List<org.bukkit.entity.Player> temporary = new ArrayList<org.bukkit.entity.Player>(getPlayers());
            temporary.forEach(this::removePlayer);

        final @NotNull XoreBoardRemoveEvent xoreBoardRemoveEvent = new XoreBoardRemoveEvent(this.getName());
        XoreBoardUtil.getPlugin(XoreBoardUtil.class).getServer().getPluginManager().callEvent(xoreBoardRemoveEvent);

        java.lang.Runtime.getRuntime().gc();
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
                    Method method = Integer.parseInt(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1).split("_")[1]) > 12 ? Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".ScoreboardServer$Action").getDeclaredMethod("valueOf", String.class) : Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".PacketPlayOutScoreboardScore$EnumScoreboardAction").getDeclaredMethod("valueOf", String.class);
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