package xyz.ldgame.corona.common

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import xyz.ldgame.corona.common.i18n.saveAndLoadLanguageFiles
import xyz.ldgame.corona.common.utils.info
import xyz.ldgame.corona.common.utils.warn

abstract class CommonMain : JavaPlugin() {
    companion object {
        lateinit var plugin: JavaPlugin
    }

    override fun onEnable() {
        plugin = this
        info("$name enabled.")
        saveDefaultConfig()
        saveAndLoadLanguageFiles("lang/common")
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