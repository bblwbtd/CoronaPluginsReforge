package xyz.ldgame.corona.bot.commands

import com.github.ajalt.clikt.core.subcommands
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.command.MagicCommandExecutor

class Executor : MagicCommandExecutor() {
    override fun getCommand(): MagicCommand {
        return BotCommand().subcommands(
            AddCommand(),
            RemoveCommand(),
            SummonCommand(),
            ListCommand(),
            DisbandCommand()
        )
    }
}