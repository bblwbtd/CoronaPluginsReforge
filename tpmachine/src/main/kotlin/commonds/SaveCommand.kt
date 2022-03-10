package commonds

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import command.MagicCommand
import entities.AddressBook
import handler.LocationHandler
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

class SaveCommand : MagicCommand() {
    private val locationName by argument(
        help = "The name of the location.",
        name = "name"
    ).default("Location" + UUID.randomUUID().toString().substring(0, 4))

    override fun run() {
        val player = checkSenderType<Player>()
        val handler = LocationHandler(player)
        val book = handler.getPlayerAddressBook() ?: AddressBook()
        if (book.address.size >= book.limit) {
            "The maximum number of addresses has been reached".locale(sender).color(ChatColor.RED).send(sender)
            return
        }
        handler.savePlayerLocation(locationName)
        "New address saved: ".locale(player).plus(locationName).color(ChatColor.GREEN).send(player)
    }
}