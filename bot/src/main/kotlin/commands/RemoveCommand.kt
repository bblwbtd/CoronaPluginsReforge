package xyz.ldgame.bot.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import i18n.locale

class RemoveCommand : MagicCommand(help = "RemoveCommandHelp") {
    val botName by argument(help = "BotNameHelp".locale(sender))
    override fun run() {

    }
}