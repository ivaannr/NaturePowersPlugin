package me.ivan.naturePowers.listeners

import me.ivan.naturePowers.NaturePowers
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable

class PlayerSetUpOnJoinListener {


    fun setPlayerStatsEvent(event: PlayerJoinEvent) {

        val player = event.player



        when (NaturePowers.manager.getPlayerClass(player.uniqueId.toString())) {


            "nether" -> {
                player.fireTicks = 0
            }
            "ocean" -> {
                //Can breathe underwater
            }
            "overworld" -> {
                player.walkSpeed = 0.15f
                player.healthScale = 40.0
                player.health = 40.0
            }
            "sky" -> {
                player.walkSpeed = 0.25f
                player.healthScale = 18.0
                player.health = 18.0
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