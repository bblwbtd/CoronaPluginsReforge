package xyz.ldgame.bot

import CommonMain
import java.io.File

object BotMain : CommonMain() {
    private lateinit var client: APIClient

    override fun onEnable() {
        super.onEnable()

        saveAndLoadLanguageFiles()

        val port = config.getInt("port")
        val host = config.getString("host")

        if (host == null || port == 0) {
            logger.severe("Invalid host or port in the config")
            server.pluginManager.disablePlugin(this)
            return
        }


        client = APIClient(host, port)
    }


}