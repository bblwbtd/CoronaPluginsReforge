package commands

import command.MagicCommand
import handlers.toggleState
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class FriendCommand(sender: CommandSender?) : MagicCommand(sender) {
    override fun run() {
        val player = checkSenderType<Player>()
        toggleState(player)
    }
}