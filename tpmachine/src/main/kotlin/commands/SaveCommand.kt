package xyz.ldgame.corona.tpmachine.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.i18n.translate
import xyz.ldgame.corona.tpmachine.AddressHandler
import java.util.*

class SaveCommand : MagicCommand(help = "Save an address.") {
    private val addressName by argument(
        help = "The name of the address (optional).".translate(sender),
        name = "name"
    ).default(UUID.randomUUID().toString().substring(0, 4))

    override fun run() {
        val player = checkSenderType<Player>()
        val handler = AddressHandler(player)
        val book = handler.getPlayerAddressBook()
        if (book.address.size >= book.limit) {
            "The maximum number of addresses has been reached".translate(sender).color(ChatColor.RED).send(sender)
            return
        }
        if (handler.savePlayerLocation(addressName)) {
            "New address saved: ".translate(player).plus(addressName).color(ChatColor.GREEN).send(player)
        }
    }
}