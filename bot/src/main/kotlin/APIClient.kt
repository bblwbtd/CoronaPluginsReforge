package xyz.ldgame.bot

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import xyz.ldgame.coronaauth.listener.PlayerAuthEvent


class APIClient(private val host: String, private val port: Int) : Listener {
    private val client = HttpClient(CIO)
    private val pendingBot = HashSet<String>()

    suspend fun addBot(serverHost: String, serverPort: Int, username: String) {
        pendingBot.add(username)

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
        } catch (e: Exception) {
            pendingBot.remove(username)
            throw e
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBotJoin(event: PlayerJoinEvent) {
        if (!pendingBot.contains(event.player.name)) {
            return
        }

        Bukkit.getScheduler().runTaskLater(BotMain, Runnable {
            pendingBot.remove(event.player.name)
            Bukkit.getPluginManager().callEvent(PlayerAuthEvent(event.player))
        }, 20L)
    }


}