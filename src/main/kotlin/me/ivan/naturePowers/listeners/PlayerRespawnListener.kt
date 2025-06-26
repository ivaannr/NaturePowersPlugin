package me.ivan.naturePowers.listeners

import me.ivan.naturePowers.NaturePowers
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class PlayerRespawnListener: Listener {

    @EventHandler
    fun onPlayerRespawnEvent(event: PlayerRespawnEvent) {

        val player = event.player
        NaturePowers.statsManager.setPlayerStats(player)
    }

}