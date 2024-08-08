package xyz.ldgame.corona.bot

import org.bukkit.entity.Player

class Bot(val player: Player, val client: APIClient) {

    suspend fun attack(vararg targets: String) {
        client.sendCommand(player.name, "attack ${targets.joinToString(" ")}")
    }

    suspend fun protect(target: String) {
        client.sendCommand(player.name, "protect $target")
    }



}