package commands

import command.MagicCommand
import handlers.toggleState
import org.bukkit.entity.Player

class ToggleCommand() :
    MagicCommand(invokeWithoutSubcommand = true, help = "Start making friends.") {
    override fun run() {
        val subCommand = currentContext.invokedSubcommand
        if (subCommand == null) {
            val player = checkSenderType<Player>()
            toggleState(player)
        }
    }
}