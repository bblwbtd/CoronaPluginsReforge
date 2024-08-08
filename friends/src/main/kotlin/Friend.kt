package xyz.ldgame.corona.friends

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bukkit.Bukkit
import org.bukkit.entity.Player

data class Friend(val name: String, val createdAt: Long) {

    fun toPlayer(): Player? {
        return Bukkit.getPlayer(name)
    }

    @JsonIgnore
    fun isOnline(): Boolean {
        return toPlayer()?.isOnline ?: false
    }
}
