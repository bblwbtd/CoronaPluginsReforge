package commands

import RelationHandler
import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import i18n.locale
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddCommand(sender: CommandSender?) : MagicCommand(sender) {
    private val playerName by argument("player name".locale(sender))

    override fun run() {
        val player = checkSenderType<Player>()
        val handler = RelationHandler(player)
        handler.sendInvitation(playerName)
    }
}