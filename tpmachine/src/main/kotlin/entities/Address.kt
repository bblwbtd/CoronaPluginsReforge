package entities

import org.bukkit.Bukkit
import org.bukkit.Location

data class Address(val name: String, val x: Double, val y: Double, val z: Double, val world: String) {
    fun toLocation(): Location {
        return Location(Bukkit.getWorld(world), x, y, z)
    }
}