package me.ivan.naturePowers.listeners

import me.ivan.naturePowers.NaturePowers
import me.ivan.naturePowers.StatsManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.entity.Firework
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class PlayerSetUpOnJoinListener(private val plugin: JavaPlugin): Listener {

    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {

        val player = event.player
        val playerUUID = player.uniqueId.toString()
        val hasClass = NaturePowers.manager.playerHasClass(playerUUID)

        if (!hasClass) {
            player.sendMessage(
                Component.text("You must select a class!")
                    .color(TextColor.color(255, 0, 0))
                    .decorate(TextDecoration.BOLD)
            )

            player.sendMessage(
                Component.text("Type '/chooseClass' in order to select a class.")
                    .color(TextColor.color(255, 0, 0))
                    .decorate(TextDecoration.BOLD)
            )
            return
        }

        NaturePowers.statsManager.setPlayerStats(player)

    }





}