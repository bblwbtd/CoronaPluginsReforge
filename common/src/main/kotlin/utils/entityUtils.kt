package utils

import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin

fun Entity.setString(plugin: Plugin, key: String, value: String) {
    persistentDataContainer.set(NamespacedKey.fromString(key, plugin)!!, PersistentDataType.STRING, value)
}

fun Entity.getString(plugin: Plugin, key: String): String? {
    return persistentDataContainer.get(NamespacedKey.fromString(key, plugin)!!, PersistentDataType.STRING)
}

fun Entity.setDouble(plugin: Plugin, key: String, value: Double) {
    persistentDataContainer.set(NamespacedKey.fromString(key, plugin)!!, PersistentDataType.DOUBLE, value)
}

fun Entity.getDouble(plugin: Plugin, key: String): Double? {
    return persistentDataContainer.get(NamespacedKey.fromString(key, plugin)!!, PersistentDataType.DOUBLE)
}

fun Entity.setInt(plugin: Plugin, key: String, value: Int) {
    persistentDataContainer.set(NamespacedKey.fromString(key, plugin)!!, PersistentDataType.INTEGER, value)
}

fun Entity.getInt(plugin: Plugin, key: String): Int? {
    return persistentDataContainer.get(NamespacedKey.fromString(key, plugin)!!, PersistentDataType.INTEGER)
}

private val locationStore = HashMap<String, Location>()

fun Entity.saveLocation(prefix: String) {
    locationStore["${prefix}_$name"] = location
}

fun Entity.retrieveLocation(prefix: String): Location? {
    val key = "${prefix}_$name"
    val l = locationStore[key]
    locationStore.remove(key)
    return l
}

fun Entity.loadLocation(prefix: String): Location? {
    return locationStore["${prefix}_$name"]
}