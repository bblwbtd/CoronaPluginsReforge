package messageQueue

import java.util.concurrent.atomic.AtomicInteger

class Message<T>(
    val content: T,
    val createdAt: Long = System.currentTimeMillis(),
    val expiredAt: Long = Long.MAX_VALUE,
    val id: Int = atomicInt.getAndIncrement()
) {
    companion object {
        val atomicInt = AtomicInteger(0)
    }
}