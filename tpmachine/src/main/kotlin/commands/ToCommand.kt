package xyz.ldgame.corona.tpmachine.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import net.md_5.bungee.api.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.translate
import xyz.ldgame.corona.tpmachine.AddressHandler
import xyz.ldgame.corona.tpmachine.MachineHandler
import xyz.ldgame.corona.tpmachine.Main

class ToCommand : MagicCommand(help = "Teleport to an address.") {
    private val addressName by argument(
        help = "The name of the address you want to go.".translate(sender),
        name = "name"
    )

    override fun run() {
        val player = checkSenderType<Player>()

        val addressHandler = AddressHandler(player)
        val book = addressHandler.getPlayerAddressBook()
        val address = book.address.find {
            it.name == addressName
        }
        if (address == null) {
            sender?.let {
                "Can't find address with name:".translate(sender).plus(" ").plus(addressName).color(ChatColor.RED)
                    .send(it)
            }
            return
        }

        val machineHandler = MachineHandler(player)
        machineHandler.teleport(
            address.toLocation(),
            Main.plugin.config.getInt("delay"),
            Main.plugin.config.getInt("timeout")
        )
    }

    override fun getTabCompleteOptions(): List<String> {
        val player = checkSenderType<Player>()
        val addressHandler = AddressHandler(player)
        return addressHandler.getPlayerAddressBook().address.map { it.name }
    }
}