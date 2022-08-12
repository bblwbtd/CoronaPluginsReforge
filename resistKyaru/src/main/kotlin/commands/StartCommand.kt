package commands

import command.InvalidSenderException
import command.MagicCommand
import handlers.startGame
import org.bukkit.command.CommandSender

class StartCommand(sender: CommandSender?) : MagicCommand(sender, help = "start the game.") {

    override fun run() {
        if (sender?.isOp != true) {
            throw InvalidSenderException()
        }

        startGame()
    }

}