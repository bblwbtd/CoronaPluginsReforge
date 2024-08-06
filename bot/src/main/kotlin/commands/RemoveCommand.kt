package xyz.ldgame.bot.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import i18n.locale

class RemoveCommand : MagicCommand(help = "Remove a bot") {
    val botName by argument(help = "The name of the bot to remove".locale(sender))
    override fun run() {

    }
}