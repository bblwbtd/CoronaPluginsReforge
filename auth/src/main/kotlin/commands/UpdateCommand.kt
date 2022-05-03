package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handler.AuthHandler
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import utils.isAuthenticated

class UpdateCommand(sender: CommandSender?) : MagicCommand(sender) {
    private val newPassword by argument()

    override fun run() {
        if (sender !is Player) {
            "Invalid sender type.".locale(sender).color(ChatColor.RED).send(sender!!)
            return
        }

        val player = sender as Player

        if (player.isAuthenticated()) {
            AuthHandler().update(player.name, newPassword)
            "Update password successfully.".locale(sender).color(ChatColor.GREEN).send(player)
        } else {
            player.kickPlayer("You have been updated :)")
        }
    }
}