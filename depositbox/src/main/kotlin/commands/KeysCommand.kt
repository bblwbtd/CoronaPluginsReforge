package commands

import command.MagicCommand
import handler.DepositBoxHandler
import i18n.color
import i18n.locale
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class KeysCommand(sender: CommandSender?): MagicCommand(sender) {

    private val handler = DepositBoxHandler()

    override fun run() {
        val player = checkSenderType<Player>()
        val keyList = handler.getValidKeyList()
        player.sendMessage(keyList.joinToString(", ").locale(sender).color(ChatColor.YELLOW))
    }
}