import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class MagicView(options: MagicViewOptions?) : InventoryHolder {

    private val inv = Bukkit.createInventory(null, options?.size ?: 54)
    private val components = HashMap<String, MagicComponent>()

    override fun getInventory(): Inventory {
        return inv
    }

    fun addComponent(x: Int, y: Int, component: MagicComponent): MagicView {
        components[getCoordinate(x, y)] = component
        return this
    }

    fun getComponent(x: Int, y: Int): MagicComponent? {
        return components[getCoordinate(x, y)]
    }

    fun removeComponent(x: Int, y: Int) {
        components.remove(getCoordinate(x, y))
    }

    fun open(player: Player) {
        player.openInventory(inv)
    }

    fun click(event: InventoryClickEvent) {
        val slot = event.slot

        val x = slot % 9
        val y = slot / 9
        val coordinate = getCoordinate(x, y)

        val component = components[coordinate]
        component?.handleClick?.invoke(event)
    }

    private fun getCoordinate(x: Int, y: Int): String {
        return "${x},${y}"
    }
}