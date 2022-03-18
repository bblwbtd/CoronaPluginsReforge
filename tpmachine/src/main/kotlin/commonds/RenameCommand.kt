package commonds

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handler.AddressHandler
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RenameCommand(sender: CommandSender) : MagicCommand(help = "Rename a address.", sender = sender) {
    private val oldName by argument()
    private val newName by argument()

    override fun run() {
        val player = checkSenderType<Player>()

        AddressHandler(player).apply {
            renamePlayerLocation(oldName, newName)
        }
    }
}