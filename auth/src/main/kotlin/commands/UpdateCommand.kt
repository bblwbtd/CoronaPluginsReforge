package xyz.ldgame.corona.auth.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import net.md_5.bungee.api.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.corona.auth.handler.AuthHandler
import xyz.ldgame.corona.auth.utils.isAuthenticated
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.color

class UpdateCommand : MagicCommand() {
    private val newPassword by argument()

    override fun run() {
        val player = checkSenderType<Player>()

        if (player.isAuthenticated()) {
            AuthHandler().update(player.name, newPassword)
            "Update password successfully.".color(ChatColor.GREEN).send(player)
        } else {
            player.kickPlayer("You have been updated :)")
        }
    }
}