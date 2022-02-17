package viewRender

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

open class MagicView(options: MagicViewOptions? = null) : InventoryHolder {

    private val inv = Bukkit.createInventory(null, options?.size ?: 54)
    open var layout = options?.layout

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

    open fun render() {
        layout?.render(inv)
    }

    fun click(event: InventoryClickEvent) {
        val slot = event.slot

        val x = slot % 9
        val y = slot / 9
        val coordinate = layout?.getCoordinate(x, y)

        val component = layout?.components?.get(coordinate)
        component?.clickHandler?.invoke(event)
    }

    fun rerender() {
        clear()
        render()
    }
}