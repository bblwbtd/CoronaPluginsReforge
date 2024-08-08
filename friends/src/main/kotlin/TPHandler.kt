package xyz.ldgame.corona.friends

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.CommonMain
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.locale
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.queue.Message
import xyz.ldgame.corona.common.queue.MessageQueue
import xyz.ldgame.corona.tpmachine.MachineHandler

class TPHandler(val player: Player) {
    companion object {
        val queue = MessageQueue<FriendMessage>()
    }

    fun sendRequest(to: Player?) {
        if (to == null) {
            "Can't find the player. Is it online?".locale(player).color(ChatColor.RED).send(player)
            return
        } else if (to.name == player.name) {
            "You can't tp to yourselves.".locale(player).color(ChatColor.RED).send(player)
            return
        }

        val friendMessage = FriendMessage(player, {
            "${to.name} ${"accepted your teleportation request!".locale(player).color(ChatColor.GREEN)}".send(player)
            MachineHandler(player, CommonMain.plugin).teleport(to.location)
        }, {
            "${to.name} ${"declined your teleportation request!".locale(player).color(ChatColor.RED)}".send(player)
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
        "TP Request sent.".locale(player).color(ChatColor.GREEN).send(player)
        "You received a teleportation request from".locale(to).plus(" ${player.name}".color(ChatColor.YELLOW))
            .color(ChatColor.GREEN).send(to)
        "${"Use".locale(to)} ${"/friend tp -a ${player.name}".color(ChatColor.GREEN)} ${
            "to accept this request.".locale(
                to
            ).color(ChatColor.WHITE)
        }".send(to)
        "${"Use".locale(to)} ${"/friend tp -d ${player.name}".color(ChatColor.RED)} ${
            "to decline this request.".locale(
                to
            ).color(ChatColor.WHITE)
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