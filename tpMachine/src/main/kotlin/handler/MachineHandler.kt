package handler

import org.bukkit.Location
import org.bukkit.entity.EntityType

class MachineHandler {

    fun spawnMachine(location: Location) {
        location.world?.spawnEntity(location, EntityType.CHICKEN)
    }
}