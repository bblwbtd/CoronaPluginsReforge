package handlers

import org.bukkit.entity.Player
import java.util.*

private val pendingInvitations = HashMap<Player, LinkedList<Player>>()

fun sendInvitation(from: Player, to: Player) {
    val players = pendingInvitations.getOrDefault(to, LinkedList<Player>())
    if (players.find { player -> player.name == from.name } == null) {
        return
    }
    players.add(from)
    pendingInvitations[to] = players
}

fun peekNextInvitation(player: Player): Player? {
    return pendingInvitations[player]?.peek()
}

fun popNextInvitation(player: Player): Player? {
    pendingInvitations[player] ?: return null
    if (pendingInvitations[player]?.size == 0) {
        return null
    }
    return pendingInvitations[player]?.pop()
}

fun removeInvitation(from: Player, to: Player) {
    pendingInvitations[to]?.remove(from)
}

fun getAllInvitations(player: Player): LinkedList<Player> {
    return pendingInvitations.getOrDefault(player, LinkedList())
}
