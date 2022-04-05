package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import command.MagicCommand
import handlers.RelationHandler
import i18n.locale
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AcceptCommand(sender: CommandSender?) : MagicCommand(sender) {
    private val playerName by argument("player name".locale(sender)).default("")

    override fun run() {
        val player = checkSenderType<Player>()
        val handler = RelationHandler(player)
        handler.acceptInvitation(Bukkit.getPlayer(playerName))
    }
}