package com.ssomar.sevents.events;

/**
 * Settings of the PlayerRightClickEvent, given by the plugin that embeds SEvents (SCore config).
 */
public class RightClickSettings {

    /* false (default): the right click is checked at the MONITOR priority, after the other plugins, so it sees what they
     * cancelled (crates, protections...).
     * true: checked at the LOWEST priority, before the other plugins: for plugins that change the item in hand on a right
     * click whatever the cancel state (ex. the PyroFarming grow station empties a water bucket), the activators still see
     * their item. The cancellations of the other plugins are then not seen by the activators. */
    private static volatile boolean beforeOtherPlugins = false;

    public static boolean isBeforeOtherPlugins() {
        return beforeOtherPlugins;
    }

    public static void setBeforeOtherPlugins(boolean beforeOtherPlugins) {
        RightClickSettings.beforeOtherPlugins = beforeOtherPlugins;
    }
}
