package commands

import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import command.MagicCommand
import handlers.RequestHandler
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RequestCommand(sender: CommandSender?) : MagicCommand(sender, help = "Manage friend requests") {
    private val accept by option("-a", "--accept", help = "Accept a friend request.".locale(sender))
    private val decline by option("-d", "--decline", help = "Decline a friend request.".locale(sender))
    private val list by option("-l", "--list", help = "List all friend requests.".locale(sender)).flag()

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
            "You have no pending friend request".locale(player).color(ChatColor.YELLOW).send(player)
            return
        }

        "Pending requests".locale(player).color(ChatColor.GREEN).send(player)
        handler.getAllRequest().forEach {
            val content = it.from.name
            content.send(player)
        }

    }

    override fun getArgumentOptions(s: String): List<String> {
        val player = checkSenderType<Player>()
        return RequestHandler(player).getAllRequest().filter { it.from.name.contains(s) }.map { it.from.name }
    }
}