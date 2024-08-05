package xyz.ldgame.coronaauth.handler

import org.bukkit.entity.Player
import utils.setString
import xyz.ldgame.coronaauth.Main

enum class PlayerState {
    UNAUTHENTICATED,
    AUTHENTICATED;

    fun setState(player: Player) {
        player.setString(Main.plugin, "state", this.name)
    }
}