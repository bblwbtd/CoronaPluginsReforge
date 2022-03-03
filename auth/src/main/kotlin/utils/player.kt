package utils

import Main
import handler.PlayerState
import org.bukkit.entity.Player

fun Player.isAuthenticated(): Boolean {
    return getString(Main.plugin, "state") == PlayerState.AUTHENTICATED.name
}