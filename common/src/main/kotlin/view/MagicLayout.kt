package xyz.ldgame.corona.common.view

import org.bukkit.inventory.Inventory

class MagicLayout {

    val components = HashMap<String, MagicComponent>()

    fun render(inv: Inventory) {
        components.forEach { entry ->
            val slot = getSlot(entry.key)
            inv.setItem(slot, entry.value.itemStack)
        }
    }

    private fun getSlot(coordinate: String): Int {
        coordinate.split(",").apply {
            return first().toInt() + last().toInt() * 9
        }
    }

    fun setComponent(x: Int, y: Int, component: MagicComponent): MagicLayout {
        components[getCoordinate(x, y)] = component
        return this
    }

    fun setComponent(pos: Int, component: MagicComponent): MagicLayout {
        components["${pos % 9},${pos / 9}"] = component
        return this
    }

    fun getComponent(x: Int, y: Int): MagicComponent? {
        return components[getCoordinate(x, y)]
    }

    fun removeComponent(x: Int, y: Int) {
        components.remove(getCoordinate(x, y))
    }


    fun getCoordinate(x: Int, y: Int): String {
        return "${x},${y}"
    }

}