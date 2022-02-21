package utils

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin

fun Player.setString(plugin: Plugin, key: String, value: String) {
    persistentDataContainer.set(NamespacedKey.fromString(key, plugin)!!, PersistentDataType.STRING, value)
}

fun Player.getString() {

}