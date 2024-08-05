package xyz.ldgame.bot

import CommonMain

object BotMain : CommonMain() {
    private lateinit var client: APIClient

    override fun onEnable() {
        super.onEnable()

        saveDefaultConfig()

        val port = config.getInt("port")
        val host = config.getString("host")

        if (host == null) {
            logger.severe("Host not found in config")
            server.pluginManager.disablePlugin(this)
            return
        }

        if (port == 0) {
            logger.severe("Port not found in config")
            server.pluginManager.disablePlugin(this)
            return
        }

        client = APIClient(host, port)

    }
}