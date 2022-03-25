package commonds

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handler.AddressHandler
import i18n.locale
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RenameCommand(sender: CommandSender?) : MagicCommand(help = "Rename an address.", sender = sender) {
    private val oldName by argument(help = "The old address name".locale(sender))
    private val newName by argument(help = "The new address name".locale(sender))

    override fun run() {
        val player = checkSenderType<Player>()

        AddressHandler(player).apply {
            renamePlayerLocation(oldName, newName)
        }
    }
}