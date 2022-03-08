package viewRender

import com.github.ajalt.clikt.core.subcommands
import command.MagicCommand
import command.MagicCommandExecutor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import utils.setName

fun generateCommand(): MagicCommand {
    return ViewCommand().subcommands(SinglePageCommand(), MultiplePageCommand())
}

internal class ViewCommandExecutor : MagicCommandExecutor() {
    override fun getCommand(): MagicCommand {
        return generateCommand()
    }
}

internal class ViewCommand : MagicCommand() {
    override fun run() {
        val subCommand = currentContext.invokedSubcommand
        if (subCommand == null) {
            sender?.sendMessage(getFormattedHelp())
        }
    }
}

internal class SinglePageCommand : MagicCommand(name = "single", help = "open a single page view") {
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

internal class MultiplePageCommand : MagicCommand(name = "multiple", help = "open a multiple page view") {
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









