package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.Sidebar;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Getter
public class XoreBoard {

    private final Scoreboard scoreboard;
    private @NotNull String ID, name;

    private HashMap<org.bukkit.entity.Player, Sidebar> sidebars = new HashMap<org.bukkit.entity.Player, Sidebar>();

    @Nullable
    private XoreBoardGlobalSidebar globalSidebar;

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
        player.setScoreboard(this.scoreboard);

        this.sidebars.put(player, new XoreBoardPlayerSidebar(this, player));
    }

    /**
     * public void removePlayer(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     */

    public void removePlayer(@NotNull org.bukkit.entity.Player player) {

    }

    /**
     * public XoreBoardGlobalSidebar getSidebar()
     * @return XoreBoardGlobalSidebar
     */

    public XoreBoardGlobalSidebar getSidebar() {
        if(this.globalSidebar == null) this.globalSidebar = new XoreBoardGlobalSidebar(this);
        this.sidebars.forEach(((player, sidebar) -> {
            if(((XoreBoardPlayerSidebar) sidebar).isShowedGlobal() == false) this.globalSidebar.showSidebar();
        }));
        return ((XoreBoardGlobalSidebar) this.globalSidebar);
    }

    /**
     *public XoreBoardPlayerSidebar getSidebar(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     * @return XoreBoardPlayerSidebar
     */

    public XoreBoardPlayerSidebar getSidebar(@NotNull org.bukkit.entity.Player player) {
        if(player.isOnline() == false) return null;
        if(this.sidebars.containsKey(player) == false) addPlayer(player);
        return ((XoreBoardPlayerSidebar) this.sidebars.get(player));
    }

    /**
     * public Collection<org.bukkit.entity.Player> getPlayers()
     * @return Collection<org.bukkit.entity.Player>
     */

    @org.jetbrains.annotations.Contract(pure = true)
    public Collection<org.bukkit.entity.Player> getPlayers() {
        return this.sidebars.keySet();
    }

    /**
     * public HashMap<org.bukkit.entity.Player, Sidebar> getSidebars()
     * @return HashMap<org.bukkit.entity.Player, Sidebar>
     */

    @org.jetbrains.annotations.Contract(pure = true)
    public HashMap<org.bukkit.entity.Player, Sidebar> getSidebars() {
        return new HashMap<>(this.sidebars);
    }

    public void destroy() {
        getSidebar().clearLines();
            getSidebar().hideSidebar();
        java.util.List<org.bukkit.entity.Player> temporary = new ArrayList<org.bukkit.entity.Player>(getPlayers());
        temporary.forEach(this::removePlayer);
}}