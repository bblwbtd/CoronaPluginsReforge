package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import command.MagicCommand
import handlers.RelationHandler
import i18n.color
import i18n.locale
import i18n.onClick
import i18n.send
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ListCommand(sender: CommandSender?) : MagicCommand(sender, help = "List your friends and friend requests.") {
    private val page by argument().int().default(1)
    private val limit by argument().int().default(9)
    private val online by option("-o", "--online", help = "Only show online players.".locale(sender)).flag()


    override fun run() {
        if (page < 1) {
            "Invalid page number.".locale(sender).color(ChatColor.RED).send(sender)
            return
        }
        val player = checkSenderType<Player>()
        showFriendList(player)
    }

    private fun showFriendList(player: Player) {
        val handler = RelationHandler(player)
        val friends = handler.getFriends().sortedBy { friend -> friend.isOnline() }

        if (friends.isEmpty()) {
            "You have no friend.".locale(player).send(player)
            return
        }

        val maxPage = (friends.size / limit) + 1
        val subList = friends.subList((page - 1) * limit, friends.size.coerceAtMost(page * limit))

        val header = "Friends".locale(sender).color(ChatColor.GREEN).plus(" ($page/$maxPage)\n")
        val content = subList.filter {
            if (online && !it.isOnline()) return@filter false
            return@filter true
        }.map {
            val status = if (it.isOnline()) "Online".locale(player).color(ChatColor.GREEN) else "Offline".locale(player)
                .color(ChatColor.RED)
            "${it.name}: $status " + if (it.isOnline()) "[TP]".onClick {
                ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend tp ")
            } else ""
        }.joinToString { "\n" }

        "$header\n$content".send(player)
    }


}