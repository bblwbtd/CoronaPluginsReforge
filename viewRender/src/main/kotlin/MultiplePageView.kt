import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class MultiplePageView : MagicView() {
    var current = 0
    val layouts = ArrayList<MagicLayout>()
    val nextPageButton = MagicComponent(ItemStack(Material.STONE_BUTTON), false)

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

}