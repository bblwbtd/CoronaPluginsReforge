package utils

import org.bukkit.Location
import org.bukkit.entity.Player

val store = HashMap<String, Location>()


fun savePlayerLocation(key: String, player: Player) {
    store["${player.name}_${key}"] = player.location
}

fun fetchSavedLocation(key: String, player: Player): Location? {
    val s = "${player.name}_${key}"
    val location = store[s]
    store.remove(s)
    return location
}
