package messageQueue

import org.bukkit.entity.Player
import java.util.*

class MessageQueue<T> {
    private val messageStorage = HashMap<Player, MutableList<Message<T>>>()

    fun addMessage(player: Player, message: Message<T>) {
        messageStorage.getOrPut(player) {
            LinkedList<Message<T>>()
        }.add(message)
    }

    fun peekMessage(player: Player, remove: Boolean = false): Message<T>? {
        val queue = messageStorage[player] ?: return null
        val iterator = queue.listIterator()
        while (iterator.hasNext()) {
            val el = iterator.next()
            if (el.expiredAt >= System.currentTimeMillis()) {
                if (remove) {
                    iterator.remove()
                }
                return el
            } else {
                iterator.remove()
            }
        }

        return null
    }

    fun popMessage(player: Player): Message<T>? {
        return peekMessage(player, true)
    }

    fun fetchMessage(player: Player, predicate: (Message<T>) -> Boolean): Message<T>? {
        return messageStorage[player]?.indexOfFirst(predicate)?.run {
            messageStorage[player]?.removeAt(this)
        }
    }

    fun getAllMessages(player: Player): MutableList<Message<T>> {
        return messageStorage.getOrPut(player) {
            LinkedList<Message<T>>()
        }
    }

    fun discardMessage(player: Player, id: Int) {
        getAllMessages(player).removeAll { message -> message.id == id }
    }

}