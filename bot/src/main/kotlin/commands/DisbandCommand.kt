package xyz.ldgame.corona.bot.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.arguments.help
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import xyz.ldgame.corona.bot.getOnlineBot
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.translate

class DisbandCommand: MagicCommand(help = "disbandCommandHelp") {
    val botName by argument().help {
        "BotNameHelp".translate(sender)
    }

    override fun run() {
        val player = checkSenderType<Player>()

        getOnlineBot(player).find { it.name == botName }?.let {
            Bukkit.getPlayer(it.name)?.kickPlayer("Disband")
        }
    }
}