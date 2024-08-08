package xyz.ldgame.corona.auth.handler

import org.bukkit.entity.Player
import xyz.ldgame.corona.auth.Main
import xyz.ldgame.corona.common.utils.setString

enum class PlayerState {
    UNAUTHENTICATED,
    AUTHENTICATED;

    fun setState(player: Player) {
        player.setString(Main.plugin, "state", this.name)
    }
}