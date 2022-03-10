package commands

import command.MagicCommand
import handler.DepositBoxHandler
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class KeysCommand: MagicCommand() {

    private val handler = DepositBoxHandler()

    override fun run() {
        if (sender !is Player) {
            "Invalid sender type.".locale(sender).color(ChatColor.RED).send(sender!!)
            return
        }

        val player = sender as Player

        val keyList = handler.getValidKeyList()
        player.sendMessage(keyList.joinToString(", ").locale(sender).color(ChatColor.YELLOW))

    }
}