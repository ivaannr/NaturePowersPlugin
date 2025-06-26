package me.ivan.naturePowers

import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.attribute.Attribute


class StatsManager(private val plugin: JavaPlugin) {

    fun setPlayerStats(player: Player) {
        val playerClass: String? = NaturePowers.manager.getPlayerClass(player.uniqueId.toString())

        when (playerClass) {
            "nether" -> {
                player.fireTicks = 0
                player.walkSpeed = 0.2f
                setMaxHealth(player, 20.0)
                player.isHealthScaled = true
                player.healthScale = 20.0
            }
            "ocean" -> {
                // Can breathe underwater
                player.walkSpeed = 0.2f
                setMaxHealth(player, 20.0)
                player.isHealthScaled = true
                player.healthScale = 20.0
            }
            "overworld" -> {
                setMaxHealth(player, 40.0)
                player.isHealthScaled = true
                player.healthScale = 40.0
                player.walkSpeed = 0.15f
            }
            "sky" -> {
                setMaxHealth(player, 18.0)
                player.isHealthScaled = true
                player.healthScale = 18.0
                player.walkSpeed = 0.25f
            }
            "end" -> {
                setMaxHealth(player, 25.0)
                player.healthScale = 25.0
                player.walkSpeed = 0.2f
            }
            "wither" -> {
                setMaxHealth(player, 15.0)
                player.isHealthScaled = true
                player.healthScale = 15.0
                player.walkSpeed = 0.35f
            }
            else -> {}
        }
    }

    private fun setMaxHealth(player: Player, amount: Double) {
        val attr = player.getAttribute(Attribute.MAX_HEALTH)
        attr?.baseValue = amount
        player.health = amount.coerceAtMost(attr?.baseValue ?: 20.0)
    }


}