package me.ivan.naturePowers.listeners

import me.ivan.naturePowers.NaturePowers
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent

class InventoryCloseListener: Listener {

    @EventHandler
    fun inventoryCloseEvent(event: InventoryCloseEvent) {

        val playerUUID = event.player.uniqueId

        if (NaturePowers.selectClassGUIMap.containsKey(playerUUID)) NaturePowers.selectClassGUIMap.remove(playerUUID)

    }
}