import command.CommandCompleter
import commands.Executor
import listener.PlayerListener
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
        getCommand("auth")!!.apply {
            Executor().let {
                setExecutor(it)
                tabCompleter = CommandCompleter(it.getCommand())
            }
        }

        registerListeners()


    }

    private fun registerListeners() {
        server.pluginManager.run {
            registerEvents(PlayerListener(), plugin)
        }
    }

    override fun onDisable() {
        warn("CoronaAuth disabled")
    }
}


