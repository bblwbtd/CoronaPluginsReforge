import com.github.ajalt.clikt.core.subcommands
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun generateCommand(): CustomCommand {
    return ViewCommand().subcommands(SinglePageCommand(), MultiplePageCommand())
}

internal class ViewCommandExecutor : CustomCommandExecutor() {
    override fun getCommand(): CustomCommand {
        return generateCommand()
    }
}

internal class ViewCommand : CustomCommand() {
    override fun run() {
        val subCommand = currentContext.invokedSubcommand
        if (subCommand == null) {
            sender?.sendMessage(getFormattedHelp())
        }
    }
}

internal class SinglePageCommand : CustomCommand(name = "single", help = "open a single page view") {
    override fun run() {
        if (sender == null || sender !is Player) {
            throw Error("invalid sender")
        }

        val view = MagicView()
        view.addLayout(MagicLayout().apply {
            setComponent(0, 0, MagicComponent(ItemStack(Material.STONE).setName("test1")))
        })
        view.open(sender as Player)
    }
}

internal class MultiplePageCommand : CustomCommand(name = "multiple", help = "open a multiple page view") {
    override fun run() {
        if (sender == null || sender !is Player) {
            throw Error("invalid sender")
        }

        val layout1 = MagicLayout()
        val layout2 = MagicLayout()

        layout1.setComponent(0, 0, MagicComponent(ItemStack(Material.GLASS)))
        layout2.setComponent(0, 0, MagicComponent(ItemStack(Material.STONE)))

        val view = MagicView().apply {
            addLayout(layout1)
            addLayout(layout2)
        }

        view.open(sender as Player)
    }
}









