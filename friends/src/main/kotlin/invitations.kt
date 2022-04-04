import org.bukkit.entity.Player
import java.util.*

private val pendingRequests = HashMap<Player, LinkedList<Player>>()

fun sendRequest(from: Player, to: Player) {
    val requestList = pendingRequests.getOrDefault(to, LinkedList<Player>())
    if (requestList.find { player -> player.name == from.name } == null) {
        return
    }
    requestList.add(from)
    pendingRequests[to] = requestList
}

fun peekNextRequest(player: Player): Player? {
    return pendingRequests[player]?.peek()
}

fun popNextRequest(player: Player): Player? {
    pendingRequests[player] ?: return null
    if (pendingRequests[player]?.size == 0) {
        return null
    }
    return pendingRequests[player]?.pop()
}

fun removeRequest(from: Player, to: Player) {
    pendingRequests[to]?.remove(from)
}
