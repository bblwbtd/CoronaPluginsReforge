import org.bukkit.Bukkit
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.Player

data class Friend(val name: String, val createdAt: Long) : ConfigurationSerializable {

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "createdAt" to createdAt
        )
    }

    fun getPlayer(): Player? {
        return Bukkit.getPlayer(name)
    }

    fun isOnline(): Boolean {
        return getPlayer()?.isOnline ?: false
    }
}
