package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handler.AuthHandler
import exceptions.InvalidPasswordException
import exceptions.NoUserException
import i18n.color
import i18n.locale
import i18n.send
import listener.PlayerAuthEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import utils.isAuthenticated

class LoginCommand(sender: CommandSender?) : MagicCommand(sender) {
    private val password by argument()

    override fun run() {
        val player = checkSenderType<Player>()

        if (player.isAuthenticated()) {
            "You have already login".locale(sender).color(ChatColor.RED).send(player)
            return
        }

        val username = player.name
        val handler = AuthHandler()

        try {
            handler.login(username, password)
            Bukkit.getPluginManager().callEvent(PlayerAuthEvent(player))
        } catch (e: InvalidPasswordException) {
            "Wrong password".locale(sender).color(ChatColor.RED).send(player)
        } catch (e: NoUserException) {
            "You need to register first.".locale(sender).color(ChatColor.RED).send(player)
        }
    }
}
