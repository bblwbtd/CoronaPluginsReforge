package commands

import command.MagicCommand
import org.bukkit.command.CommandSender

class BoxCommand(sender: CommandSender?): MagicCommand(sender) {

    override fun aliases(): Map<String, List<String>> {
        return mapOf(
            "l" to listOf("lock"),
            "k" to listOf("keys"),
            "lo" to listOf("lockbox"),
            "unl" to listOf("unlock")
        )
    }
}