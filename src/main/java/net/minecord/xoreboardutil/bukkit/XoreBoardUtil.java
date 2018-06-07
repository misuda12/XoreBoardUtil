package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@Getter
public class XoreBoardUtil extends org.bukkit.plugin.java.JavaPlugin {

    private static XoreBoardUtil xoreBoardUtil;

    private HashMap<String, XoreBoard> xoreBoards = new HashMap<String, XoreBoard>();
    private org.bukkit.scoreboard.Scoreboard scoreboard;

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
        getXoreBoard(boardName).destroy();

        this.xoreBoards.remove(boardName);
    }

    /**
     * public void removeXoreBoard(@NotNull String boardName)
     * @param xoreBoard XoreBoard {@link String}
     */

    public void removeXoreBoard(@NotNull XoreBoard xoreBoard) {
        xoreBoard.destroy();

        this.xoreBoards.remove(xoreBoard.getName());
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
     * private int getRandomPacketID()
     * @return int
     */

    private int getRandomPacketID() {
        return this.iterator ++;
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
     * public static XoreBoardUtil getInstance()
     * @return XoreBoardUtil
     */

    @NotNull
    public static XoreBoardUtil getInstance() {
        return xoreBoardUtil;
}}