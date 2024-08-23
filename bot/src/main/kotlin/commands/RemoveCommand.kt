package xyz.ldgame.corona.bot.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import org.bukkit.entity.Player
import xyz.ldgame.corona.bot.listBot
import xyz.ldgame.corona.bot.removeBot
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.i18n.translate

class RemoveCommand : MagicCommand(help = "RemoveCommandHelp") {
    val botName by argument().help {
        "BotNameHelp".translate(sender)
    }

    override fun run() {
        val player = checkSenderType<Player>()

        try {
            removeBot(player, botName)
        } catch (e: IllegalArgumentException) {
            "BotNotFound".send(player)
            return
        }

        "BotRemoved".send(player)
    }

    override fun getArgumentOptions(s: String): List<String> {
        val player = checkSenderType<Player>()

        return listBot(player).map {
            it.name
        }.filter {
            it.startsWith(s, ignoreCase = true)
        }
    }

}