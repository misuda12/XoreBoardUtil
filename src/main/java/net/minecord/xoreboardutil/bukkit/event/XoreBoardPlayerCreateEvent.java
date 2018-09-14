package net.minecord.xoreboardutil.bukkit.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.XoreBoard;
import net.minecord.xoreboardutil.bukkit.XorePlayer;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public final class XoreBoardPlayerCreateEvent extends XoreBoardEvent {

    private @NotNull final XoreBoard xoreBoard;
    private @NotNull final XorePlayer xorePlayer;

}