package listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListener : Listener {

    @EventHandler
    fun savePlayerData(event: PlayerJoinEvent) {
        event.player.apply {
            player?.saveData()

        }
    }

}