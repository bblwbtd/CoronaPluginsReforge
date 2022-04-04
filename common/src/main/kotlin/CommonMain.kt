import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

abstract class CommonMain : JavaPlugin() {
    companion object {
        lateinit var plugin: JavaPlugin
    }

    override fun onEnable() {
        plugin = this
        saveDefaultConfig()
    }

    fun registerListeners(vararg listener: Listener) {
        listener.forEach {
            server.pluginManager.registerEvents(it, this)
        }
    }
}