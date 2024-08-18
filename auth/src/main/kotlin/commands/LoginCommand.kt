package xyz.ldgame.corona.auth.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import xyz.ldgame.corona.auth.exceptions.InvalidPasswordException
import xyz.ldgame.corona.auth.exceptions.NoUserException
import xyz.ldgame.corona.auth.handler.AuthHandler
import xyz.ldgame.corona.auth.listener.PlayerAuthEvent
import xyz.ldgame.corona.auth.utils.isAuthenticated
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.translate

class LoginCommand : MagicCommand() {
    private val password by argument()

    override fun run() {
        val player = checkSenderType<Player>()

        if (player.isAuthenticated()) {
            "You have already login".translate(sender).color(ChatColor.RED).send(player)
            return
        }

        val username = player.name
        val handler = AuthHandler()

        try {
            handler.login(username, password)
            Bukkit.getPluginManager().callEvent(PlayerAuthEvent(player))
        } catch (e: InvalidPasswordException) {
            "Wrong password".translate(sender).color(ChatColor.RED).send(player)
        } catch (e: NoUserException) {
            "You need to register first.".translate(sender).color(ChatColor.RED).send(player)
        }
    }
}
