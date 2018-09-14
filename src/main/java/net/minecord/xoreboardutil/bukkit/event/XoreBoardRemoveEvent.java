package net.minecord.xoreboardutil.bukkit.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public final class XoreBoardRemoveEvent extends XoreBoardEvent {

    private @NotNull final String name;

}