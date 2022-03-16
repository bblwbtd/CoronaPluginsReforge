package commonds

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handler.AddressHandler
import i18n.locale
import i18n.send
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class UpdateCommand(sender: CommandSender) : MagicCommand(sender, "Update an address") {
    private val addressName by argument()

    override fun run() {
        val player = checkSenderType<Player>()
        val handler = AddressHandler(player)
        val book = handler.getPlayerAddressBook()
        val address = book.getAddressByName(addressName)
        if (address == null) {
            "No address with name".locale(sender).plus(": $addressName").send(player)
            return
        }

        player.location.apply {
            address.x = x
            address.y = y
            address.z = z
        }

        handler.saveBook()

    }
}