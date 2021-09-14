import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import kotlin.test.Test
import kotlin.test.assertEquals


class MagicViewTest : BaseTest() {

    @Test
    fun testOpen() {
        val player = server!!.addPlayer()
        val view = MagicView()

        view.open(player)

        player.assertInventoryView(InventoryType.CHEST)
    }

    @Test
    fun testRender() {
        val newLayout = MagicLayout().apply {
            setComponent(0, 0, MagicComponent(ItemStack(Material.GLASS)))
            setComponent(4, 4, MagicComponent(ItemStack(Material.STONE)))
        }
        val view = MagicView().apply {
            layout = newLayout
        }

        val player = server!!.addPlayer()
        view.open(player)

        val inv = player.openInventory.topInventory
        assertEquals(inv.getItem(0)?.type, Material.GLASS)
        assertEquals(inv.getItem(40)?.type, Material.STONE)
    }
}