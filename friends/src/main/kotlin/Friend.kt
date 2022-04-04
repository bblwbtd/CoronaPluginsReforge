import org.bukkit.configuration.serialization.ConfigurationSerializable

data class Friend(val name: String, val createdAt: Long) : ConfigurationSerializable {

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "createdAt" to createdAt
        )
    }
}
