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
            MachineHandler(player).teleport(to.location)
        }, {
            "$to" + "decline your teleportation request.".locale(player).color(ChatColor.RED).send(player)
        })

        RequestHandler.queue.addMessage(
            to,
            Message(
                friendMessage,
                expiredAt = System.currentTimeMillis() + 1000 * CommonMain.plugin.config.getInt("tp_timeout")
            )
        )
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