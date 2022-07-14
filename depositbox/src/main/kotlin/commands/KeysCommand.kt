package commands

import command.MagicCommand
import i18n.color
import i18n.locale
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import utils.getKeyList

class KeysCommand(sender: CommandSender?) : MagicCommand(sender) {
    override fun run() {
        val player = checkSenderType<Player>()
        val keyList = getKeyList()
        player.sendMessage(keyList.joinToString(", ").locale(sender).color(ChatColor.YELLOW))
    }
}