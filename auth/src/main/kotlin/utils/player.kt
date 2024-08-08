package xyz.ldgame.corona.auth.utils

import org.bukkit.entity.Player
import xyz.ldgame.corona.auth.Main
import xyz.ldgame.corona.common.utils.getString
import xyz.ldgame.corona.auth.handler.PlayerState

fun Player.isAuthenticated(): Boolean {
    return getString(Main.plugin, "state") == PlayerState.AUTHENTICATED.name
}