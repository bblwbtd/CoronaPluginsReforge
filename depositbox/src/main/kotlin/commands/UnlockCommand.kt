package commands

import command.MagicCommand
import handler.DepositBoxHandler
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class UnlockCommand(sender: CommandSender?) : MagicCommand(sender, help = "Unlock a chest permanently.") {
    override fun run() {
        val player = checkSenderType<Player>()
        val targetBlock = player.getTargetBlockExact(10)

        if (targetBlock == null) {
            "Please aim a block or the block is too far from your.".locale(player).color(ChatColor.RED).send(player)
            return
        }

        DepositBoxHandler(player).unlockBox(targetBlock, player.isOp)
    }

}