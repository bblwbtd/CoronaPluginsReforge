package handlers

import i18n.color
import i18n.locale
import i18n.send
import messageQueue.Message
import messageQueue.MessageQueue
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class RequestHandler(val player: Player) {
    companion object {
        val queue = MessageQueue<FriendMessage>()
    }

    fun sendRequest(to: Player) {
        if (player.name == to.name) {
            "you can't be friends with yourself.".locale(player).color(ChatColor.RED).send(player)
            return
        }

        if (queue.getAllMessages(to).find { message -> message.content.from == player } != null) {
            "You have sent request. Don't send again!".locale(player).color(ChatColor.YELLOW).send(player)
            return
        }

        val friendMessage = FriendMessage(player, {
            val handler1 = RelationHandler(player)
            val handler2 = RelationHandler(to)

            handler1.addFriend(to)
            handler2.addFriend(player)
        }, {
            "$to" + "decline your invitation.".locale(player).color(ChatColor.RED).send(player)
        })

        queue.addMessage(to, Message(friendMessage))
        "You received a friend request from".locale(to).plus(" ${player.name}".color(ChatColor.YELLOW))
            .color(ChatColor.GREEN).send(to)
        "${"Use".locale(to)} ${"/friend request -a ${player.name}".color(ChatColor.GREEN)} ${
            "to accept this request.".locale(
                to
            )
        }".send(to)
        "${"Use".locale(to)} ${"/friend request -d ${player.name}".color(ChatColor.RED)} ${
            "to decline this request.".locale(
                to
            )
        }".send(to)
        "Request sent.".locale(player).color(ChatColor.GREEN).send(player)
    }

    fun getRequest(from: Player?): FriendMessage? {
        return if (from == null) queue.popMessage(player)?.content else queue.fetchMessage(player) {
            it.content.from == from
        }?.content
    }

    fun getAllRequest(): List<FriendMessage> {
        return queue.getAllMessages(player).map {
            it.content
        }
    }

    fun acceptRequest(from: Player?) {
        val inv = getRequest(from)
        if (inv == null) {
            "No such an invitation.".locale(from).color(ChatColor.RED).send(from)
            return
        }

        inv.onAccept()
    }

    fun declineRequest(from: Player?) {
        val inv = getRequest(from)
        if (inv == null) {
            "No such an invitation.".locale(from).color(ChatColor.RED).send(from)
            return
        }

        inv.onDeclined()
    }
}