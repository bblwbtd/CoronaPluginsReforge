package commands

import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import command.MagicCommand
import handlers.RequestHandler
import i18n.color
import i18n.locale
import i18n.onClick
import i18n.send
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RequestCommand(sender: CommandSender?) : MagicCommand(sender) {
    private val accept by option("-a", "--accept", help = "Accept a friend request.".locale(sender))
    private val decline by option("-d", "--decline", help = "Decline a friend request.".locale(sender))
    private val list by option("-l", "--list", help = "List all friend request.".locale(sender)).flag()

    override fun run() {
        val player = checkSenderType<Player>()

        if (list) {
            showPendingList(player)
        } else if (accept != null) {
            RequestHandler(player).acceptRequest(Bukkit.getPlayer(accept!!))
        } else if (decline != null) {
            val handler = RequestHandler(player)
            handler.declineRequest(Bukkit.getPlayer(decline!!))
        }

    }

    private fun showPendingList(player: Player) {
        val handler = RequestHandler(player)
        val requests = handler.getAllRequest()
        if (requests.isEmpty()) {
            "You have no pending friend request".locale(player).color(ChatColor.YELLOW)
            return
        }

        val header = "Pending requests".locale(player).color(ChatColor.GREEN)
        val content = handler.getAllRequest().map {
            "${it.from} ${
                "[Accept]".locale(player).color(ChatColor.GREEN).onClick {
                    return@onClick ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept ${it.from.name}")
                }
            } ${
                "[Decline]".locale(player).color(ChatColor.RED).onClick {
                    return@onClick ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend decline ${it.from.name}")
                }
            }"

        }

        "$header\n$content".send(player)
    }
}