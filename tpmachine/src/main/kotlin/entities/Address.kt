package entities

import org.bukkit.Bukkit
import org.bukkit.Location

data class Address(var name: String, var x: Double, var y: Double, var z: Double, val world: String) {
    fun toLocation(): Location {
        return Location(Bukkit.getWorld(world), x, y, z)
    }
}