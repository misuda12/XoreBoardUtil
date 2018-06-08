package net.minecord.xoreboardutil.bukkit;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class XoreBoardUtil extends org.bukkit.plugin.java.JavaPlugin {

    private static HashMap<String, XoreBoard> xoreBoards = new HashMap<String, XoreBoard>();
    private static org.bukkit.scoreboard.Objective objective;
    private static org.bukkit.scoreboard.Scoreboard scoreboard;
    private static org.bukkit.scoreboard.Team.OptionStatus nameTag, collisions;

    private static int name, iterator, randomInt = 0;
    private static Random random = new Random();

    @Override
    public void onEnable() {

        randomInt = random.nextInt(100);
        scoreboard = org.bukkit.Bukkit.getScoreboardManager().getMainScoreboard();
        org.bukkit.Bukkit.getServer().getConsoleSender().sendMessage(org.bukkit.ChatColor.DARK_AQUA + "[XoreBoardUtil]" + " " + org.bukkit.ChatColor.WHITE + "Plugin has been" + " " + ChatColor.GREEN + "enabled");
    }

    @Override
    public void onDisable() {
            destroy();
        org.bukkit.Bukkit.getServer().getConsoleSender().sendMessage(org.bukkit.ChatColor.DARK_AQUA + "[XoreBoardUtil]" + " " + org.bukkit.ChatColor.WHITE + "Plugin has been" + " " + ChatColor.RED + "disabled");
    }

    /**
     * public static XoreBoard getXoreBoard()
     * @return XoreBoard
     */

    @NotNull
    public static XoreBoard getXoreBoard() {
        return getXoreBoard(name ++ + "");
    }

    /**
     * public static XoreBoard getXoreBoard(@NotNull String boardName)
     * @param boardName String {@link String}
     * @return XoreBoard
     */

    @NotNull
    public static XoreBoard getXoreBoard(@NotNull String boardName) {
        if(xoreBoards.containsKey(boardName)) return xoreBoards.get(boardName);
        else return createXoreBoard(boardName);
    }

    /**
     * public static XoreBoard getNextXoreBoard()
     * @return XoreBoard
     */

    @NotNull
    public static XoreBoard getNextXoreBoard() {
        return getXoreBoard(name ++ + "");
    }

    /**
     * public static XoreBoard createXoreBoard(@NotNull String boardName)
     * @param boardName String {@link String}
     * @return XoreBoard
     */

    @NotNull
    public static XoreBoard createXoreBoard(@NotNull String boardName) {
        if(xoreBoards.containsKey(boardName)) return xoreBoards.get(boardName);
        else {
            xoreBoards.put(boardName, new XoreBoard(scoreboard, randomInt + "." + getRandomPacketID(), boardName));
            return xoreBoards.get(boardName);
    }}

    /**
     * public static void removeXoreBoard(@NotNull String boardName)
     * @param boardName String {@link String}
     */

    public static void removeXoreBoard(@NotNull String boardName) {
        if(xoreBoards.containsKey(boardName)) return;
        org.bukkit.Bukkit.getServer().getConsoleSender().sendMessage(org.bukkit.ChatColor.DARK_AQUA + "[XoreBoardUtil]" + " " + org.bukkit.ChatColor.WHITE + "Removing XoreBoard with name:" + " " + ChatColor.YELLOW + boardName);
                getXoreBoard(boardName).destroy();
        xoreBoards.remove(boardName);
    }

    /**
     * public static void removeXoreBoard(@NotNull String boardName)
     * @param xoreBoard XoreBoard {@link String}
     */

    public static void removeXoreBoard(@NotNull XoreBoard xoreBoard) {
        removeXoreBoard(xoreBoard.getName());
    }

    /**
     * public static HashMap<String, XoreBoard> getXoreBoards()
     * @return HashMap<String, XoreBoard>
     */

    @org.jetbrains.annotations.Contract(pure = true)
    public static HashMap<String, XoreBoard> getXoreBoards() {
        return xoreBoards;
    }

    /**
     * public static org.bukkit.scoreboard.Scoreboard getScoreboard()
     * @return org.bukkit.scoreboard.Scoreboard
     */

    public static org.bukkit.scoreboard.Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * public static void setCollisionsRule(@NotNull org.bukkit.scoreboard.Team.OptionStatus value)
     * @param value OptionStatus {@link org.bukkit.scoreboard.Team.OptionStatus}
     */

    public static void setCollisionsRule(@NotNull org.bukkit.scoreboard.Team.OptionStatus value) {
        collisions = value;
        for(org.bukkit.scoreboard.Team team : scoreboard.getTeams()) team.setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, value);
    }

    public static void disableCollisionsRule() {
        collisions = org.bukkit.scoreboard.Team.OptionStatus.NEVER;
        for(org.bukkit.scoreboard.Team team : scoreboard.getTeams()) team.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
    }

    /**
     * public static void setBellowName(@NotNull String displayName)
     * @param displayName String {@link String}
     */

    public static void setBellowName(@NotNull String displayName) {
        objective = scoreboard.getObjective(org.bukkit.scoreboard.DisplaySlot.BELOW_NAME);
        if(objective == null) {
            objective = scoreboard.registerNewObjective("bellow", "name");
                objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.BELOW_NAME);
                    objective.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName));
        }
        else objective.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', displayName));
    }

    /**
     * public static void updateBellowName(@NotNull org.bukkit.entity.Player player, int value)
     * @param player Player {@link org.bukkit.entity.Player}
     * @param value int {@link Integer}
     */

    public static void updateBellowName(@NotNull org.bukkit.entity.Player player, int value) {
        if(objective == null) return;
        scoreboard.getObjective(org.bukkit.scoreboard.DisplaySlot.BELOW_NAME).getScore(player.getPlayer()).setScore(value);
    }

    /**
     * public static org.bukkit.scoreboard.Team getTeam(@NotNull String teamName)
     * @param teamName String {@link String}
     * @return org.bukkit.scoreboard.Team
     */

    public static org.bukkit.scoreboard.Team getTeam(@NotNull String teamName) {
        org.bukkit.scoreboard.Team team = scoreboard.getTeam(teamName) != null ? scoreboard.getTeam(teamName) : scoreboard.registerNewTeam(teamName);
        if(nameTag == null) team.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, nameTag);
            if(collisions == null) team.setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, collisions);
        return team;
    }

    /**
     * public static void setNameTagVisibility(@NotNull org.bukkit.scoreboard.Team.OptionStatus value)
     * @param value org.bukkit.scoreboard.Team.OptionStatus {@link org.bukkit.scoreboard.Team.OptionStatus}
     */

    public static void setNameTagVisible(@NotNull org.bukkit.scoreboard.Team.OptionStatus value) {
        nameTag = value;
        for(org.bukkit.scoreboard.Team team : scoreboard.getTeams()) team.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, value);
    }

    public static void destroy() {
        if(scoreboard != null) {
            for(org.bukkit.scoreboard.Team team : scoreboard.getTeams()) {
                for(String entryName : team.getEntries()) team.removeEntry(entryName);
        }}

        ArrayList<XoreBoard> xoreBoards = new ArrayList<XoreBoard>(getXoreBoards().values());
        for(XoreBoard xoreBoard : xoreBoards) removeXoreBoard(xoreBoard);
    }

    /**
     * private static int getRandomPacketID()
     * @return int
     */

    private static int getRandomPacketID() {
        return iterator ++;
}}