package utils

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin

fun Player.setString(plugin: Plugin, key: String, value: String) {
    persistentDataContainer.set(NamespacedKey.fromString(key, plugin)!!, PersistentDataType.STRING, value)
}

fun Player.getString(plugin: Plugin, key: String): String? {
    return persistentDataContainer.get(NamespacedKey.fromString(key, plugin)!!, PersistentDataType.STRING)
}

fun Player.setDouble(plugin: Plugin, key: String, value: Double) {
    persistentDataContainer.set(NamespacedKey.fromString(key, plugin)!!, PersistentDataType.DOUBLE, value)
}

fun Player.getDouble(plugin: Plugin, key: String): Double? {
    return persistentDataContainer.get(NamespacedKey.fromString(key, plugin)!!, PersistentDataType.DOUBLE)
}