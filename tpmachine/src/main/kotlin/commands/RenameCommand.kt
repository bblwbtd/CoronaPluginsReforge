package xyz.ldgame.corona.tpmachine.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.translate
import xyz.ldgame.corona.tpmachine.AddressHandler

class RenameCommand : MagicCommand(help = "Rename an address.") {
    private val oldName by argument(help = "The old address name".translate(sender))
    private val newName by argument(help = "The new address name".translate(sender))

    override fun run() {
        val player = checkSenderType<Player>()

        AddressHandler(player).apply {
            renamePlayerLocation(oldName, newName)
        }
    }
}