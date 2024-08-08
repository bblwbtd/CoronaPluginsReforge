package xyz.ldgame.corona.bot

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.CompletableDeferred
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import xyz.ldgame.corona.auth.listener.PlayerAuthEvent


class APIClient(private val host: String, private val port: Int) : Listener {
    private val client = HttpClient(CIO)
    private val botJoinDeferred = mutableMapOf<String, CompletableDeferred<Player>>()

    suspend fun addBot(serverHost: String, serverPort: Int, username: String): Bot {
        val deferred = CompletableDeferred<Player>()
        try {
            val response = client.post("http://$host:$port/add") {
                setBody {
                    "username" to username
                    "host" to serverHost
                    "port" to serverPort
                }
            }

            if (response.status.value != 200) {
                throw Exception("Failed to add bot")
            }

            val botPlayer = deferred.await()

            return Bot(botPlayer, this)
        } catch (e: Exception) {
            botJoinDeferred.remove(username)
            throw e
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBotJoin(event: PlayerJoinEvent) {
        if (!botJoinDeferred.contains(event.player.name)) {
            return
        }
        val username = event.player.name

        Bukkit.getPluginManager().callEvent(PlayerAuthEvent(event.player))
        botJoinDeferred[username]?.complete(event.player)
        botJoinDeferred.remove(username)
    }

    suspend fun removeBot(name: String) {
        val response = client.post("http://$host:$port/remove") {
            parameter("username", name)
        }

        if (response.status.value != 200) {
            throw Exception("Failed to remove bot")
        }
    }

    data class CommandResponse(val output: String?, val error: String?)

    suspend fun sendCommand(name: String, command: String): String {
        val response = client.post("http://$host:$port/command") {
            parameter("username", name)
            setBody {
                "command" to command
            }
        }

        val body = response.body<CommandResponse>()

        if (response.status.value != 200) {
            throw Exception("Failed to send command. Error: ${body.error}")
        }

        return body.output ?: ""
    }
}