package commands

import command.InvalidSenderException
import command.MagicCommand
import i18n.color
import i18n.locale
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import utils.isAuthenticated

class GetKeysTypeListCommand: MagicCommand() {

    override fun run() {
        if (sender !is Player) {
            throw InvalidSenderException("Invalid sender type.".locale(sender).color(ChatColor.RED))
        }

        val player = sender as Player

        if (player.isAuthenticated()){

        }
    }
}