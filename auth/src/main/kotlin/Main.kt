import command.CommandCompleter
import commands.AuthCommand
import commands.Executor
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
    }

    override fun onDisable() {
        warn("CoronaAuth disabled")
    }
}


