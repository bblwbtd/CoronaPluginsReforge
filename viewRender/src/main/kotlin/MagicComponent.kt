import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MagicComponent(
    val itemStack: ItemStack,
    var clickable: Boolean = true
) {

    var handleClick: ((event: InventoryClickEvent) -> Any)? = null

    fun onClick(handler: (event: InventoryClickEvent) -> Any): MagicComponent {
        if (clickable) {
            this.handleClick = handler
        }
        return this
    }
}