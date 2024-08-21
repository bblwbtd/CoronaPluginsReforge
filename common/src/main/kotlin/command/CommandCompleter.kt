package xyz.ldgame.corona.common.command

import com.github.ajalt.clikt.core.context
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
        var current: MagicCommand = customCommand
        current.context {
            obj = MagicContext(sender)
        }

        args.forEach { arg ->
            val nextCommand = current.registeredSubcommands().find {
                it.commandName == arg
            }
            if (nextCommand != null) {
                current = nextCommand as MagicCommand
            }
        }

        current.presetSender = sender

        current.getTabCompleteOptions().forEach {
            if (it.contains(args.last())) {
                results.add(it)
            }
        }

        results.addAll(current.getArgumentOptions(args.last()))

        return results
    }
}
