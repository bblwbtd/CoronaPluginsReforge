package commands

import command.InvalidSenderException
import command.MagicCommand
import handlers.endGame
import org.bukkit.command.CommandSender

class StopCommand(sender: CommandSender?): MagicCommand(sender, help = "Stop the game.") {

    override fun run() {
        if (sender?.isOp != true) {
            throw InvalidSenderException()
        }

        endGame()
    }
}