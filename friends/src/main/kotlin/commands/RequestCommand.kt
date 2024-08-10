package xyz.ldgame.corona.friends.commands

import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.i18n.translate
import xyz.ldgame.corona.friends.RequestHandler

class RequestCommand : MagicCommand(help = "Manage friend requests") {
    private val accept by option("-a", "--accept", help = "Accept a friend request.".translate(sender))
    private val decline by option("-d", "--decline", help = "Decline a friend request.".translate(sender))
    private val list by option("-l", "--list", help = "List all friend requests.".translate(sender)).flag()

    override fun run() {
        val player = checkSenderType<Player>()

        if (list) {
            showPendingList(player)
        } else if (accept != null) {
            RequestHandler(player).acceptRequest(Bukkit.getPlayer(accept!!))
        } else if (decline != null) {
            RequestHandler(player).declineRequest(Bukkit.getPlayer(decline!!))
        }

    }

    private fun showPendingList(player: Player) {
        val handler = RequestHandler(player)
        val requests = handler.getAllRequest()
        if (requests.isEmpty()) {
            "You have no pending friend request".translate(player).color(ChatColor.YELLOW).send(player)
            return
        }

        "Pending requests".translate(player).color(ChatColor.GREEN).send(player)
        handler.getAllRequest().forEach {
            val content = it.from.name
            content.send(player)
        }

    }

    override fun getTabCompleteOptions(): List<String> {
        val player = checkSenderType<Player>()
        return RequestHandler(player).getAllRequest().map { it.from.name }
    }
}