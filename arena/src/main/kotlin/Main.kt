import command.CommandCompleter
import org.bukkit.plugin.java.JavaPlugin
import utils.info
import utils.warn

class Main : JavaPlugin() {
    companion object {
        lateinit var plugin: JavaPlugin
    }

    override fun onEnable() {
        info("CoronaAuth enabled")
        plugin = this

        registerListeners()
        saveDefaultConfig()
    }

    private fun registerListeners() {
//        server.pluginManager.registerEvents()
    }

    override fun onDisable() {
        warn("CoronaAuth disabled")
    }
}