package xyz.ldgame.corona.bot.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.translate

class RemoveCommand : MagicCommand(help = "RemoveCommandHelp") {
    val botName by argument().help {
        "BotNameHelp".translate(sender)
    }

    override fun run() {

    }
}