package commands

import command.MagicCommand
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import utils.getKeyList

class KeysCommand(sender: CommandSender?) : MagicCommand(sender, help = "List all valid key material.") {
    override fun run() {
        val keyList = getKeyList()
        keyList.joinToString(", ").locale(sender).color(ChatColor.YELLOW).send(sender)
    }
}