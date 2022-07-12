package commands

import Main
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import command.MagicCommand
import handlers.TPHandler
import i18n.color
import i18n.locale
import i18n.onClick
import i18n.send
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TPCommand(sender: CommandSender?) : MagicCommand(sender, help = "Send teleport request to your friend.") {
    private val listFlag by option("-l", "--list", help = "List all requests".locale(sender)).flag()
    private val accept by option("-a", "--accept", help = "Accept a TP request.".locale(sender))
    private val decline by option("-d", "--decline", help = "Decline a TP request.".locale(sender))
    private val target by option("-t", "--target", help = "The teleportation target.".locale(sender))

    override fun run() {
        val player = checkSenderType<Player>()
        if (listFlag) {
            listRequests(player)
        } else if (accept != null) {
            TPHandler(player).acceptRequest(Bukkit.getPlayer(accept!!))
        } else if (decline != null) {
            TPHandler(player).declineRequest(Bukkit.getPlayer(accept!!))
        } else if (target != null) {
            TPHandler(player).sendRequest(Bukkit.getPlayer(target!!))
        }
    }

    private fun listRequests(player: Player) {
        val handler = TPHandler(player)
        val requestText = handler.getAllRequests().map {
            "${it.from.name} ${
                "[Accept]".locale(player).color(ChatColor.GREEN).onClick {
                    return@onClick ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend tp -a ${it.from.name}")
                }
            } ${
                "[Decline]".locale(player).color(ChatColor.RED).onClick {
                    return@onClick ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend tp -d ${it.from.name}")
                }
            }"
        }.joinToString { "\n" }

        "Pending requests: \n$requestText".send(player)
    }

}