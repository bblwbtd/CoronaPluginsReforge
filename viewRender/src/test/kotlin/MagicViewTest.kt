import org.bukkit.event.inventory.InventoryType
import kotlin.test.Test


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

    }
}