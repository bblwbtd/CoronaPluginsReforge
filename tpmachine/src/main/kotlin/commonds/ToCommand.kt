package commonds

import Main
import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handler.AddressHandler
import handler.MachineHandler
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ToCommand(sender: CommandSender?) : MagicCommand(help = "Teleport to an address.", sender = sender) {
    private val addressName by argument(help = "The name of the address you want to go.".locale(sender), name = "name")

    override fun run() {
        val player = checkSenderType<Player>()

        val addressHandler = AddressHandler(player)
        val book = addressHandler.getPlayerAddressBook()
        val address = book.address.find {
            it.name == addressName
        }
        if (address == null) {
            "Can't find address with name:".locale(sender).plus(" ").plus(addressName).color(ChatColor.RED).send(sender)
            return
        }

        val machineHandler = MachineHandler(player)
        machineHandler.teleport(
            address.toLocation(),
            Main.plugin.config.getInt("delay"),
            Main.plugin.config.getInt("timeout")
        )
    }

    override fun getArgumentOptions(s: String): List<String> {
        val player = checkSenderType<Player>()
        val addressHandler = AddressHandler(player)
        return addressHandler.getPlayerAddressBook().address.filter { it.name.contains(s) }.map { it.name }
    }
}