package commands

import com.github.ajalt.clikt.core.subcommands
import command.MagicCommand
import command.MagicCommandExecutor

class Executor : MagicCommandExecutor() {
    override fun getCommand(): MagicCommand {
        return ToggleCommand().subcommands(
            AddCommand(),
            ToggleCommand(),
            RemoveCommand(),
            ListCommand(),
            TPCommand(),
            RequestCommand()
        )
    }
}