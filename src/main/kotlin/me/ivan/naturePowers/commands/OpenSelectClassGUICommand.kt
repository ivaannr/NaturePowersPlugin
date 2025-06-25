package me.ivan.naturePowers.commands

import me.ivan.naturePowers.NaturePowers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class OpenSelectClassGUICommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {

        if (sender !is Player) return false

        val inventory = Bukkit.createInventory(sender, 9, Component.text("Select your class!")
            .color(TextColor.color(20, 50, 50)))

        //nether
        val netherClassButton = ItemStack(Material.BLAZE_POWDER)
        val netherClassButtonMeta = netherClassButton.itemMeta

        netherClassButtonMeta
            .displayName(Component.text("Nether")
                .color(TextColor.color(255, 100, 0)))

        netherClassButton.itemMeta = netherClassButtonMeta

        //ocean
        val oceanClassButton = ItemStack(Material.HEART_OF_THE_SEA)
        val oceanClassButtonMeta = oceanClassButton.itemMeta

        oceanClassButtonMeta
            .displayName(Component.text("Ocean")
                .color(TextColor.color(0, 100, 255)))

        oceanClassButton.itemMeta = oceanClassButtonMeta

        //overworld
        val overworldClassButton = ItemStack(Material.GRASS_BLOCK)
        val overworldClassButtonMeta = overworldClassButton.itemMeta

        overworldClassButtonMeta
            .displayName(Component.text("Overworld")
                .color(TextColor.color(96, 56, 0)))

        overworldClassButton.itemMeta = overworldClassButtonMeta

        //sky
        val skyClassButton = ItemStack(Material.FEATHER)
        val skyClassButtonMeta = skyClassButton.itemMeta

        skyClassButtonMeta
            .displayName(Component.text("Sky")
                .color(TextColor.color(0, 255, 255)))

        skyClassButton.itemMeta = skyClassButtonMeta

        //end
        val endClassButton = ItemStack(Material.ENDER_EYE)
        val endClassButtonMeta = endClassButton.itemMeta

        endClassButtonMeta
            .displayName(Component.text("End")
                .color(TextColor.color(9, 59, 53)))

        endClassButton.itemMeta = endClassButtonMeta

        //wither
        val witherClassButton = ItemStack(Material.WITHER_SKELETON_SKULL)
        val witherClassButtonMeta = witherClassButton.itemMeta

        witherClassButtonMeta
            .displayName(Component.text("Wither")
                .color(TextColor.color(0, 0, 0)))

        witherClassButton.itemMeta = witherClassButtonMeta

        inventory.setItem(0, netherClassButton)
        inventory.setItem(2, oceanClassButton)
        inventory.setItem(3, witherClassButton)
        inventory.setItem(4, overworldClassButton)
        inventory.setItem(6, skyClassButton)
        inventory.setItem(8, endClassButton)

        NaturePowers.selectClassGUIMap[sender.uniqueId] = inventory

        sender.openInventory(inventory)

        return true
    }
}