package xyz.ldgame.bot.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand

class SummonCommand : MagicCommand(help = "SummonCommandHelp") {
    val botName by argument(help = "BotNameHelp")
    override fun run() {
        println("Summon command $botName")
    }
}