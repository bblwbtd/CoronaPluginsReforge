package xyz.ldgame.corona.auth.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.corona.auth.handler.AuthHandler
import xyz.ldgame.corona.auth.utils.isAuthenticated
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.i18n.translate

class UpdateCommand : MagicCommand() {
    private val newPassword by argument()

    override fun run() {
        val player = checkSenderType<Player>()

        if (player.isAuthenticated()) {
            AuthHandler().update(player.name, newPassword)
            "Update password successfully.".translate(sender).color(ChatColor.GREEN).send(player)
        } else {
            player.kickPlayer("You have been updated :)")
        }
    }
}