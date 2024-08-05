package xyz.ldgame.coronaauth

import command.CommandCompleter
import org.bukkit.plugin.java.JavaPlugin
import utils.info
import utils.warn
import xyz.ldgame.coronaauth.commands.Executor
import xyz.ldgame.coronaauth.listener.PlayerListener

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
        saveDefaultConfig()
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


