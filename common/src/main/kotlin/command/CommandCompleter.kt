package command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.*

class CommandCompleter(private val customCommand: MagicCommand) : TabCompleter {

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): MutableList<String> {

        val results = LinkedList<String>()
        var current: MagicCommand? = customCommand

        args.forEach { arg ->
            current?.sender = sender
            val nextCommand = current?.registeredSubcommands()?.find {
                it.commandName == arg
            }
            if (nextCommand != null) {
                current = nextCommand as MagicCommand?
            }
        }

        current?.registeredSubcommandNames()?.forEach {
            if (it.contains(args.last())) {
                results.add(it)
            }
        }

        results.addAll(current?.getArgumentOptions(args.last()) ?: emptyList())

        return results
    }
}
