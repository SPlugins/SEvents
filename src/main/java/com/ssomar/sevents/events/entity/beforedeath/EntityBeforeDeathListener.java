package com.ssomar.sevents.events.entity.beforedeath;

import com.ssomar.sevents.events.BeforeDeathSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityBeforeDeathListener implements Listener {

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
        //System.out.println("EntityBeforeDeathListener.onEntityDamageEvent");

        if((e.getEntity() instanceof Player) || !(e.getEntity() instanceof LivingEntity)) return;

        //System.out.println("EntityBeforeDeathListener.onEntityDamageEvent: e.getEntity() instanceof LivingEntity");

        LivingEntity entity = (LivingEntity) e.getEntity();

        if (entity.getHealth() <= e.getFinalDamage()) {
            EntityBeforeDeathEvent entityBeforeDeathEvent = new EntityBeforeDeathEvent(entity);
            entityBeforeDeathEvent.setCancelled(e.isCancelled());
            Bukkit.getServer().getPluginManager().callEvent(entityBeforeDeathEvent);
            //System.out.println("EntityBeforeDeathListener.onEntityDamageEvent: entityBeforeDeathEvent.isCancelled() = " + entityBeforeDeathEvent.isCancelled());
            if (entityBeforeDeathEvent.isCancelled()) {
                e.setCancelled(true);
            };
        }
    }
}
