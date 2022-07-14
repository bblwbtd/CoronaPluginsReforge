package commands

import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import command.MagicCommand
import handler.DepositBoxHandler
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*


class LockCommand(sender: CommandSender?) : MagicCommand(sender, help = "Lock a chest.") {
    private val label by option(
        "label".locale(sender),
        help = "The label of the chest key".locale(sender)
    ).default(UUID.randomUUID().toString().slice(0..3))

    override fun run() {
        val player = checkSenderType<Player>()
        val targetBlock = player.getTargetBlockExact(10)

        if (targetBlock == null) {
            "Please aim a block or the block is too far from your.".locale(player).color(ChatColor.RED).send(player)
            return
        }

        DepositBoxHandler(player).lockBox(targetBlock, label)
    }
}





