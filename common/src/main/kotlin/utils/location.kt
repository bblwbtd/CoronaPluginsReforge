package xyz.ldgame.corona.common.utils

import org.bukkit.Location
import org.bukkit.entity.EntityType

fun Location.spawn(entity: EntityType) {
    world?.spawnEntity(this, entity)
}