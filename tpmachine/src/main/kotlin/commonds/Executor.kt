package commonds

import com.github.ajalt.clikt.core.subcommands
import command.MagicCommand
import command.MagicCommandExecutor
import org.bukkit.command.CommandSender

class Executor : MagicCommandExecutor() {
    override fun getCommand(): MagicCommand {
        return TPMCommand().subcommands(
            ListCommand(),
            RenameCommand(),
            SaveCommand(),
            UpdateCommand(),
            RemoveCommand(),
            ToCommand(),
        )
    }
}