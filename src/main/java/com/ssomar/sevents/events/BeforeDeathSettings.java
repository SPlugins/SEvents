package com.ssomar.sevents.events;

/**
 * Settings of the PlayerBeforeDeathEvent / EntityBeforeDeathEvent, given by the plugin that embeds SEvents (SCore config).
 */
public class BeforeDeathSettings {

    /* false (default): the damage is checked at the NORMAL priority, even when it is already cancelled.
     * true: checked at the HIGHEST priority, after the other listeners (a damage cap, a protected region...), and not when
     * one of them cancelled the damage: at NORMAL the result depended on which plugin registered its listener first. */
    private static volatile boolean ignoreCancelledDamage = false;

    public static boolean isIgnoreCancelledDamage() {
        return ignoreCancelledDamage;
    }

    public static void setIgnoreCancelledDamage(boolean ignoreCancelledDamage) {
        BeforeDeathSettings.ignoreCancelledDamage = ignoreCancelledDamage;
    }
}
