import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class MultiplePageView : MagicView() {
    private var current = 0
    private val layouts = ArrayList<MagicLayout>()
    private val nextPageButton = MagicComponent(ItemStack(Material.STONE_BUTTON)).onClick {
        next()
    }
    private val lastPageButton = MagicComponent(ItemStack(Material.STONE_BUTTON)).onClick {
        last()
    }

    fun next(): MultiplePageView {
        if (current >= layouts.size) {
            return this
        }

        current += 1
        layout = layouts[current]

        rerender()
        return this
    }

    fun last(): MultiplePageView {
        if (current <= 0) {
            return this
        }

        current -= 1
        layout = layouts[current]

        rerender()
        return this
    }

    fun setLayout(page: Int, layout: MagicLayout): MultiplePageView {
        layouts[page] = layout
        return this
    }

    fun addLayout(layout: MagicLayout): MultiplePageView {
        layouts.add(layout)
        return this
    }

    override fun render() {
        super.render()

        val player = inventory.viewers.first() as Player
        if (current > 0) {
            layout.setComponent(2, 5, lastPageButton.apply {
                itemStack.setName(getText("Last Page", player.locale))
            })
        }

        if (current < layouts.size - 1) {
            layout.setComponent(6, 5, nextPageButton.apply {
                itemStack.setName(getText("Next Page", player.locale))
            })
        }
    }
}