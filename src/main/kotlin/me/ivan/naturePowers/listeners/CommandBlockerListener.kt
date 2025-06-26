package me.ivan.naturePowers.listeners

import me.ivan.naturePowers.NaturePowers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class CommandBlockerListener: Listener {

    @EventHandler
    fun onCommandPreprocess(event: PlayerCommandPreprocessEvent) {
        val player = event.player
        val playerUUID = player.uniqueId.toString()
        val message = event.message.lowercase()
        val args = message.split(" ")

        if (message.startsWith("/chooseclass")) {

            if (NaturePowers.manager.playerHasClass(playerUUID)) {

                val hasBypass = args.size > 1 && args[1] == "bypass"

                if (hasBypass && player.isOp) {
                    return
                }

                event.isCancelled = true
                player.sendMessage(
                    Component.text("You've already chosen your class!")
                        .color(TextColor.color(255, 0, 0))
                )
            }
        }
    }


}