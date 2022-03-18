package commands

import command.MagicCommand
import org.bukkit.command.CommandSender

class AuthCommand(sender: CommandSender?) : MagicCommand(sender) {

    override fun aliases(): Map<String, List<String>> {
        return mapOf(
            "l" to listOf("login"),
            "r" to listOf("register"),
            "u" to listOf("update")
        )
    }
}