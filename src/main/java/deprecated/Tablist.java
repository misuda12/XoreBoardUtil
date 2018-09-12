package deprecated;

import lombok.Generated;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public interface Tablist {

    /**
     * TablistType getType()
     * @return TablistType
     */

    TablistType getType();

    @Generated
    class IChatBaseComponentConverter {
        public static Object toIChatBaseComponent(@NotNull String message) {
            try {
                Method method = Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".IChatBaseComponent$ChatSerializer").getDeclaredMethod("a", String.class);
                method.setAccessible(true);
                return method.invoke(null, "{\"text\":\"" + org.bukkit.ChatColor.translateAlternateColorCodes('&', message) + "\"}");
            }
            catch(final @NotNull Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
}}}