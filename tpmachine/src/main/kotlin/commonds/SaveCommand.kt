package commonds

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import command.MagicCommand
import handler.AddressHandler
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class SaveCommand(sender: CommandSender?) : MagicCommand(sender, help = "Save an address.") {
    private val addressName by argument(
        help = "The name of the address (optional).".locale(sender),
        name = "name"
    ).default(UUID.randomUUID().toString().substring(0, 4))

    override fun run() {
        val player = checkSenderType<Player>()
        val handler = AddressHandler(player)
        val book = handler.getPlayerAddressBook()
        if (book.address.size >= book.limit) {
            "The maximum number of addresses has been reached".locale(sender).color(ChatColor.RED).send(sender)
            return
        }
        if (handler.savePlayerLocation(addressName)) {
            "New address saved: ".locale(player).plus(addressName).color(ChatColor.GREEN).send(player)
        }
    }
}