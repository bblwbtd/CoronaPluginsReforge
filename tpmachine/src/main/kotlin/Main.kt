import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        lateinit var plugin: JavaPlugin
    }

    override fun onEnable() {
        plugin = this

        server.pluginManager.run {
            registerEvents(EventListener(), plugin)
        }

        saveDefaultConfig()
    }
}