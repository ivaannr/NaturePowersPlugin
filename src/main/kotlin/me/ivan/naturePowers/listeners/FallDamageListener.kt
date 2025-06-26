package me.ivan.naturePowers.listeners

import me.ivan.naturePowers.NaturePowers
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class FallDamageListener: Listener {

    @EventHandler
    fun onFallDamageEvent(event: EntityDamageEvent) {

        val entity = event.entity

        if (entity is Player && (
            event.cause == EntityDamageEvent.DamageCause.FALL
            )) {

            val clazz = NaturePowers.manager.getPlayerClass(entity.uniqueId.toString())

            when (clazz) {

                "sky" -> event.isCancelled = true
                "overworld" -> event.damage /= 2

            }

        }
    }

}