package me.ivan.naturePowers.listeners

import me.ivan.naturePowers.NaturePowers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryClickListener: Listener {

    @EventHandler
    fun inventoryClassSelectorClickEvent(event: InventoryClickEvent) {

        val player = event.whoClicked as? Player ?: return
        val playerUUID = player.uniqueId

        if (event.inventory == NaturePowers.selectClassGUIMap[playerUUID]) {

            val playerClass = when (event.currentItem?.type) {
                Material.BLAZE_POWDER -> "nether"
                Material.HEART_OF_THE_SEA -> "ocean"
                Material.GRASS_BLOCK -> "overworld"
                Material.FEATHER -> "sky"
                Material.ENDER_EYE -> "end"
                Material.WITHER_SKELETON_SKULL -> "wither"
                else -> return
            }

            NaturePowers.manager.setPlayerClass(playerUUID.toString(), playerClass)

            player.sendMessage(
                Component
                    .text("Has elegido la clase: $playerClass")
                    .color(TextColor.color(0, 255, 0))
            )

            event.inventory.close()
            event.isCancelled = true
        }
    }

}