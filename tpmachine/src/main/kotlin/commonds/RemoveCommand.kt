package commonds

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handler.AddressHandler
import i18n.locale
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RemoveCommand(sender: CommandSender?) : MagicCommand(sender, help = "Remove an address") {
    private val addressName by argument(
        help = "The name of the address".locale(sender)
    )

    override fun run() {
        val player = checkSenderType<Player>()
        AddressHandler(player).removeAddress(addressName)
    }
}