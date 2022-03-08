package pages

import entities.Address
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import utils.setName
import viewRender.MagicComponent
import viewRender.MagicLayout
import viewRender.MagicView

class ListPage(private val list: List<Address>, val player: Player) : MagicView() {
    override fun getLayout(): MagicLayout {
        return MagicLayout().apply {
            val subList = list.subList(current * 44, (current + 1) * 44)

            subList.forEachIndexed { index, address ->
                setComponent(index, MagicComponent(ItemStack(Material.BEACON).apply {
                    setName(address.name)
                }).onClick {
                    close()

                })
            }

            if (current > 0) {
                setComponent(2, 5, lastPageButton)
            }

            if ((current + 1) * 44 < list.size) {
                setComponent(6, 5, nextPageButton)
            }
        }
    }
}