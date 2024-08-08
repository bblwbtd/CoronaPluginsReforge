package xyz.ldgame.corona.common

import xyz.ldgame.corona.common.i18n.saveAndLoadLanguageFiles
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import xyz.ldgame.corona.common.utils.info
import xyz.ldgame.corona.common.utils.warn
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

abstract class CommonMain : JavaPlugin() {
    companion object {
        lateinit var plugin: JavaPlugin
    }

    override fun onEnable() {
        plugin = this
        info("$name enabled.")
        saveDefaultConfig()
    }

    fun registerListeners(vararg listener: Listener) {
        listener.forEach {
            server.pluginManager.registerEvents(it, this)
        }
    }

    override fun onDisable() {
        warn("$name disabled.")
    }

}