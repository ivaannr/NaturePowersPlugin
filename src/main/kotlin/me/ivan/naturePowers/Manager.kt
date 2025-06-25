package me.ivan.naturePowers

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class Manager(private val plugin: JavaPlugin) {

    private lateinit var classFile: File
    private lateinit var classConfig: FileConfiguration

    fun loadClasses() {
        classFile = File(plugin.dataFolder, "classes.yml")

        try {
            if (!classFile.exists()) {
                classFile.parentFile.mkdirs()
                classFile.createNewFile()
            }
            classConfig = YamlConfiguration.loadConfiguration(classFile)
        } catch (e: IOException) {
            plugin.logger.severe("It wasn't possible to load or create the file classes.yml")
            e.printStackTrace()
        }

        plugin.logger.info("Classes loaded successfully")
    }

    fun getPlayerClass(UUID: String): String? = classConfig.getString("$UUID.playerClass")

    fun setPlayerClass(UUID: String, playerClass: String) {
        classConfig.set("$UUID.playerClass", playerClass)
        classConfig.save(classFile)
    }

    fun getClases(): List<String> = classConfig.getStringList("classes")

    private fun getPlayersUUIDs(): List<String> = classConfig.getStringList("UUID")

    fun playerHasSelectedClass(sender: Player): Boolean {
        val uuid = sender.uniqueId.toString()

        if (NaturePowers.manager.getPlayersUUIDs().contains(uuid)) {
            sender.sendMessage(
                Component.text("You have already selected your class!")
                    .color(TextColor.color(20, 50, 50))
            )
            return true
        }

        return false
    }



}
