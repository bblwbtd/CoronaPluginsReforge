package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.InvalidSenderException
import command.MagicCommand
import handler.AuthHandler
import i18n.color
import i18n.locale
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import utils.isAuthenticated

class UpdateCommand : MagicCommand() {
    private val newPassword by argument()

    override fun run() {
        if (sender !is Player) {
            throw InvalidSenderException("Invalid sender type.".locale(sender).color(ChatColor.RED))
        }

        val player = sender as Player

        if (player.isAuthenticated()) {
            AuthHandler().update(player.name, newPassword)
            player.sendMessage("Update password successfully.".locale(sender).color(ChatColor.GREEN))
        } else {
            player.kickPlayer("")
        }
    }
}