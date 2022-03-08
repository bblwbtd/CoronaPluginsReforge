package handler

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

val store = HashMap<String, Array<ItemStack>>()

fun save(player: Player) {
    store[player.name] = player.inventory.contents.clone()
}

fun loadAndDelete(player: Player) {
    player.inventory.contents = store[player.name] ?: arrayOf()
    store.remove(player.name)
    player.updateInventory()
}