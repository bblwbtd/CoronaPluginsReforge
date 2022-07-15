package utils


import CommonMain
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.block.TileState
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

fun getKeyList(): List<String> {
    return CommonMain.plugin.config.getStringList("keys")
}

fun getNameSpaceKey(key: String): NamespacedKey {
    return NamespacedKey(CommonMain.plugin, key)
}

fun setUUID(targetBlock: Block, uuid: String, hint: String = "") {
    val tileState = targetBlock.state as TileState
    val container = tileState.persistentDataContainer
    container.set(getNameSpaceKey("chest_uuid"), PersistentDataType.STRING, uuid)
    container.set(getNamespaceKey("lock_hint"), PersistentDataType.STRING, hint)
    tileState.update(true)
}

fun getHint(targetBlock: Block): String? {
    val tileState = targetBlock.state as TileState
    val container = tileState.persistentDataContainer
    return container.get(getNamespaceKey("lock_hint"), PersistentDataType.STRING)
}

fun getUUID(targetBlock: Block?): String? {
    if (targetBlock?.type != Material.CHEST) {
        return null
    }

    val tileState = targetBlock.state as TileState
    val container = tileState.persistentDataContainer

    if (!container.has(getNameSpaceKey("chest_uuid"), PersistentDataType.STRING)) {
        return null
    }

    return container.get(getNameSpaceKey("chest_uuid"), PersistentDataType.STRING)
}

fun setKey(itemStack: ItemStack?, uuid: String, label: String): Boolean {
    if (itemStack == null) {
        return false
    }
    itemStack.setString("key_uuid", uuid)
    itemStack.setName(label)
    itemStack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1)
    return true
}

fun getKey(stack: ItemStack?): String? {
    return stack?.getString("key_uuid")
}

fun keyCheck(chestKey: ItemStack?, targetChest: Block?): Boolean {
    val keySecret = getKey(chestKey)
    val chestSecret = getUUID(targetChest)

    if (chestSecret == null || chestSecret == "") {
        return true
    }

    return keySecret == chestSecret
}

fun cleanKeyUUID(meta: ItemStack) {
    val metaKey = meta.itemMeta as ItemMeta
    val container = metaKey.persistentDataContainer
    container.remove(NamespacedKey(CommonMain.plugin, "key_uuid"))
    meta.itemMeta = metaKey
}

fun cleanChestUUID(block: Block?) {
    val tileState = block?.state as TileState
    val container = tileState.persistentDataContainer
    container.remove(NamespacedKey(CommonMain.plugin, "chest_uuid"))
    tileState.update(true)
}

