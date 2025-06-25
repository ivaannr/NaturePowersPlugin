package me.ivan.naturePowers.listeners

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.plugin.java.JavaPlugin

class DebugShootListener(private val plugin: JavaPlugin): Listener {

    @EventHandler
    fun onShoot(event: EntityShootBowEvent) {
        val shooter = event.entity
        if (shooter is Player) {
            plugin.logger.info("DEBUG: Player ${shooter.name} disparó un arco.")
        }
    }

}