package commands

import RelationHandler
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import command.MagicCommand
import i18n.locale
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DeclineCommand(sender: CommandSender?) : MagicCommand(sender) {
    private val playerName by argument("player name".locale(sender)).default("")

    override fun run() {
        val player = checkSenderType<Player>()
        val handler = RelationHandler(player)
        handler.declineInvitation(Bukkit.getPlayer(playerName))
    }
}