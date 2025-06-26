package me.ivan.naturePowers

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class Manager(private val plugin: JavaPlugin) {

    private lateinit var classFile: File
    private lateinit var classConfig: YamlConfiguration

    fun loadClasses() {
        classFile = File(plugin.dataFolder, "playerData.yml")

        try {
            if (!classFile.exists()) {
                classFile.parentFile.mkdirs()
                classFile.createNewFile()
            }
            classConfig = YamlConfiguration.loadConfiguration(classFile)
        } catch (e: IOException) {
            plugin.logger.severe("It wasn't possible to load or create the file playerData.yml")
            e.printStackTrace()
        }

        plugin.logger.info("Classes loaded successfully")
    }

    fun getPlayerClass(UUID: String): String? =
        classConfig.getString("$UUID.playerClass")

    fun setPlayerClass(UUID: String, playerClass: String) {
        classConfig.set("$UUID.playerClass", playerClass)

        val uuidList = classConfig.getStringList("UUID")
        if (!uuidList.contains(UUID)) {
            uuidList.add(UUID)
            classConfig.set("UUID", uuidList)
        }

        try {
            classConfig.save(classFile)
        } catch (e: IOException) {
            plugin.logger.severe("Could not save player class for $UUID")
            e.printStackTrace()
        }
    }

    fun getClases(): List<String> = classConfig.getStringList("classes")

    /**
     * For debugging purposes
     */
    fun debugLogAllPlayerClasses() {
        for (uuid in classConfig.getKeys(false)) {
            val section = classConfig.getConfigurationSection(uuid)
            val playerClass = section?.getString("playerClass")

            if (playerClass != null) {
                plugin.logger.info("UUID: $uuid | Clase: $playerClass")
            } else {
                plugin.logger.warning("UUID: $uuid no tiene clase asignada.")
            }
        }
    }

    fun playerHasClass(uuid: String): Boolean {
        return classConfig.contains("$uuid.playerClass") && !classConfig.getString("$uuid.playerClass").isNullOrEmpty()
    }

    fun cleanPlayerData() {
        val keysToRemove = mutableListOf<String>()

        for (key in classConfig.getKeys(false)) {
            val section = classConfig.getConfigurationSection(key)
            val playerClass = section?.getString("playerClass")

            if (playerClass.isNullOrEmpty()) {
                keysToRemove.add(key)
            }
        }

        for (key in keysToRemove) {
            classConfig.set(key, null)
            plugin.logger.info("Invalid key: $key")
        }

        try {
            classConfig.save(classFile)
            plugin.logger.info("File: playerData.yml cleaned and saved successfully")
        } catch (e: IOException) {
            plugin.logger.severe("Error, saving playerData.yml after cleaning")
            e.printStackTrace()
        }
    }
}
