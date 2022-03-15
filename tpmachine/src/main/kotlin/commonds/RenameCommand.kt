package commonds

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handler.LocationHandler
import org.bukkit.entity.Player

class RenameCommand : MagicCommand(help = "Rename a address.") {
    private val oldName by argument()
    private val newName by argument()

    override fun run() {
        val player = checkSenderType<Player>()

        LocationHandler(player).apply {
            renamePlayerLocation(oldName, newName)
        }
    }
}