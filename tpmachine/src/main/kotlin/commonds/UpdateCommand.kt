package commonds

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handler.AddressHandler
import i18n.locale
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class UpdateCommand(sender: CommandSender?) : MagicCommand(sender, "Update an address.") {
    private val addressName by argument(help = "The name of the address.".locale(sender), name = "name")

    override fun run() {
        val player = checkSenderType<Player>()
        AddressHandler(player).updateAddress(addressName)
    }
}