package xyz.ldgame.corona.bot.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import xyz.ldgame.corona.bot.BotMain
import xyz.ldgame.corona.bot.listBot
import xyz.ldgame.corona.common.CommonMain
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.utils.warn

class SummonCommand : MagicCommand(help = "SummonCommandHelp") {
    val botName by argument(help = "BotNameHelp")
    override fun run() {
        val player = checkSenderType<Player>()

        runBlocking {
            CommonMain.plugin.config.run {

                if (listBot(player).count {
                        Bukkit.getOnlinePlayers().find { p -> p.name == it.name } != null
                    } > getInt("maxLoginBotPerPlayer", 1)) {
                    "TooManyBot".send(
                        player, mapOf(
                            "max" to getInt("maxLoginBotPerPlayer", 1).toString()
                        )
                    )
                }

                val serverHost = getString("serverHost")
                if (serverHost == null) {
                    warn("serverHost is not set in config.yml")
                    return@runBlocking
                }

                BotMain.client.addBot(
                    getString("serverHost") ?: return@runBlocking,
                    getInt("serverPort"),
                    botName
                )
            }
        }

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