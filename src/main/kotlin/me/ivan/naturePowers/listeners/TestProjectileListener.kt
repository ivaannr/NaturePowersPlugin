package me.ivan.naturePowers.listeners

import org.bukkit.entity.Fireball
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.plugin.java.JavaPlugin

class TestProjectileListener(private val plugin: JavaPlugin): Listener {

    @EventHandler
    fun onShoot(event: EntityShootBowEvent) {
        val entity = event.entity
        if (entity !is Player) return

        plugin.logger.info("DEBUG: ${entity.name} disparó arco. Cancelando disparo original...")

        event.isCancelled = true

        val direction = entity.location.direction.multiply(2.0)

        plugin.logger.info("DEBUG: Lanzando bola de fuego")

        val fireball = entity.launchProjectile(Fireball::class.java, direction)

        fireball.shooter = entity

        plugin.logger.info("DEBUG: Bola de fuego lanzada!")
    }

}