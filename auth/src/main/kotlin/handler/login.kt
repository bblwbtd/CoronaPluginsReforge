package handler

import Main
import org.bukkit.Bukkit


fun login(username: String, password: String) {
    Bukkit.getScheduler().runTaskAsynchronously(Main.plugin) { _ ->
        val player = Bukkit.getPlayer(username)


    }
}