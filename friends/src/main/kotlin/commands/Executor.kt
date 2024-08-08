package xyz.ldgame.corona.friends.commands

import com.github.ajalt.clikt.core.subcommands
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.command.MagicCommandExecutor

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