package xyz.ldgame.corona.bot.commands

import org.bukkit.entity.Player
import xyz.ldgame.corona.bot.listBot
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.send

class ListCommand: MagicCommand(help = "ListCommandHelp") {
    override fun run() {
        val player = checkSenderType<Player>()

        val bots = listBot(player)

        if (bots.isEmpty()) {
            "NoBot".send(player)
            return
        }

        "BotList".send(player)

        bots.joinToString {
            " - " + it.name + "\n"
        }.send(player)
    }
}