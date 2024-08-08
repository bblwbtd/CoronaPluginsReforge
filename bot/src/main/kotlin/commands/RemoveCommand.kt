package xyz.ldgame.corona.bot.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.locale

class RemoveCommand : MagicCommand(help = "RemoveCommandHelp") {
    val botName by argument().help {
        "BotNameHelp".locale(sender)
    }

    override fun run() {

    }
}