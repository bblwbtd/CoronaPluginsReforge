import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import utils.info
import utils.warn

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