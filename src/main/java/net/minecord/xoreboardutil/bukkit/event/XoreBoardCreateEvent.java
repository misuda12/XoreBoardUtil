package net.minecord.xoreboardutil.bukkit.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.XoreBoard;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public final class XoreBoardCreateEvent extends XoreBoardEvent {

    private @NotNull final XoreBoard xoreBoard;

}