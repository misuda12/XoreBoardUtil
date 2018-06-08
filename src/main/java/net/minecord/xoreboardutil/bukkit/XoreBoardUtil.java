package net.minecord.xoreboardutil.bukkit;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class XoreBoardUtil extends org.bukkit.plugin.java.JavaPlugin {

    private static XoreBoardUtil xoreBoardUtil;

    private HashMap<String, XoreBoard> xoreBoards = new HashMap<String, XoreBoard>();
    private org.bukkit.scoreboard.Objective objective;
    private org.bukkit.scoreboard.Scoreboard scoreboard;
    private org.bukkit.scoreboard.Team.OptionStatus nameTag, collisions;

    private int name, iterator, randomInt = 0;
    private Random random = new Random();

    @Override
    public void onEnable() {

        xoreBoardUtil = this;

        this.randomInt = random.nextInt(100);
        this.scoreboard = org.bukkit.Bukkit.getScoreboardManager().getMainScoreboard();
        getServer().getLogger().warning("Plugin has been successfully enabled");
    }

    @Override
    public void onDisable() {
            destroy();
        getServer().getLogger().warning("Plugin has been successfully disabled");
    }

    /**
     * public XoreBoard getXoreBoard()
     * @return XoreBoard
     */

    @NotNull
    public XoreBoard getXoreBoard() {
        return getXoreBoard(name ++ + "");
    }

    /**
     * public XoreBoard getXoreBoard(@NotNull String boardName)
     * @param boardName String {@link String}
     * @return XoreBoard
     */

    @NotNull
    public XoreBoard getXoreBoard(@NotNull String boardName) {
        if(this.xoreBoards.containsKey(boardName)) return this.xoreBoards.get(boardName);
        else return createXoreBoard(boardName);
    }

    /**
     * public XoreBoard getNextXoreBoard()
     * @return XoreBoard
     */

    @NotNull
    public XoreBoard getNextXoreBoard() {
        return getXoreBoard(name ++ + "");
    }

    /**
     * public XoreBoard createXoreBoard(@NotNull String boardName)
     * @param boardName String {@link String}
     * @return XoreBoard
     */

    @NotNull
    public XoreBoard createXoreBoard(@NotNull String boardName) {
        if(this.xoreBoards.containsKey(boardName)) return this.xoreBoards.get(boardName);
        else return this.xoreBoards.put(boardName, new XoreBoard(getScoreboard(), this.randomInt + "." + getRandomPacketID(), boardName));
    }

    /**
     * public void removeXoreBoard(@NotNull String boardName)
     * @param boardName String {@link String}
     */

    public void removeXoreBoard(@NotNull String boardName) {
        if(this.xoreBoards.containsKey(boardName)) return;
        getServer().getLogger().warning("Removing XoreBoard(" + boardName + ") with ID(" + getXoreBoard(boardName).getID() + ")");
        getXoreBoard(boardName).destroy();
        this.xoreBoards.remove(boardName);
    }

    /**
     * public void removeXoreBoard(@NotNull String boardName)
     * @param xoreBoard XoreBoard {@link String}
     */

    public void removeXoreBoard(@NotNull XoreBoard xoreBoard) {
        removeXoreBoard(xoreBoard.getName());
    }

    /**
     * public HashMap<String, XoreBoard> getXoreBoards()
     * @return HashMap<String, XoreBoard>
     */

    @org.jetbrains.annotations.Contract(pure = true)
    public HashMap<String, XoreBoard> getXoreBoards() {
        return this.xoreBoards;
    }

    /**
     * public org.bukkit.scoreboard.Scoreboard getScoreboard()
     * @return org.bukkit.scoreboard.Scoreboard
     */

    public org.bukkit.scoreboard.Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    /**
     * public void setCollisionsRule(@NotNull org.bukkit.scoreboard.Team.OptionStatus value)
     * @param value OptionStatus {@link org.bukkit.scoreboard.Team.OptionStatus}
     */

    public void setCollisionsRule(@NotNull org.bukkit.scoreboard.Team.OptionStatus value) {
        this.collisions = value;
        for(org.bukkit.scoreboard.Team team : this.scoreboard.getTeams()) team.setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, value);
    }

    public void disableCollisionsRule() {
        this.collisions = org.bukkit.scoreboard.Team.OptionStatus.NEVER;
        for(org.bukkit.scoreboard.Team team : this.scoreboard.getTeams()) team.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
    }

    /**
     * public void setBellowName(@NotNull String displayName)
     * @param displayName String {@link String}
     */

    public void setBellowName(@NotNull String displayName) {
        this.objective = this.scoreboard.getObjective(org.bukkit.scoreboard.DisplaySlot.BELOW_NAME);
        if(this.objective == null) {
            this.objective = this.scoreboard.registerNewObjective("bellow", "name");
                this.objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.BELOW_NAME);
                    this.objective.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName));
        }
        else this.objective.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName));
    }

    /**
     * public void updateBellowName(@NotNull org.bukkit.entity.Player player, int value)
     * @param player Player {@link org.bukkit.entity.Player}
     * @param value int {@link Integer}
     */

    public void updateBellowName(@NotNull org.bukkit.entity.Player player, int value) {
        if(this.objective == null) return;
        this.scoreboard.getObjective(org.bukkit.scoreboard.DisplaySlot.BELOW_NAME).getScore(player.getPlayer()).setScore(value);
    }

    /**
     * public org.bukkit.scoreboard.Team getTeam(@NotNull String teamName)
     * @param teamName String {@link String}
     * @return org.bukkit.scoreboard.Team
     */

    public org.bukkit.scoreboard.Team getTeam(@NotNull String teamName) {
        org.bukkit.scoreboard.Team team = this.scoreboard.getTeam(teamName) != null ? this.scoreboard.getTeam(teamName) : this.scoreboard.registerNewTeam(teamName);
        if(this.nameTag == null) team.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, this.nameTag);
            if(this.collisions == null) team.setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, this.collisions);
        return team;
    }

    /**
     * public void setNameTagVisibility(@NotNull org.bukkit.scoreboard.Team.OptionStatus value)
     * @param value org.bukkit.scoreboard.Team.OptionStatus {@link org.bukkit.scoreboard.Team.OptionStatus}
     */

    public void setNameTagVisible(@NotNull org.bukkit.scoreboard.Team.OptionStatus value) {
        this.nameTag = value;
        for(org.bukkit.scoreboard.Team team : this.scoreboard.getTeams()) team.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, value);
    }

    public void destroy() {
        if(this.scoreboard != null) {
            for(org.bukkit.scoreboard.Team team : this.scoreboard.getTeams()) {
                for(String entryName : team.getEntries()) team.removeEntry(entryName);
        }}

        ArrayList<XoreBoard> xoreBoards = new ArrayList<XoreBoard>(this.xoreBoards.values());
        for(XoreBoard xoreBoard : xoreBoards) removeXoreBoard(xoreBoard);
    }

    /**
     * private int getRandomPacketID()
     * @return int
     */

    private int getRandomPacketID() {
        return this.iterator ++;
    }

    /**
     * public static XoreBoardUtil getInstance()
     * @return XoreBoardUtil
     */

    @NotNull
    public static XoreBoardUtil getInstance() {
        return xoreBoardUtil;
}}