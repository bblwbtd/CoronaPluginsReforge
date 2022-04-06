package utils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin
import kotlin.math.ceil
import kotlin.random.Random

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

fun Entity.safeRandomTP(radius: Double): Location {
    val originalLocation = location

    for (i in 1..10) {
        val x = Random.nextDouble(radius)
        val z = Random.nextDouble(radius)

        val newLocation = Location(
            originalLocation.world, ceil(originalLocation.x + x) + 0.5,
            originalLocation.y, ceil(originalLocation.z + z) + 0.5
        )

        var pointer = newLocation.clone()
        for (height in newLocation.y.toInt()..newLocation.world!!.maxHeight) {
            pointer.y = height.toDouble()
            val firstBlock = pointer.block.isPassable
            val secondBlock = pointer.add(0.0, 1.0, 0.0).block.isPassable
            val thirdBlock = pointer.add(0.0, 2.0, 0.0).block.isPassable

            if (!firstBlock && secondBlock && thirdBlock) {
                teleport(pointer)
                return pointer
            }
        }

        pointer = newLocation.clone()
        for (height in newLocation.y.toInt() downTo location.world!!.minHeight + 2) {
            pointer.y = height.toDouble()
            val firstBlock = pointer.block.isPassable
            val secondBlock = pointer.add(0.0, -1.0, 0.0).block.isPassable
            val thirdBlock = pointer.add(0.0, -2.0, 0.0).block.isPassable

            if (firstBlock && secondBlock && !thirdBlock) {
                pointer.y = height - 2.0
                teleport(pointer)
                return pointer
            }
        }
    }

    teleport(Bukkit.getWorlds().first().spawnLocation)
    return Bukkit.getWorlds().first().spawnLocation
}