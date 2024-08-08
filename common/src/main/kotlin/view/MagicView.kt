package xyz.ldgame.corona.common.view

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

abstract class MagicView(size: Int = 54) : InventoryHolder {
    private val inv = Bukkit.createInventory(this, size)
    var current = 0
    private var currentLayout: MagicLayout? = null
    val nextPageButton = MagicComponent(ItemStack(Material.STONE_BUTTON)).onClick {
        next()
    }
    val lastPageButton = MagicComponent(ItemStack(Material.STONE_BUTTON)).onClick {
        last()
    }

    abstract fun getLayout(): MagicLayout

    fun next(): MagicView {
        current += 1
        render()
        return this
    }

    fun last(): MagicView {
        current -= 1
        render()
        return this
    }


    override fun getInventory(): Inventory {
        return inv
    }

    fun open(player: Player) {
        render()
        player.openInventory(inv)
    }

    fun clear() {
        inv.clear()
    }

    open fun render(locale: String = "en_us") {
        clear()
        currentLayout = getLayout()
        currentLayout?.render(inv)
    }

    fun click(event: InventoryClickEvent) {
        val slot = event.slot

        val x = slot % 9
        val y = slot / 9
        val coordinate = currentLayout?.getCoordinate(x, y)

        currentLayout?.components?.get(coordinate)?.clickHandler?.invoke(event)
    }

    fun close() {
        inv.viewers.forEach { it.closeInventory() }
    }
}