package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;
import net.minecord.xoreboardutil.bukkit.event.XoreBoardCreateEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Getter
public class XoreBoard {

    private final org.bukkit.scoreboard.Scoreboard scoreboard;
    private @NotNull String ID, name;

    private HashMap<org.bukkit.entity.Player, XorePlayer> xorePlayers = new HashMap<org.bukkit.entity.Player, XorePlayer>();

    @Nullable
    private XoreBoardSharedSidebar sharedSidebar;

    XoreBoard(org.bukkit.scoreboard.Scoreboard scoreboard, @NotNull String ID, @NotNull String name) {
        this.scoreboard = scoreboard;
        this.ID = ID;
        this.name = name;

        final XoreBoardCreateEvent xoreBoardCreateEvent = new XoreBoardCreateEvent(this);
            XoreBoardUtil.getPlugin(XoreBoardUtil.class).getServer().getPluginManager().callEvent(xoreBoardCreateEvent);
    }

    /**
     * public final org.bukkit.scoreboard.Scoreboard getScoreboard()
     * @return Scoreboard
     */

    public final org.bukkit.scoreboard.Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    /**
     * public final String getID()
     * @return String
     */

    @NotNull
    @org.jetbrains.annotations.Contract(pure = true)
    public final String getID() {
        return this.ID;
    }

    /**
     * public final String getName()
     * @return String
     */

    @NotNull
    @org.jetbrains.annotations.Contract(pure = true)
    public final String getName() {
        return this.name;
    }

    /**
     * public void setDefaultTitle(@NotNull String defaultTitle)
     * @param defaultTitle String {@link String {@value name}}
     */

    public void setDefaultTitle(@NotNull String defaultTitle) {
        this.name = defaultTitle;
    }

    /**
     * public void addPlayer(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     */

    public void addPlayer(@NotNull org.bukkit.entity.Player player) {
        if(player.isOnline() == false) return;
        if(this.xorePlayers.containsKey(player)) return;

        final XorePlayer xorePlayer = new XorePlayer(this, player);
            player.setScoreboard(this.scoreboard);

        this.xorePlayers.put(player, xorePlayer);
    }

    /**
     * public void removePlayer(@NotNull org.bukkit.entity.Player player)
     * @param player Player {@link org.bukkit.entity.Player}
     */

    public void removePlayer(@NotNull org.bukkit.entity.Player player) {
        if(player.isOnline() == false) return;
        if(this.xorePlayers.containsKey(player)) {
            final XorePlayer xorePlayer = this.xorePlayers.get(player);
                if(xorePlayer.getPrivateSidebar().isShowed()) xorePlayer.getPrivateSidebar().hideSidebar();
                    if(xorePlayer.hasDisplayedSharedSidebar()) getSharedSidebar().hideSidebar(xorePlayer);

            this.xorePlayers.remove(player);
    }}

    /**
     * public XoreBoardSharedSidebar getSidebar()
     * @return XoreBoardSharedSidebar
     */

    public XoreBoardSharedSidebar getSharedSidebar() {
        if(this.sharedSidebar == null) this.sharedSidebar = new XoreBoardSharedSidebar(this);
        return ((XoreBoardSharedSidebar) this.sharedSidebar);
    }

    /**
     * public Collection<org.bukkit.entity.Player> getPlayers()
     * @return Collection<org.bukkit.entity.Player>
     */

    @org.jetbrains.annotations.Contract(pure = true)
    public Collection<org.bukkit.entity.Player> getPlayers() {
        return this.xorePlayers.keySet();
    }

    /**
     * public Collection<XorePlayer> getXorePlayers()
     * @return Collection<XorePlayer>
     */

    @org.jetbrains.annotations.Contract(pure = true)
    public Collection<XorePlayer> getXorePlayers() {
        return this.xorePlayers.values();
    }

    /**
     * public HashMap<org.bukkit.entity.Player, XorePlayer> getEntries()
     * @return HashMap<org.bukkit.entity.Player, XorePlayer>
     */

    @org.jetbrains.annotations.Contract(pure = true)
    public HashMap<org.bukkit.entity.Player, XorePlayer> getEntries() {
        return this.xorePlayers;
    }

    public void destroy() {
        getSharedSidebar().clearLines();
            getSharedSidebar().hideSidebar();
        java.util.List<org.bukkit.entity.Player> temporary = new ArrayList<org.bukkit.entity.Player>(getPlayers());
        temporary.forEach(this::removePlayer);
    }

