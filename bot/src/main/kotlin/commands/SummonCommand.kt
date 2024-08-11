package xyz.ldgame.corona.bot.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import org.bukkit.entity.Player
import xyz.ldgame.corona.bot.addBot
import xyz.ldgame.corona.bot.listBot
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.MagicString

class SummonCommand : MagicCommand(help = "SummonCommandHelp") {
    val botName by argument(help = "BotNameHelp")
    override fun run() {
        val player = checkSenderType<Player>()


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