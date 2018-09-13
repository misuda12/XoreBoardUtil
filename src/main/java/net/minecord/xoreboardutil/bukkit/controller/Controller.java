package net.minecord.xoreboardutil.bukkit.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.XoreBoardUtil;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public abstract class Controller {
    private @NotNull final XoreBoardUtil plugin;

    public void onDisable() {}

    /**
     * public XoreBoardUtil getPlugin()
     * @return XoreBoardUtil
     */

    @NotNull
    public XoreBoardUtil getPlugin() {
        return this.plugin;
}}