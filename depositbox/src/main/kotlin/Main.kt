import command.CommandCompleter
import commands.Executor
import listener.KeyListListener
import org.bukkit.plugin.java.JavaPlugin
import utils.info
import utils.warn
import viewRender.MagicViewListener

class Main: JavaPlugin() {
    companion object {
        lateinit var plugin: JavaPlugin
    }

    override fun onEnable() {
        info("CoronaDepositBox enabled")
        plugin = this
        getCommand("box")!!.apply {
            Executor().let {
                setExecutor(it)
                tabCompleter = CommandCompleter(it.getCommand())
            }
        }

        saveConfig()
        registerListeners()
    }

    private fun registerListeners() {
        server.pluginManager.run {
            registerEvents(MagicViewListener(), plugin)
            registerEvents(KeyListListener(), plugin)
        }
    }

    override fun onDisable() {
        warn("CoronaDepositBox disabled")
    }

}