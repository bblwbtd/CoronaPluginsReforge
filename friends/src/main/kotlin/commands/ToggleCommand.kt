package xyz.ldgame.corona.friends.commands

import org.bukkit.entity.Player
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.friends.toggleState

class ToggleCommand :
    MagicCommand(help = "Start making friends.") {
    override val invokeWithoutSubcommand: Boolean = true

    override fun run() {
        val subCommand = currentContext.invokedSubcommand
        if (subCommand == null) {
            val player = checkSenderType<Player>()
            toggleState(player)
        }
    }
}