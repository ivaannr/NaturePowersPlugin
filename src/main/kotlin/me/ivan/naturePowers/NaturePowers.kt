package me.ivan.naturePowers

import me.ivan.naturePowers.listeners.SpecialAttackListener
import org.bukkit.event.EventHandler
import org.bukkit.plugin.java.JavaPlugin



class NaturePowers : JavaPlugin() {

    companion object {
        val manager = Manager()
    }

    override fun onEnable() {
        // Plugin startup logic

        logger.info("Starting Nature Powers...")

        manager.loadClasses()

    }

    @EventHandler
    private fun registerListener() {

        server.pluginManager.registerEvents(SpecialAttackListener(this), this)


    }

    @EventHandler
    private fun registerCommands() {


    }



    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("Shutting down...")
    }
}
