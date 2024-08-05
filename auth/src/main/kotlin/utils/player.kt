package xyz.ldgame.coronaauth.utils

import org.bukkit.entity.Player
import utils.getString
import xyz.ldgame.coronaauth.Main
import xyz.ldgame.coronaauth.handler.PlayerState

fun Player.isAuthenticated(): Boolean {
    return getString(Main.plugin, "state") == PlayerState.AUTHENTICATED.name
}