package xyz.ldgame.corona.bot.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.arguments.validate
import org.bukkit.entity.Player
import xyz.ldgame.corona.bot.addBot
import xyz.ldgame.corona.bot.listBot
import xyz.ldgame.corona.common.CommonMain
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.MagicString
import xyz.ldgame.corona.common.i18n.translate

class AddCommand : MagicCommand(help = "AddCommandHelp") {
    private val botName by argument().help {
        "BotNameHelp".translate(sender)
    }.validate {
        require(it.length in 1..CommonMain.plugin.config.getInt("botNameMaxLength", 16)) {
            "InvalidBotNameLength".translate(
                sender, mapOf(
                    "max" to CommonMain.plugin.config.getInt("botNameMaxLength", 16).toString()
                )
            )
        }
    }

    override fun run() {
        val player = checkSenderType<Player>()

        if (listBot(player).count() > CommonMain.plugin.config.getInt("maxBotPerPlayer", 10)) {
            MagicString("BotCountExceeded").send(
                player, mapOf(
                    "max" to CommonMain.plugin.config.getInt("maxBotPerPlayer", 10).toString()
                )
            )
            return
        }

        try {
            addBot(player, botName)
        } catch (e: IllegalArgumentException) {
            MagicString("BotExisted").send(
                player, mapOf(
                    "owner" to e.message!!
                )
            )
            return
        }

        MagicString("BotAdded").send(
            player, mapOf(
                "botName" to botName
            )
        )
    }
}