    @Getter
    static class XoreBoardPackets {
        enum EnumScoreboardAction {

            CHANGE, REMOVE;

            /**
             * public Object toNamespace()
             * @return Object
             */

            public Object toNamespace() {
                try {
                    Method method = Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".PacketPlayOutScoreboardScore$EnumScoreboardAction").getDeclaredMethod("valueOf", String.class);
                    method.setAccessible(true);
                    return method.invoke(null, name());
                }
                catch(final @NotNull Exception ignored) {}
                return null;
        }}

        /**
         * static Object getPacket(@NotNull String packetName, Object... objects)
         * @param packetName String {@link String}
         * @param objects Object... {@link Object}
         * @return Object
         */

        static Object getPacket(@NotNull String packetName, Object... objects) {
            int objectIndex = 0;
            Object outputObject = null;
            try {
                outputObject = Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + "." + packetName).getConstructor().newInstance();
                for(@NotNull Field field : getDeclaredFields(Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + "." + packetName))) {
                    rewriteField(outputObject, field.getName(), objects[objectIndex]);
                    objectIndex++;
            }} catch(ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException ignored) {}
            return outputObject;
        }

        /**
         * static void sendPacket(@NotNull org.bukkit.entity.Player player, Object packet)
         * @param player org.bukkit.entity.Player {@link org.bukkit.entity.Player}
         * @param packet Object {@link Object}
         */

        static void sendPacket(@NotNull org.bukkit.entity.Player player, Object packet) {
            Object craftPlayer;
            try {
                craftPlayer = Class.forName("org.bukkit.craftbukkit." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".entity.CraftPlayer").cast(player);
                Object handle = getFieldInstance(craftPlayer, "entity");
                Object playerConnection = getFieldInstance(handle, "playerConnection");
                    invokeMethod(playerConnection, new Class[] {Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getPackage().getName().substring(org.bukkit.Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".Packet")}, packet);
            } catch (ClassNotFoundException ignored) {}
        }

        /**
         * private static void rewriteField(@NotNull Object packet, @NotNull String key, Object value)
         * @param packet Object {@link Object}
         * @param key String {@link String}
         * @param value Object {@link Object}
         */

        private static void rewriteField(@NotNull Object packet, @NotNull String key, Object value) {
            try {
                Field field = packet.getClass().getDeclaredField(key);
                    field.setAccessible(true);
                        field.set(packet, value);
            } catch(NoSuchFieldException | IllegalAccessException ignored) {}
        }

        /**
         * private static Object getFieldInstance(@NotNull Object instance, @NotNull String fieldName)
         * @param instance Object {@link Object}
         * @param fieldName String {@link String}
         * @return Object
         */

        private static Object getFieldInstance(@NotNull Object instance, @NotNull String fieldName) {
            try {
                Field field = getDeclaredField(getDeclaredFields(instance.getClass()), fieldName);
                    field.setAccessible(true);
                return field.get(instance);
            } catch(IllegalAccessException ignored) {}
            return null;
        }

        /**
         * private static void invokeMethod(@NotNull Object instance, @NotNull String methodName, Class<?>[] classes, Object... values)
         * @param instance Object {@link Object}
         * @param classes Class {@link Class}
         * @param values Object {@link Object}
         */

        private static void invokeMethod(@NotNull Object instance, Class<?>[] classes, Object... values) {
            try {
                Method method = instance.getClass().getDeclaredMethod("sendPacket", classes);
                    method.setAccessible(true);
                        method.invoke(instance, values);
            } catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {}
        }

        /**
         * private static List<Field> getDeclaredFields(@NotNull Class clazz)
         * @param clazz Class {@link Class}
         * @return List
         */

        private static List<Field> getDeclaredFields(@NotNull Class clazz) {
            List<Field> fieldList = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));
            if(clazz.getSuperclass() != null) fieldList.addAll(getDeclaredFields(clazz.getSuperclass()));
            return fieldList;
        }

        /**
         * private static Field getDeclaredField(List<Field> fields, @NotNull String fieldName)
         * @param fields List {@link List}
         * @param fieldName String {@link String}
         * @return Field
         */

        private static Field getDeclaredField(List<Field> fields, @NotNull String fieldName) {
            return fields.stream().filter(field -> field.getName().equalsIgnoreCase(fieldName)).findFirst().orElse(null);
}}}