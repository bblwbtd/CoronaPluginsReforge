import command.CommandCompleter
import commonds.Executor
import org.bukkit.plugin.java.JavaPlugin
import utils.info

class Main : JavaPlugin() {
    companion object {
        @JvmStatic
        lateinit var plugin: JavaPlugin
    }

    override fun onEnable() {
        plugin = this

        getCommand("tpm")!!.apply {
            val executor = Executor()
            setExecutor(executor)
            tabCompleter = CommandCompleter(executor.getCommand())
        }

        server.pluginManager.run {
            registerEvents(EventListener(), plugin)
        }

        saveDefaultConfig()

        info("TP Machine enabled")
    }

    override fun onDisable() {
        info("TP Machine disabled")
    }
}