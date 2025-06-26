package me.ivan.naturePowers.listeners

import me.ivan.naturePowers.NaturePowers
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class PlayerIsDamagedListener: Listener {

    @EventHandler
    fun onPlayerDamagedByFireEvent(event: EntityDamageEvent) {

        val entity = event.entity

        if (entity !is Player) return

        val playerClass = NaturePowers.manager.getPlayerClass(entity.uniqueId.toString()) ?: return

        if (playerClass != "nether") return

        val cause = event.cause

        when (cause) {
            EntityDamageEvent.DamageCause.FIRE, EntityDamageEvent.DamageCause.FIRE_TICK, EntityDamageEvent.DamageCause.LAVA -> {
                event.damage = 0.0
                event.isCancelled = true
            }
            else -> {}
        }
    }

}