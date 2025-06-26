package me.ivan.naturePowers

import me.ivan.naturePowers.commands.OpenSelectClassGUICommand
import me.ivan.naturePowers.listeners.*
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class NaturePowers : JavaPlugin() {



    companion object {
        lateinit var instance: NaturePowers
            private set

        lateinit var manager: Manager
        
        val selectClassGUIMap: MutableMap<UUID, Inventory> = mutableMapOf()
    }

    override fun onEnable() {
        // Plugin startup logic

        logger.info("Starting Nature Powers...")

        instance = this
        manager = Manager(this)
        manager.loadClasses()

        UnderWaterBreathingTask().runTaskTimer(this, 0L, 20L)

        registerListener()
        registerCommands()

    }

    @EventHandler
    private fun registerListener() {

        server.pluginManager.registerEvents(SpecialAttackListener(this), this)

        server.pluginManager.registerEvents(FallDamageListener(), this)

        server.pluginManager.registerEvents(PlayerIsUnderwaterListener(), this)

        server.pluginManager.registerEvents(PlayerSetUpOnJoinListener(), this)

        server.pluginManager.registerEvents(SpecialSkillListener(), this)

        server.pluginManager.registerEvents(InventoryClickListener(), this)

        server.pluginManager.registerEvents(InventoryCloseListener(), this)

        server.pluginManager.registerEvents(CommandBlockerListener(), this)

        //server.pluginManager.registerEvents(DebugShootListener(this), this)

        //server.pluginManager.registerEvents(TestProjectileListener(this), this)

        logger.info("Listeners registered!")

    }

    @EventHandler
    private fun registerCommands() {

        getCommand("chooseClass")?.setExecutor(OpenSelectClassGUICommand())

        logger.info("Commands registered!")
    }



    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("Shutting down...")

        logger.severe("=== playerData.yml CONTENT ===")

        manager.debugLogAllPlayerClasses()

        manager.cleanPlayerData()

        manager.debugLogAllPlayerClasses()

    }



}

class UnderWaterBreathingTask : BukkitRunnable() {

    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            if (NaturePowers.manager.getPlayerClass(player.uniqueId.toString()) == "ocean") {
                player.remainingAir = player.maximumAir
            }
        }
    }
}
