package xyz.ldgame.corona.common.view

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MagicComponent(
    val itemStack: ItemStack,
    private var movable: Boolean = false
) {

    var clickHandler: ((event: InventoryClickEvent) -> Any)? = null

    fun onClick(handler: (event: InventoryClickEvent) -> Any): MagicComponent {
        this.clickHandler = handler
        return this
    }

    fun handleClick(event: InventoryClickEvent) {
        if (!movable) event.isCancelled = true
        clickHandler?.invoke(event)
    }

}