package command

import com.github.ajalt.clikt.core.CliktCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.*

class CommandCompleter(private val customCommand: CliktCommand) : TabCompleter {

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): MutableList<String> {

        val results = LinkedList<String>()
        var current: CliktCommand? = customCommand

        args.forEach { arg ->
            val nextCommand = current?.registeredSubcommands()?.find {
                it.commandName == arg
            }
            if (nextCommand != null) {
                current = nextCommand
            }
        }

        if (current != null) {
            current?.registeredSubcommandNames()?.forEach {
                if (it.contains(args.last())) {
                    results.add(it)
                }
            }
        }

        return results
    }
}
