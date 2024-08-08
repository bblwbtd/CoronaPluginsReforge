package xyz.ldgame.corona.tpmachine

import xyz.ldgame.corona.common.command.CommandCompleter
import xyz.ldgame.corona.tpmachine.commands.Executor
import org.bukkit.plugin.java.JavaPlugin
import xyz.ldgame.corona.common.utils.info

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