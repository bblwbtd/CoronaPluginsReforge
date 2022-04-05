package handlers

import i18n.color
import i18n.locale
import i18n.send
import messageQueue.Message
import messageQueue.MessageQueue
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class InvitationHandler(val player: Player) {
    companion object {
        val queue = MessageQueue<Invitation>()
    }

    fun sendInvitation(to: Player) {
        val invitation = Invitation(player, {
            val handler1 = RelationHandler(player)
            val handler2 = RelationHandler(to)

            handler1.acceptInvitation(to)
            handler2.acceptInvitation(player)
        }, {
            "$to" + "decline your invitation.".locale(player).color(ChatColor.RED).send(player)
        })

        queue.addMessage(to, Message(invitation))
    }

    fun acceptInvitation(from: Player) {
        val inv = queue.fetchMessage(from) {
            it.content.from == from
        }?.content
        if (inv == null) {
            "No such an invitation.".locale(from).color(ChatColor.RED).send(from)
            return
        }

        inv.onAccept()
    }

    fun declineInvitation(from: Player) {
        val inv = queue.fetchMessage(from) {
            it.content.from == from
        }?.content
        if (inv == null) {
            "No such an invitation.".locale(from).color(ChatColor.RED).send(from)
            return
        }

        inv.onDeclined()
    }
}