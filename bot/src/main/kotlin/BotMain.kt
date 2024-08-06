package xyz.ldgame.bot

import CommonMain

object BotMain : CommonMain() {
    private lateinit var client: APIClient

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

    }
}