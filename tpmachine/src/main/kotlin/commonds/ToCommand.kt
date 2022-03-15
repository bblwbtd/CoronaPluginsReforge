package commonds

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handler.LocationHandler
import handler.TeleportationHandler
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class ToCommand : MagicCommand(help = "Teleport to a address.") {
    private val addressName by argument()

    override fun run() {
        val player = checkSenderType<Player>()

        val locationHandler = LocationHandler(player)
        val book = locationHandler.getPlayerAddressBook()
        val address = book.address.find {
            it.name == addressName
        }
        if (address == null) {
            "Can't find address with name:".locale(sender).plus(" ").plus(addressName).color(ChatColor.RED).send(sender)
            return
        }

        val teleportationHandler = TeleportationHandler(player)
        teleportationHandler.spawnMachine(address.toLocation())
    }

    override fun getArgumentOptions(s: String): List<String> {
        val player = checkSenderType<Player>()
        val locationHandler = LocationHandler(player)
        return locationHandler.getPlayerAddressBook().address.filter { it.name.contains(s) }.map { it.name }
    }
}