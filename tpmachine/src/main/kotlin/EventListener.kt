import org.bukkit.block.Chest
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.DoubleChestInventory
import org.spigotmc.event.entity.EntityMountEvent

class EventListener : Listener {

    fun onMount(event: EntityMountEvent) {

    }

    fun test(event: PlayerInteractEvent) {
        val block = event.clickedBlock
        if (block?.state is Chest) {
            val chest = block.state as Chest
            if (chest.inventory is DoubleChestInventory) {
                val inv = chest.inventory as DoubleChestInventory
                val leftChest = inv.leftSide.location?.block
                val rightChest = inv.rightSide.location?.block
            }
        }
    }

}