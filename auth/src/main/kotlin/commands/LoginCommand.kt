package commands

import Main
import com.github.ajalt.clikt.parameters.arguments.argument
import command.InvalidSenderException
import command.MagicCommand
import handler.AuthHandler
import handler.InvalidPasswordException
import handler.NoUserException
import handler.PlayerState
import i18n.color
import i18n.locale
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import utils.getString

class LoginCommand : MagicCommand() {
    private val password by argument()


    override fun run() {
        if (sender !is Player) {
            throw InvalidSenderException("Invalid sender type.".locale(sender).color(ChatColor.RED))
        }

        val player = sender as Player

        if (PlayerState.AUTHENTICATED.name == player.getString(Main.plugin, "state")) {
            throw AuthenticatedException("You have already login".locale(sender).color(ChatColor.RED))
        }

        val username = player.name
        val handler = AuthHandler()

        try {
            handler.login(username, password)
            player.loadData()
            PlayerState.AUTHENTICATED.setState(player)
            player.sendMessage("Login successfully!".locale(sender).color(ChatColor.GREEN))
        } catch (e: InvalidPasswordException) {
            player.sendMessage("Wrong password".locale(sender).color(ChatColor.RED))
        } catch (e: NoUserException) {
            player.sendMessage("You need to register first.".locale(sender).color(ChatColor.RED))
        }
    }
}