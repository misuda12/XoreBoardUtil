package net.minecord.xoreboardutil.bukkit;

import lombok.Getter;

@Getter
public interface Sidebar {

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

    java.util.HashMap<String, Integer> getLines();

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
     * void setShowedSidebar(boolean statement)
     * @param statement boolean {@link Boolean}
     */

    void setShowedSidebar(boolean statement);

}