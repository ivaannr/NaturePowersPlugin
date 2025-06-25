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

        if (event.view.title != "Select your class!") return

        val classGui = NaturePowers.selectClassGUIMap[playerUUID] ?: return

        if (event.clickedInventory != classGui) return

        event.isCancelled = true

        val selectedItem = event.currentItem ?: return

        val playerClass = when (selectedItem.type) {
            Material.BLAZE_POWDER -> "nether"
            Material.HEART_OF_THE_SEA -> "ocean"
            Material.GRASS_BLOCK -> "overworld"
            Material.FEATHER -> "sky"
            Material.ENDER_EYE -> "end"
            Material.WITHER_SKELETON_SKULL -> "wither"
            else -> return
        }

        NaturePowers.manager.setPlayerClass(playerUUID.toString(), playerClass)

        player.sendMessage(Component
            .text("Has elegido la clase: $playerClass")
            .color(TextColor.color(0, 255, 0)))


        player.closeInventory()
    }

}