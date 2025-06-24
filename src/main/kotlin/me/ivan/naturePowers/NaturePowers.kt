package me.ivan.naturePowers

import me.ivan.naturePowers.listeners.FallDamageListener
import me.ivan.naturePowers.listeners.PlayerIsUnderwaterListener
import me.ivan.naturePowers.listeners.SpecialAttackListener
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class NaturePowers : JavaPlugin() {

    companion object {
        val manager = Manager()
    }

    override fun onEnable() {
        // Plugin startup logic

        logger.info("Starting Nature Powers...")

        UnderWaterBreathingTask().runTaskTimer(this, 0L, 20L)

        registerListener()
        registerCommands()

        manager.loadClasses()

    }

    @EventHandler
    private fun registerListener() {

        server.pluginManager.registerEvents(SpecialAttackListener(this), this)

        server.pluginManager.registerEvents(FallDamageListener(), this)

        server.pluginManager.registerEvents(PlayerIsUnderwaterListener(), this)

        logger.info("Listeners registered!")

    }

    @EventHandler
    private fun registerCommands() {

        logger.info("Commands registered!")
    }



    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("Shutting down...")
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
