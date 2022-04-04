import org.bukkit.plugin.java.JavaPlugin

abstract class CommonMain : JavaPlugin() {
    companion object {
        lateinit var plugin: JavaPlugin
    }

    override fun onEnable() {
        plugin = this
    }

}