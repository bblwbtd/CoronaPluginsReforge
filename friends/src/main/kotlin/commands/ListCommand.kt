package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import command.MagicCommand
import handler.TeleportationHandler
import handlers.RelationHandler
import i18n.color
import i18n.locale
import i18n.onClick
import i18n.send
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ListCommand(sender: CommandSender?) : MagicCommand(sender) {
    val page by argument().int().default(1)
    val limit by argument().int().default(9)
    val online by option("--online", "-o").flag()

    override fun run() {
        if (page < 1) {
            "Invalid page number.".locale(sender).color(ChatColor.RED).send(sender)
            return
        }
        val player = checkSenderType<Player>()
        val handler = RelationHandler(player)
        val friends = handler.getFriends().sortedBy { friend -> friend.isOnline() }
        val maxPage = (friends.size / limit) + 1
        val subList = friends.subList((page - 1) * limit, friends.size.coerceAtMost(page * limit))

        val header = "Friends".locale(sender).color(ChatColor.GREEN).plus(" ($page/$maxPage)\n")
        val content = subList.map {
            val status = if (it.isOnline()) "Online".locale(player).color(ChatColor.GREEN) else "Offline".locale(player)
                .color(ChatColor.RED)
            "${it.name}: $status " + if (it.isOnline()) "[TP]".onClick {
                TeleportationHandler(player).spawnMachine(it.getPlayer()!!.location)
                ClickEvent(ClickEvent.Action.RUN_COMMAND, "")
            } else ""
        }.joinToString { "\n" }
    }
}