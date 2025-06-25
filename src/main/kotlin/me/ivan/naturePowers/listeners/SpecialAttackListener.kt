package me.ivan.naturePowers.listeners

import me.ivan.naturePowers.NaturePowers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.NamespacedKey
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.util.*


class SpecialAttackListener(private val plugin: JavaPlugin): Listener {


    @EventHandler
    fun specialAttackEvent(event: EntityShootBowEvent) {

        val entity = event.entity
        if (entity !is Player) return

        event.isCancelled = true

        val playerClass = NaturePowers.manager.getPlayerClass(entity.uniqueId.toString())?.lowercase(Locale.getDefault())
            ?: return

        plugin.logger.info("EntityShootBowEvent triggered by ${entity.name}")
        plugin.logger.info("Player class for ${entity.name}: $playerClass")

        val direction = entity.location.direction.normalize().multiply(12.0)
        val notArrowDirection = entity.location.direction.normalize()

        when (playerClass) {

            "nether" -> {

                val fireball = entity.launchProjectile(Fireball::class.java, notArrowDirection)
                fireball.shooter = entity

                object : BukkitRunnable() {
                    override fun run() {
                        val smallFireball = entity.launchProjectile(SmallFireball::class.java, notArrowDirection)
                        smallFireball.shooter = entity
                    }
                }.runTaskLater(plugin, 1L)

                object : BukkitRunnable() {
                    override fun run() {
                        val firework = entity.launchProjectile(Firework::class.java, notArrowDirection)
                        firework.shooter = entity
                    }
                }.runTaskLater(plugin, 2L)

            }
            "ocean" -> {

                val arrow = entity.world.spawnArrow(entity.eyeLocation, direction, 1.0f, 12.0f)
                arrow.addCustomEffect(PotionEffect(PotionEffectType.POISON, 10 * 20, 2), true)
                val trident = entity.launchProjectile(Trident::class.java, notArrowDirection.multiply(2.0))
                trident.isGlowing = true

                arrow.shooter = entity

                val key = NamespacedKey(plugin, "bow_shot_trident")
                trident.persistentDataContainer.set(key, PersistentDataType.INTEGER, 1)

                trident.shooter = entity

            }
            "overworld" -> {

                val damageArrow = entity.world.spawnArrow(entity.eyeLocation, direction, 1.0f, 12.0f)
                damageArrow.addCustomEffect(PotionEffect(PotionEffectType.INSTANT_DAMAGE, 7 * 20, 1), true)
                val slownessArrow = entity.world.spawnArrow(entity.eyeLocation, direction, 1.0f, 12.0f)
                slownessArrow.addCustomEffect(PotionEffect(PotionEffectType.SLOWNESS, 3 * 20, 255), true)

                damageArrow.shooter = entity
                slownessArrow.shooter = entity

            }
            "sky" -> {

                val levitatingArrow = entity.world.spawnArrow(entity.eyeLocation, direction, 1.0f, 12.0f)
                levitatingArrow.addCustomEffect(PotionEffect(PotionEffectType.LEVITATION, 2 * 20, 30), true)

                val key = NamespacedKey(plugin, "lightningSpecialArrow")
                levitatingArrow.persistentDataContainer.set(key, PersistentDataType.INTEGER, 1)

                levitatingArrow.shooter = entity
            }
            "end" -> {

                val dragonFireball = entity.launchProjectile(DragonFireball::class.java, notArrowDirection)
                dragonFireball.shooter = entity
            }

            "wither" -> {

                val witherSkull = entity.launchProjectile(WitherSkull::class.java, notArrowDirection.multiply(4.0))
                witherSkull.isGlowing = true
                witherSkull.isCharged = true
                val witherArrow = entity.world.spawnArrow(entity.eyeLocation, direction, 1.0f, 12.0f)
                witherArrow.addCustomEffect(PotionEffect(PotionEffectType.WITHER, 7 * 20, 1), true)

                witherSkull.shooter = entity
                witherArrow.shooter = entity

            }

        }
    }


    //Unused
    private fun lightningSpectralArrowEvent(event: EntityShootBowEvent) {

        val entity = event.entity

        if (entity !is Player) return

        val bow = event.bow ?: return

        if (!bow.hasItemMeta()) return

        val arrow = event.projectile
        if (arrow is Arrow) {
            arrow.persistentDataContainer.set(
                NamespacedKey(plugin, "lightningSpecialArrow"),
                PersistentDataType.INTEGER,
                1
            )
        }


    }


    @EventHandler
    fun onArrowHit(event: EntityDamageByEntityEvent) {

        val damager = event.damager
        val target = event.entity

        if (damager is AbstractArrow && target is Player) {

            val shooter = damager.shooter
            if (shooter is Player) {

                val key = NamespacedKey(plugin, "lightningSpecialArrow")
                val isSpecial = damager.persistentDataContainer.get(key, PersistentDataType.INTEGER)

                if (isSpecial == 1) {

                    target.world.strikeLightning(target.location)
                    shooter.sendMessage(
                        Component.text("You've shot a lightning arrow!")
                            .color(TextColor.color(255, 0, 0))
                            .decorate(TextDecoration.BOLD))
                    target.sendMessage(
                        Component.text("You've been hit by a lightning arrow!")
                            .color(TextColor.color(255, 255, 0))
                            .decorate(TextDecoration.BOLD))

                }

            }
        }
    }

    @EventHandler
    fun onTridentHitEvent(event: ProjectileHitEvent) {

        val projectile = event.entity

        if (projectile is Trident) {

            val key = NamespacedKey(plugin, "bow_shot_trident")
            val isCustom = projectile.persistentDataContainer.get(key, PersistentDataType.INTEGER)

            if (isCustom == 1) {
                projectile.remove()
            }
        }

    }


}