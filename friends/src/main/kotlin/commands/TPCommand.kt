package xyz.ldgame.corona.friends.commands

import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.onClick
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.i18n.translate
import xyz.ldgame.corona.friends.TPHandler

class TPCommand : MagicCommand(help = "Send teleport request to your friend.") {
    private val listFlag by option("-l", "--list", help = "List all requests".translate(sender)).flag()
    private val accept by option("-a", "--accept", help = "Accept a TP request.".translate(sender))
    private val decline by option("-d", "--decline", help = "Decline a TP request.".translate(sender))
    private val target by option("-t", "--target", help = "The teleportation target.".translate(sender))

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
                "[Accept]".translate(player).color(ChatColor.GREEN).onClick {
                    return@onClick ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend tp -a ${it.from.name}")
                }
            } ${
                "[Decline]".translate(player).color(ChatColor.RED).onClick {
                    return@onClick ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend tp -d ${it.from.name}")
                }
            }"
        }.joinToString { "\n" }

        "Pending requests: \n$requestText".send(player)
    }

    override fun getTabCompleteOptions(): List<String> {
        val player = checkSenderType<Player>()
        return TPHandler(player).getAllRequests().map {
            it.from.name
        }
    }
}