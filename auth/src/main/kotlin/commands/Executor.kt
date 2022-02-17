package commands

import command.MagicCommand
import command.MagicCommandExecutor

class Executor : MagicCommandExecutor() {
    override fun getCommand(): MagicCommand {
        return RootCommand()
    }
}