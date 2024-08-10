package xyz.ldgame.corona.tpmachine.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.translate
import xyz.ldgame.corona.tpmachine.AddressHandler

class RemoveCommand : MagicCommand(help = "Remove addresses.") {
    private val addressName by argument(
        help = "The name of the address. (Regex is supported)".translate(sender),
        name = "name"
    )

    override fun run() {
        val player = checkSenderType<Player>()
        AddressHandler(player).removeAddress(addressName)
    }
}