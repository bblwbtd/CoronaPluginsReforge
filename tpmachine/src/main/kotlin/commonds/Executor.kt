package commonds

import com.github.ajalt.clikt.core.subcommands
import command.MagicCommand
import command.MagicCommandExecutor
import org.bukkit.command.CommandSender

class Executor : MagicCommandExecutor() {
    override fun getCommand(sender: CommandSender): MagicCommand {
        return TPMCommand(sender).subcommands(
            ListCommand(sender),
            RenameCommand(sender),
            SaveCommand(sender),
            UpdateCommand(sender),
            RemoveCommand(sender),
            ToCommand(sender),
        )
    }
}