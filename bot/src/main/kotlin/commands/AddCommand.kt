package xyz.ldgame.corona.bot.commands

import org.bukkit.entity.Player
import xyz.ldgame.corona.bot.BotMain
import xyz.ldgame.corona.common.command.MagicCommand

class AddCommand: MagicCommand(help = "AddCommandHelp") {
    override fun run() {
        val player = checkSenderType<Player>()

        BotMain.storage.readData(player.name)
    }
}