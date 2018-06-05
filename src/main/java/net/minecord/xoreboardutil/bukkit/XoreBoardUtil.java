package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

@Getter
public class XoreBoardUtil extends JavaPlugin {

    private static HashMap<String, XoreBoard> xoreBoards = new HashMap<String, XoreBoard>();
    private static Scoreboard scoreboard;


    @Override
    public void onEnable() {
        super.onEnable();
    }
}