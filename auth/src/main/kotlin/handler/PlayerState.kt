package handler

import Main
import org.bukkit.entity.Player
import utils.setString

enum class PlayerState {
    UNAUTHENTICATED,
    AUTHENTICATED;

    fun setState(player: Player) {
        player.setString(Main.plugin, "state", this.name)
    }
}