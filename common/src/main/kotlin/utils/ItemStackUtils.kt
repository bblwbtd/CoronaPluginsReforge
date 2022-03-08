package utils

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

fun getKey(key: String): NamespacedKey {
    return NamespacedKey.fromString(key)!!
}

fun ItemStack.setString(key: String, value: String) {
    itemMeta?.persistentDataContainer?.set(getKey(key), PersistentDataType.STRING, value)
}

fun ItemStack.getString(key: String): String? {
    return itemMeta?.persistentDataContainer?.get(getKey(key), PersistentDataType.STRING)
}

fun ItemStack.setInt(key: String, value: Int) {
    itemMeta?.persistentDataContainer?.set(getKey(key), PersistentDataType.INTEGER, value)
}

fun ItemStack.getInt(key: String): Int? {
    return itemMeta?.persistentDataContainer?.get(getKey(key), PersistentDataType.INTEGER)
}

fun ItemStack.setName(name: String): ItemStack {
    this.itemMeta = itemMeta?.apply {
        setDisplayName(name)
    }
    return this
}
