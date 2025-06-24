package me.ivan.naturePowers

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class Manager: JavaPlugin() {

    private lateinit var classFile: File
    private lateinit var classConfig: FileConfiguration

    fun loadClasses() {

        classFile = File(dataFolder, "classes.yml")

        try {
            if (!classFile.exists()) {
                classFile.parentFile.mkdirs()
                classFile.createNewFile()
            }
            classConfig = YamlConfiguration.loadConfiguration(classFile)
        } catch (e: IOException) {
            logger.severe("It wasn't posible to load or create the file classes.yml")
            e.printStackTrace()
        }

        logger.info("Classes loaded successfully")
    }

    fun getPlayerClass(UUID: String): String? = classConfig.getString("$UUID.playerClass")

    fun setPlayerClass(UUID: String, playerClass: String) {
        classConfig.set(UUID, playerClass)
        classConfig.save(classFile)
    }




}