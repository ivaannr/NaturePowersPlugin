package me.ivan.naturePowers

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
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
}
