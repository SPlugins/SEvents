package com.ssomar.sevents.events.player.beforedeath;

import com.ssomar.sevents.events.BeforeDeathSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerBeforeDeathListener implements Listener {

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if (BeforeDeathSettings.isIgnoreCancelledDamage()) return;
        check(e);
    }

    /* BeforeDeathSettings.ignoreCancelledDamage: after the other listeners, never for a damage they cancelled */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageEventAfterOthers(EntityDamageEvent e) {
        if (!BeforeDeathSettings.isIgnoreCancelledDamage()) return;
        check(e);
    }

    private void check(EntityDamageEvent e) {

        if(!(e.getEntity() instanceof Player)) return;

        Player p = (Player)e.getEntity();

        if (p.getHealth() <= e.getFinalDamage()) {
            PlayerBeforeDeathEvent playerBeforeDeathEvent = new PlayerBeforeDeathEvent(p);
            playerBeforeDeathEvent.setCancelled(e.isCancelled());
            Bukkit.getServer().getPluginManager().callEvent(playerBeforeDeathEvent);
            if (playerBeforeDeathEvent.isCancelled()) {
                e.setCancelled(true);
            };
        }
    }
}
