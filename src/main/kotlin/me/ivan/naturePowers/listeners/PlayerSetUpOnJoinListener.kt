package me.ivan.naturePowers.listeners

import me.ivan.naturePowers.NaturePowers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerSetUpOnJoinListener: Listener {


    fun onPlayerJoinEvent (event: PlayerJoinEvent) {

        val player = event.player
        val playerClass: String? = NaturePowers.manager.getPlayerClass(player.uniqueId.toString())
        val classesList = NaturePowers.manager.getClases()

        if (!classesList.contains(playerClass)) {

            player.sendMessage(
                Component.text("You must select a class!")
                    .color(TextColor.color(255, 0, 0))
                    .decorate(TextDecoration.BOLD))

            player.sendMessage(
                Component.text("Type '/chooseClass' in order to select a class.")
                    .color(TextColor.color(255, 0, 0))
                    .decorate(TextDecoration.BOLD))

            return
        }

        setPlayerStats(player)
    }


    private fun setPlayerStats(player: Player) {

        val playerClass: String? = NaturePowers.manager.getPlayerClass(player.uniqueId.toString())

        when (playerClass) {

            "nether" -> {
                player.fireTicks = 0
            }
            "ocean" -> {
                //Can breathe underwater
            }
            "overworld" -> {
                player.isHealthScaled = true
                player.healthScale = 40.0
                player.health = 40.0
                player.walkSpeed = 0.15f
            }
            "sky" -> {
                player.isHealthScaled = true
                player.healthScale = 18.0
                player.health = 18.0
                player.walkSpeed = 0.25f
                //Has no fall damage
            }
            "end" -> {
                player.healthScale = 25.0
                player.health = 25.0
                //Has no fall damage
            }
            "wither" -> {
                player.isHealthScaled = true
                player.healthScale = 15.0
                player.health = 15.0
                player.walkSpeed = 0.35f
            }

        }

    }


}