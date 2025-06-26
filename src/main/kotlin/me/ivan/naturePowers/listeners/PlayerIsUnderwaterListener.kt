package me.ivan.naturePowers.listeners

import me.ivan.naturePowers.NaturePowers
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random

class PlayerIsUnderwaterListener: Listener {

    private val rd = Random.Default

    @EventHandler
    fun oceanClassTakesDamageUnderwater(event: EntityDamageEvent) {

        val entity = event.entity

        if (entity !is Player) return

        if (entity.isUnderWater && NaturePowers.manager
            .getPlayerClass(entity.uniqueId.toString()) == "ocean") {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun oceanClassSwimsUnderwater(event: PlayerMoveEvent) {

        val player = event.player

        if (NaturePowers.manager.getPlayerClass(player.uniqueId.toString()) != "ocean") return

        val block = player.location.block
        val isInWater = block.type.name.contains("WATER")

        if (isInWater) {
            player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 60, 1, true, false, false))
            player.addPotionEffect(PotionEffect(PotionEffectType.DOLPHINS_GRACE, 60, 0, true, false, false))
        } else {
            player.removePotionEffect(PotionEffectType.SPEED)
            player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE)
        }
    }


}