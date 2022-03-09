package command

import com.github.ajalt.clikt.core.CliktCommand
import org.bukkit.command.CommandSender

abstract class MagicCommand(var sender: CommandSender? = null, help: String = "", var name: String? = null) :
    CliktCommand(help = help, printHelpOnEmptyArgs = false, name = name) {

    override fun run() {

    }

    internal fun passSender() {
        registeredSubcommands().forEach {
            it as MagicCommand
            it.sender = sender
            it.passSender()
        }
    }
}