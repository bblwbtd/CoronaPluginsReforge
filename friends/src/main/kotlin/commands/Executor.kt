package commands

import com.github.ajalt.clikt.core.subcommands
import command.MagicCommand
import command.MagicCommandExecutor
import org.bukkit.command.CommandSender

class Executor : MagicCommandExecutor() {
    override fun getCommand(sender: CommandSender?): MagicCommand {
        return ToggleCommand(sender).subcommands(
            AddCommand(sender),
            ToggleCommand(sender),
            RemoveCommand(sender),
            ListCommand(sender),
            TPCommand(sender),
            RequestCommand(sender)
        )
    }
}