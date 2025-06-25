package me.ivan.naturePowers.listeners

import me.ivan.naturePowers.NaturePowers
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileLaunchEvent

class SpecialSkillListener: Listener {

    @EventHandler
    fun useSpecialSkillEvent(event: ProjectileLaunchEvent) {

        val projectile = event.entity

        if (projectile is Snowball) {

            val shooter = projectile.shooter

            if (shooter is Player) {

                event.isCancelled = true

                val playerClass = NaturePowers.manager.getPlayerClass(shooter.uniqueId.toString())


                val replacement = when (playerClass?.lowercase()) {

                    "end" -> EnderPearl::class.java
                    "wither" -> WitherSkull::class.java
                    "nether" -> SmallFireball::class.java
                    "ocean" -> Trident::class.java
                    "sky" -> SpectralArrow::class.java
                    else -> null
                }

                if (replacement != null) {
                    event.isCancelled = true
                    shooter.launchProjectile(replacement, projectile.velocity)
                }

            }
        }
    }




}