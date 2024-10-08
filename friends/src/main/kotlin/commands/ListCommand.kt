package xyz.ldgame.corona.friends.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.onClick
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.i18n.translate
import xyz.ldgame.corona.friends.RelationHandler

class ListCommand : MagicCommand(help = "List your friends.") {
    private val page by argument().int().default(1)
    private val limit by argument().int().default(9)
    private val online by option("-o", "--online", help = "Only show online players.".translate(sender)).flag()

    override fun run() {
        if (page < 1) {
            "Invalid page number.".translate(sender).color(ChatColor.RED).send(sender)
            return
        }
        val player = checkSenderType<Player>()
        showFriendList(player)
    }

    private fun showFriendList(player: Player) {
        val handler = RelationHandler(player)
        val friends = handler.getFriends().sortedBy { friend -> friend.isOnline() }

        if (friends.isEmpty()) {
            "You have no friend.".translate(player).send(player)
            return
        }

        val maxPage = (friends.size / limit) + 1
        if (page > maxPage) {
            "Nothing to display".translate(sender).color(ChatColor.RED).send(player)
            return
        }

        val subList = friends.filter {
            return@filter !(online && !it.isOnline())
        }.subList((page - 1) * limit, friends.size.coerceAtMost(page * limit))

        "Friends".translate(sender).color(ChatColor.GREEN).plus(" ($page/$maxPage)\n").send(player)
        subList.forEach {
            "${it.name} ${if (it.isOnline()) "[Online]".color(ChatColor.GREEN) else "[Offline]".color(ChatColor.GRAY)}".onClick {
                ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend tp -t ${it.name}")
            }.send(player)
        }
    }
}