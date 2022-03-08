package viewRender

import i18n.getText
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import utils.setName

open class MagicView(options: MagicViewOptions? = null) : InventoryHolder {

    open var layout = options?.layout
    private val inv = Bukkit.createInventory(this, options?.size ?: 54)
    private var current = 0
    private val layouts = ArrayList<MagicLayout>()
    private val nextPageButton = MagicComponent(ItemStack(Material.STONE_BUTTON)).onClick {
        next()
    }
    private val lastPageButton = MagicComponent(ItemStack(Material.STONE_BUTTON)).onClick {
        last()
    }

    fun next(): MagicView {
        if (current >= layouts.size) {
            return this
        }

        current += 1

        rerender()
        return this
    }

    fun last(): MagicView {
        if (current <= 0) {
            return this
        }

        current -= 1

        rerender()
        return this
    }

    fun setLayout(page: Int, layout: MagicLayout): MagicView {
        layouts[page] = layout
        return this
    }

    fun addLayout(layout: MagicLayout): MagicView {
        layouts.add(layout)
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

    open fun render(lang: String = "en_us") {
        val currentLayout = layouts[current]

        if (layouts.size > 1) {
            if (current > 0) {
                currentLayout.setComponent(2, 5, lastPageButton.apply {
                    itemStack.setName(getText("Last Page", lang))
                })
            }

            if (current < layouts.size - 1) {
                currentLayout.setComponent(6, 5, nextPageButton.apply {
                    itemStack.setName(getText("Next Page", lang))
                })
            }
        }

        currentLayout.render(inv)
    }

    fun click(event: InventoryClickEvent) {
        val slot = event.slot
        val currentLayout = layouts[current]

        val x = slot % 9
        val y = slot / 9
        val coordinate = currentLayout.getCoordinate(x, y)

        val component = currentLayout.components[coordinate]
        component?.clickHandler?.invoke(event)
    }

    fun rerender() {
        clear()
        render()
    }
}