package net.minecord.xoreboardutil.bukkit.controller;

import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.XoreBoardUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class LogController extends Controller {
    public LogController(@NotNull XoreBoardUtil plugin) {
        super(plugin);
    }

    /**
     * public LoggerController info(@Nullable String message)
     * @param message String {@link String}
     * @return LoggerController
     */

    public LogController info(@Nullable String message) {
        log(LogController.Level.INFO, message);
        return this;
    }

    /**
     * public LoggerController warn(@Nullable String message)
     * @param message String {@link String}
     * @return LoggerController
     */

    public LogController warn(@Nullable String message) {
        log(LogController.Level.WARNING, message);
        return this;
    }

    /**
     * public LoggerController error(@Nullable String message)
     * @param message String {@link String}
     * @return LoggerController
     */

    public LogController error(@Nullable String message) {
        log(LogController.Level.ERROR, message);
        return this;
    }

    /**
     * public LoggerController severe(@Nullable String message)
     * @param message String {@link String}
     * @return LoggerController
     */

    public LogController severe(@Nullable String message) {
        log(LogController.Level.SEVERE, message);
        return this;
    }

    /**
     * public LoggerController debug(Object instance, @Nullable String message)
     * @param instance Object {@link Object}
     * @param message String {@link String}
     * @return LoggerController
     */

    public LogController debug(Object instance, @Nullable String message) {
        log(LogController.Level.DEBUG, instance.getClass().getName().split("\\.")[instance.getClass().getName().split("\\.").length - 1] + " " +  message);
        return this;
    }

    /**
     * private LoggerController log(@NotNull LoggerController.Level level, @Nullable String message)
     * @param level Level {@link LogController.Level}
     * @param message String {@link String}
     * @return LoggerController
     */

    private LogController log(@NotNull LogController.Level level, @Nullable String message) {
        getPlugin().getServer().getConsoleSender().sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', level.getPrefix() + (message != null ? message : "null")));
        return this;
    }

    @Getter
    public enum Level {

        INFO(org.bukkit.ChatColor.YELLOW + "[INFO]"), WARNING(org.bukkit.ChatColor.GOLD + "[WARN]"), ERROR(org.bukkit.ChatColor.RED + "[ERROR]"), SEVERE(org.bukkit.ChatColor.DARK_RED + "[SEVERE]"), DEBUG(org.bukkit.ChatColor.WHITE + "[DEBUG]");
        private @NotNull final String prefix;

        Level(@NotNull String prefix) {
            this.prefix = prefix;
        }

        @org.jetbrains.annotations.Contract(pure = true)
        public final @NotNull String getPrefix() {
            return org.bukkit.ChatColor.GOLD + "XoreBoardUtil" + org.bukkit.ChatColor.WHITE + " | " + this.prefix + " ";
        }

        @Override
        public String toString() {
            return name();
}}}