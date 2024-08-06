package xyz.ldgame.bot.commands

import com.github.ajalt.clikt.core.subcommands
import command.MagicCommand
import command.MagicCommandExecutor

class Executor : MagicCommandExecutor() {
    override fun getCommand(): MagicCommand {
        return BotCommand().subcommands(
            AddCommand(),
            RemoveCommand(),
            SummonCommand(),
            ListCommand(),
        )
    }
}