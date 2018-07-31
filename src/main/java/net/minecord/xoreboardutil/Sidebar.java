package net.minecord.xoreboardutil;

import lombok.Generated;
import net.minecord.xoreboardutil.bukkit.XoreBoard;
import net.minecord.xoreboardutil.bukkit.XoreBoardUtil;
import net.minecord.xoreboardutil.bukkit.XorePlayer;
import net.minecord.xoreboardutil.bukkit.event.XoreBoardSendPacketEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Sidebar {

    /**
     * XoreBoard getXoreBoard()
     * @return XoreBoard
     */

    XoreBoard getXoreBoard();

    /**
     * String getDisplayName()
     * @return String
     */

    @org.jetbrains.annotations.Contract(pure = true)
    String getDisplayName();

    /**
     * void setDisplayName(@org.jetbrains.annotations.NotNull String displayName)
     * @param displayName String {@link String}
     */

    void setDisplayName(@org.jetbrains.annotations.NotNull String displayName);

    /**
     * void putLine(@org.jetbrains.annotations.NotNull String lineKey, int value)
     * @param lineKey String {@link String}
     * @param value int {@link Integer}
     */

    void putLine(@org.jetbrains.annotations.NotNull String lineKey, int value);

    /**
     * default void putLines(java.util.HashMap<String, Integer> lineKeys)
     * @param lineKeys HashMap {@link java.util.HashMap}
     */

    default void putLines(java.util.HashMap<String, Integer> lineKeys) {
        setLines(lineKeys);
    }

    /**
     * void putLines(java.util.HashMap<String, Integer> lineKeys)
     * @param lineKeys HashMap {@link java.util.HashMap}
     */

    void setLines(java.util.HashMap<String, Integer> lineKeys);

    /**
     * java.util.HashMap<String, Integer> getLines()
     * @return java.util.HashMap
     */

    @org.jetbrains.annotations.Contract(pure = true)
    java.util.HashMap<String, Integer> getLines();

    /**
     * public void rewriteLine(@org.jetbrains.annotations.NotNull String lineKey, int value)
     * @param lineKey String {@link String}
     * @param value int {@link Integer}
     */

    void rewriteLine(@org.jetbrains.annotations.NotNull String lineKey, int value);

    /**
     * void rewriteLines(java.util.HashMap<String, Integer> lineKeys)
     * @param lineKeys HashMap {@link java.util.HashMap}
     */

    void rewriteLines(java.util.HashMap<String, Integer> lineKeys);

    /**
     * void clearLine(@org.jetbrains.annotations.NotNull String lineKey)
     * @param lineKey String {@link String}
     */

    void clearLine(@org.jetbrains.annotations.NotNull String lineKey);

    void clearLines();

    void hideSidebar();

    void showSidebar();

    /**
     * boolean isSidebar()
     * @return boolean
     */

    boolean isShowed();

    /**
     * SidebarType getType()
     * @return SidebarType
     */

    SidebarType getType();

    /**
     * default Object prepareVanillaPacket(@NotNull String packetName, Object... objects)
     * @param packetName String {@link String}
     * @param objects Object... {@link Object}
     * @return Object
     */

    default Object prepareVanillaPacket(@NotNull String packetName, Object... objects) {
        int objectIndex = 0;
        Object outputObject = null;
        try {
            outputObject = Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + "." + packetName).newInstance();
            for(@NotNull Field field : getDeclaredFields(Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + "." + packetName))) {
                if(field.getType().getName().contains("IChatBaseComponent") || field.getType().getName().endsWith("IChatBaseComponent")) rewriteField(outputObject, field.getName(), IChatBaseComponentConverter.toIChatBaseComponent((String) objects[objectIndex]));
                else rewriteField(outputObject, field.getName(), objects[objectIndex]);
                    objectIndex++;
        }} catch(@NotNull final ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException ignored) {}
        return outputObject;
    }

    /**
     * default void sendPacket(@NotNull XorePlayer xorePlayer, Object packet)
     * @param xorePlayer XorePlayer {@link XorePlayer}
     * @param packet Object {@link Object}
     */

    default void sendPacket(@NotNull XorePlayer xorePlayer, Object packet) {
        if(xorePlayer.getPlayer().isOnline() == false) return;
        try {
            final XoreBoardSendPacketEvent xoreBoardSendPacketEvent = new XoreBoardSendPacketEvent(getXoreBoard(), xorePlayer.getPlayer(), packet);
            XoreBoardUtil.getPlugin(XoreBoardUtil.class).getServer().getPluginManager().callEvent(xoreBoardSendPacketEvent);
                if(xoreBoardSendPacketEvent.isCancelled()) return;
            Object craftPlayer = Class.forName("org.bukkit.craftbukkit." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".entity.CraftPlayer").cast(xorePlayer.getPlayer());
            Object handle = getFieldInstance(craftPlayer, "entity");
            Object playerConnection = getFieldInstance(handle, "playerConnection");
            invokeMethod(playerConnection, "sendPacket", new Class[] {Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".Packet")}, packet);
        } catch (ClassNotFoundException ignored) {}
    }

    /**
     * default void sendPacket(@NotNull org.bukkit.entity.Player player, Object packet)
     * @param player Player {@link org.bukkit.entity.Player}
     * @param packet Object {@link Object}
     */

    default void sendPacket(@NotNull org.bukkit.entity.Player player, Object packet) {
        if(player.isOnline() == false) return;
        try {
            final XoreBoardSendPacketEvent xoreBoardSendPacketEvent = new XoreBoardSendPacketEvent(getXoreBoard(), player, packet);
            XoreBoardUtil.getPlugin(XoreBoardUtil.class).getServer().getPluginManager().callEvent(xoreBoardSendPacketEvent);
                if(xoreBoardSendPacketEvent.isCancelled()) return;
            Object craftPlayer = Class.forName("org.bukkit.craftbukkit." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".entity.CraftPlayer").cast(player);
            Object handle = getFieldInstance(craftPlayer, "entity");
            Object playerConnection = getFieldInstance(handle, "playerConnection");
            invokeMethod(playerConnection, "sendPacket", new Class[] {Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".Packet")}, packet);
        } catch (ClassNotFoundException ignored) {}
    }

    /**
     * protected List<Field> getDeclaredFields(@NotNull Class clazz)
     * @param clazz Class {@link Class}
     * @return List<Field>
     */

    default List<Field> getDeclaredFields(@NotNull Class clazz) {
        List<Field> fields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));
        if(clazz.getSuperclass() != null) fields.addAll(getDeclaredFields(clazz.getSuperclass()));
        return fields;
    }

    /**
     * default void rewriteField(@NotNull Object packet, @NotNull String key, Object value)
     * @param packet Object {@link Object}
     * @param key String {@link String}
     * @param value Object {@link Object}
     */

    default void rewriteField(@NotNull Object packet, @NotNull String key, Object value) {
        try {
            Field field = packet.getClass().getDeclaredField(key);
                field.setAccessible(true);
                    field.set(packet, value);
        } catch(NoSuchFieldException | IllegalAccessException ignored) {}
    }

    /**
     * protected Object getFieldInstance(@NotNull Object instance, @NotNull String fieldName)
     * @param instance Object {@link Object}
     * @param fieldName String {@link String}
     * @return Object
     */

    default Object getFieldInstance(@NotNull Object instance, @NotNull String fieldName) {
        try {
            Field field = getDeclaredField(getDeclaredFields(instance.getClass()), fieldName);
                field.setAccessible(true);
            return field.get(instance);
        } catch(IllegalAccessException ignored) {}
        return null;
    }

    default void invokeMethod(@NotNull Object instance, @NotNull String methodName, Class<?>[] classes, Object... values) {
        try {
            Method method = instance.getClass().getDeclaredMethod(methodName, classes);
            method.setAccessible(true);
            method.invoke(instance, values);
        } catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {}
    }

    /**
     * default Field getDeclaredField(List<Field> fields, @NotNull String fieldName)
     * @param fields List {@link List}
     * @param fieldName String {@link String}
     * @return Field
     */

    default Field getDeclaredField(List<Field> fields, @NotNull String fieldName) {
        return fields.stream().filter(field -> field.getName().equalsIgnoreCase(fieldName)).findFirst().orElse(null);
    }

    @Generated
    class IChatBaseComponentConverter {
        public static Object toIChatBaseComponent(@NotNull String message) {
            try {
                Method method = Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".IChatBaseComponent").getDeclaredMethod("a", String.class);
                method.setAccessible(true);
                return method.invoke(null, "{\"text\":\"" + org.bukkit.ChatColor.translateAlternateColorCodes('&', message) + "\"}");
            }
            catch(final @NotNull Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
}}}