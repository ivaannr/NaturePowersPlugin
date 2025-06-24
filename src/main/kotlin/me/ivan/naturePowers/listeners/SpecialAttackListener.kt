package me.ivan.naturePowers.listeners

import me.ivan.naturePowers.NaturePowers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.NamespacedKey
import org.bukkit.entity.*;
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*


class SpecialAttackListener(private val plugin: JavaPlugin): Listener {


    fun specialAttackEvent(event: EntityShootBowEvent) {

        val projectiles: MutableList<Projectile> = mutableListOf()
        projectiles.clear()

        val entity = event.entity

        if (entity !is Player) return

        val playerUUID = entity.uniqueId

        val bow = event.bow ?: return

        if (!bow.hasItemMeta()) return

        event.isCancelled = true

        when (NaturePowers.manager.getPlayerClass(playerUUID
            .toString()).toString().lowercase(Locale.getDefault())) {

            "nether" -> {

                projectiles.add(entity.launchProjectile(Fireball::class.java, event.projectile.velocity))
                projectiles.add(entity.launchProjectile(SmallFireball::class.java, event.projectile.velocity))
                projectiles.add(entity.launchProjectile(Firework::class.java, event.projectile.velocity))

            }
            "ocean" -> {

                val arrow = entity.launchProjectile(TippedArrow::class.java, event.projectile.velocity)
                arrow.addCustomEffect(PotionEffect(PotionEffectType.POISON, 15, 2), true)

                val trident: Trident = entity.launchProjectile(Trident::class.java, event.projectile.velocity)
                trident.isGlowing = true

                projectiles.add(arrow)
                projectiles.add(trident)

            }
            "overworld" -> {

                val damageArrow = entity.launchProjectile(TippedArrow::class.java, event.projectile.velocity)
                damageArrow.addCustomEffect(PotionEffect(PotionEffectType.INSTANT_DAMAGE, 7, 1), true)
                val slownessArrow = entity.launchProjectile(TippedArrow::class.java, event.projectile.velocity)
                slownessArrow.addCustomEffect(PotionEffect(PotionEffectType.SLOWNESS, 3, 10), true)

                projectiles.add(damageArrow)
                projectiles.add(slownessArrow)

            }
            "sky" -> {

                lightningSpectralArrowEvent(event)

            }
            "end" -> projectiles.add(entity.launchProjectile(DragonFireball::class.java, event.projectile.velocity))

            "wither" -> {

                val witherSkull = entity.launchProjectile(WitherSkull::class.java, event.projectile.velocity)
                witherSkull.isGlowing = true
                witherSkull.isCharged = true
                val witherArrow = entity.launchProjectile(TippedArrow::class.java, event.projectile.velocity)
                witherArrow.addCustomEffect(PotionEffect(PotionEffectType.WITHER, 7, 1), true)

                projectiles.add(witherArrow)
                projectiles.add(witherSkull)

            }

        }

        projectiles.forEach { it.shooter = entity }

    }

    private fun lightningSpectralArrowEvent(event: EntityShootBowEvent) {

        val entity = event.entity

        if (entity !is Player) return

        val playerUUID = entity.uniqueId

        val bow = event.bow ?: return

        if (!bow.hasItemMeta()) return

        event.isCancelled = true

        val arrow = event.projectile
        if (arrow is Arrow) {
            arrow.persistentDataContainer.set(
                NamespacedKey(plugin, "lightningSpectralArrow"),
                PersistentDataType.INTEGER,
                1
            )
        }


    }


    fun onArrowHit(event: EntityDamageByEntityEvent) {

        val damager = event.damager
        val target = event.entity

        if (damager is Arrow && target is Player) {

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


}