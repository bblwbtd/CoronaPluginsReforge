package xyz.ldgame.corona.bot

data class Bot(
    val name: String,
) {

    private val client: APIClient by lazy {
        BotMain.client
    }

    suspend fun attack(vararg targets: String) {
        client.sendCommand(name, "attack ${targets.joinToString(" ")}")
    }

    suspend fun protect(target: String) {
        client.sendCommand(name, "protect $target")
    }


}