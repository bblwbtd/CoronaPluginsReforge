package xyz.ldgame.coronaauth.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.coronaauth.handler.AuthHandler
import xyz.ldgame.coronaauth.utils.isAuthenticated

class UpdateCommand : MagicCommand() {
    private val newPassword by argument()

    override fun run() {
        val player = checkSenderType<Player>()

        if (player.isAuthenticated()) {
            AuthHandler().update(player.name, newPassword)
            "Update password successfully.".locale(sender).color(ChatColor.GREEN).send(player)
        } else {
            player.kickPlayer("You have been updated :)")
        }
    }
}