package xyz.ldgame.corona.bot

import xyz.ldgame.corona.bot.commands.Executor
import xyz.ldgame.corona.common.CommonMain
import xyz.ldgame.corona.common.command.CommandCompleter
import xyz.ldgame.corona.common.i18n.saveAndLoadLanguageFiles

class BotMain : CommonMain() {

    companion object {
        lateinit var client: APIClient
    }

    override fun onEnable() {
        super.onEnable()

        val port = config.getInt("port")
        val host = config.getString("host")

        if (host == null || port == 0) {
            logger.severe("Invalid host or port in the config")
            server.pluginManager.disablePlugin(this)
            return
        }

        client = APIClient(host, port)

        getCommand("bot")!!.apply {
            val executor = Executor()
            setExecutor(executor)
            tabCompleter = CommandCompleter(executor.getCommand())
        }

        saveAndLoadLanguageFiles("lang/bot")
    }
}