package utils

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

fun getNamespaceKey(key: String): NamespacedKey {
    return NamespacedKey.minecraft(key)
}

fun ItemStack.setString(key: String, value: String) {
    val meta = itemMeta
    meta?.persistentDataContainer?.set(getNamespaceKey(key), PersistentDataType.STRING, value)
    itemMeta = meta
}


fun ItemStack.getString(key: String): String? {
    return itemMeta?.persistentDataContainer?.get(getNamespaceKey(key), PersistentDataType.STRING)
}

fun ItemStack.setInt(key: String, value: Int) {
    val meta = itemMeta
    meta?.persistentDataContainer?.set(getNamespaceKey(key), PersistentDataType.INTEGER, value)
    itemMeta = meta
}

fun ItemStack.getInt(key: String): Int? {
    return itemMeta?.persistentDataContainer?.get(getNamespaceKey(key), PersistentDataType.INTEGER)
}

fun ItemStack.setName(name: String): ItemStack {
    this.itemMeta = itemMeta?.apply {
        setDisplayName(name)
    }
    return this
}
