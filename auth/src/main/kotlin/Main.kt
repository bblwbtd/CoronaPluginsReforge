package xyz.ldgame.corona.auth

import xyz.ldgame.corona.common.command.CommandCompleter
import org.bukkit.plugin.java.JavaPlugin
import xyz.ldgame.corona.common.utils.info
import xyz.ldgame.corona.common.utils.warn
import xyz.ldgame.corona.auth.commands.Executor
import xyz.ldgame.corona.auth.listener.PlayerListener

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


