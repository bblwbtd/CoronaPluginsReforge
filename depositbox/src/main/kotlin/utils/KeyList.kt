package utils


import CommonMain
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.block.TileState
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

fun getKeyList(): List<String> {
    return CommonMain.plugin.config.getStringList("keys")
}

fun getNameSpaceKey(key: String): NamespacedKey {
    return NamespacedKey(CommonMain.plugin, key)
}

fun setUUID(targetBlock: Block, uuid: String) {
    val tileState = targetBlock.state as TileState
    val container = tileState.persistentDataContainer
    container.set(getNameSpaceKey("ChestUUID"), PersistentDataType.STRING, uuid)
    tileState.update(true)
}

fun getUUID(targetBlock: Block?): String? {
    if (targetBlock?.state == null) {
        return null
    }

    val tileState = targetBlock.state as TileState
    val container = tileState.persistentDataContainer

    if (container.has(getNameSpaceKey("ChestUUID"), PersistentDataType.STRING)) {
        return null
    }

    return container.get(NamespacedKey(CommonMain.plugin, "ChestUUID"), PersistentDataType.STRING)
}

fun setKey(itemStack: ItemStack?, uuid: String, label: String): Boolean {
    if (itemStack == null) {
        return false
    }
    itemStack.setString("KeyUUID", uuid)
    itemStack.setName(label)
    return true
}

fun getKey(stack: ItemStack?): String? {
    return stack?.getString("KeyUUID")
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
    container.remove(NamespacedKey(CommonMain.plugin, "KeyUUID"))
    meta.itemMeta = metaKey
}

fun cleanChestUUID(block: Block?) {
    val tileState = block?.state as TileState
    val container = tileState.persistentDataContainer
    container.remove(NamespacedKey(CommonMain.plugin, "ChestUUID"))
    tileState.update(true)
}

