package handlers

import CommonMain
import handler.MachineHandler
import i18n.color
import i18n.locale
import i18n.send
import messageQueue.Message
import messageQueue.MessageQueue
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class TPHandler(val player: Player) {
    companion object {
        val queue = MessageQueue<FriendMessage>()
    }

    fun sendRequest(to: Player?) {
        if (to == null) {
            "No such a player.".locale(player).color(ChatColor.RED).send(player)
            return
        }

        val friendMessage = FriendMessage(player, {
            "$to ${"accepted your teleportation request!".locale(player).color(ChatColor.GREEN)}".send(player)
            MachineHandler(player).teleport(to.location)
        }, {
            "$to ${"declined your teleportation request!".locale(player).color(ChatColor.RED)}".send(player)
        })

        getAllRequests().find { it.from == player }?.apply {
            "Don't send repeated request.".locale(player).color(ChatColor.YELLOW).send(player)
            return
        }
        RequestHandler.queue.addMessage(
            to,
            Message(
                friendMessage,
                expiredAt = System.currentTimeMillis() + 1000 * CommonMain.plugin.config.getInt("tp_timeout")
            )
        )
        "Request sent.".locale(player).color(ChatColor.GREEN).send(player)
        "You received a teleportation request from".locale(to).plus(" ${player.name}".color(ChatColor.YELLOW))
            .color(ChatColor.GREEN).send(to)
        "${"Use".locale(to)} ${"/friend tp -a ${player.name}".color(ChatColor.GREEN)} ${
            "to accept this request.".locale(
                to
            )
        }".send(to)
        "${"Use".locale(to)} ${"/friend tp -d ${player.name}".color(ChatColor.RED)} ${
            "to decline this request.".locale(
                to
            )
        }".send(to)
    }

    fun getAllRequests(): List<FriendMessage> {
        return queue.getAllMessages(player).map { it.content }
    }

    fun getRequest(from: Player?): FriendMessage? {
        return if (from == null) RequestHandler.queue.popMessage(player)?.content else RequestHandler.queue.fetchMessage(
            player
        ) {
            it.content.from == from
        }?.content
    }

    fun acceptRequest(from: Player?) {
        val inv = getRequest(from)
        if (inv == null) {
            "No such a request or it is timeout.".locale(from).color(ChatColor.RED).send(from)
            return
        }

        inv.onAccept()
    }

    fun declineRequest(from: Player?) {
        val inv = getRequest(from)
        if (inv == null) {
            "No such a request or it is timeout.".locale(from).color(ChatColor.RED).send(from)
            return
        }

        inv.onDeclined()
    }
}