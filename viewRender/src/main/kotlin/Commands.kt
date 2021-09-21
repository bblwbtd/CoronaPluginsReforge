import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

internal class ViewCommand : MagicCommand() {
    override fun run() {
        sender?.sendMessage(getFormattedHelp())
    }
}

internal class SinglePageCommand : MagicCommand() {
    override fun run() {
        val view = MagicView()
        view.layout = MagicLayout().apply {
            setComponent(0, 0, MagicComponent(ItemStack(Material.STONE).setName("test1")))
        }
    }
}

internal class MultiplePageCommand : MagicCommand() {
    override fun run() {
        if (sender == null || sender !is Player) {
            throw Error("invalid sender")
        }

        val layout1 = MagicLayout()
        val layout2 = MagicLayout()

        layout1.setComponent(0, 0, MagicComponent(ItemStack(Material.GLASS)))
        layout2.setComponent(0, 0, MagicComponent(ItemStack(Material.STONE)))

        val view = MultiplePageView().apply {
            addLayout(layout1)
            addLayout(layout2)
        }

        view.open(sender as Player)
    }
}









