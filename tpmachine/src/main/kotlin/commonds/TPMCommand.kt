package commonds

import command.MagicCommand
import org.bukkit.command.CommandSender

class TPMCommand(sender: CommandSender) : MagicCommand(help = "A tool for teleportation.", sender = sender) {
    override fun aliases(): Map<String, List<String>> {
        return mapOf()
    }
}