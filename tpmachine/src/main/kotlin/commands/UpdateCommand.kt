package xyz.ldgame.corona.tpmachine.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.translate
import xyz.ldgame.corona.tpmachine.AddressHandler

class UpdateCommand : MagicCommand("Update an address.") {
    private val addressName by argument(help = "The name of the address.".translate(sender), name = "name")

    override fun run() {
        val player = checkSenderType<Player>()
        AddressHandler(player).updateAddress(addressName)
    }
}