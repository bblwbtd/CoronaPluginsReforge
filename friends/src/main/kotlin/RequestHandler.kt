package xyz.ldgame.corona.friends

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.i18n.translate
import xyz.ldgame.corona.common.queue.Message
import xyz.ldgame.corona.common.queue.MessageQueue

class RequestHandler(val player: Player) {
    companion object {
        val queue = MessageQueue<FriendMessage>()
    }

    fun sendRequest(to: Player) {
        if (player.name == to.name) {
            "you can't be friends with yourself.".translate(player).color(ChatColor.RED).send(player)
            return
        } else if (queue.getAllMessages(to).find { message -> message.content.from == player } != null) {
            "You have sent request. Don't send again!".translate(player).color(ChatColor.YELLOW).send(player)
            return
        } else {
            val relationHandler = RelationHandler(player)
            val friend = relationHandler.getFriends().find {
                it.name == to.name
            }
            if (friend != null)  {
                "You are already friends.".translate(player).color(ChatColor.RED).send(player)
                return
            }
        }

        val friendMessage = FriendMessage(player, {
            val handler1 = RelationHandler(player)
            val handler2 = RelationHandler(to)

            handler1.addFriend(to)
            handler2.addFriend(player)
        }, {
            "${to.name} ${"decline your friend request.".translate(player)}".color(ChatColor.RED).send(player)
        })

        queue.addMessage(to, Message(friendMessage))
        "You received a friend request from".translate(to).plus(" ${player.name}".color(ChatColor.YELLOW))
            .color(ChatColor.GREEN).send(to)
        "${"Use".translate(to)} ${"/friend request -a ${player.name}".color(ChatColor.GREEN)} ${
            "to accept this request.".translate(
                to
            ).color(ChatColor.WHITE)
        }".send(to)
        "${"Use".translate(to)} ${"/friend request -d ${player.name}".color(ChatColor.RED)} ${
            "to decline this request.".translate(
                to
            ).color(ChatColor.WHITE)
        }".send(to)
        "Friend request sent.".translate(player).color(ChatColor.GREEN).send(player)
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
            "No such an invitation.".translate(from).color(ChatColor.RED).send(from)
            return
        }

        inv.onAccept()
    }

    fun declineRequest(from: Player?) {
        val inv = getRequest(from)
        if (inv == null) {
            "No such an invitation.".translate(from).color(ChatColor.RED).send(from)
            return
        }

        inv.onDeclined()
    }
}