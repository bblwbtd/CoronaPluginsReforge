import command.CommandCompleter
import commands.Executor
import database.connectDB
import database.db
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

        db = connectDB()
    }

    override fun onDisable() {
        warn("CoronaAuth disabled")
    }
}


