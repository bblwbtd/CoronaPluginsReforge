package xyz.ldgame.corona.tpmachine.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.tpmachine.AddressHandler
import xyz.ldgame.corona.common.i18n.locale
import org.bukkit.entity.Player

class UpdateCommand : MagicCommand("Update an address.") {
    private val addressName by argument(help = "The name of the address.".locale(sender), name = "name")

    override fun run() {
        val player = checkSenderType<Player>()
        AddressHandler(player).updateAddress(addressName)
    }
}