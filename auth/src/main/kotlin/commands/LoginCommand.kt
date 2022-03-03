package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import command.SenderTypeException
import handler.AuthHandler
import handler.InvalidPasswordException
import handler.PlayerState
import i18n.color
import i18n.getText
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class LoginCommand : MagicCommand() {
    private val password by argument()

    override fun getFormattedHelp(): String {
        return getText("Login with your password.", sender)
    }

    override fun run() {
        if (sender !is Player) {
            throw SenderTypeException()
        }

        val player = sender as Player
        val username = player.name
        val handler = AuthHandler()

        try {
            handler.login(username, password)
            player.loadData()
            PlayerState.AUTHENTICATED.setState(player)
        } catch (e: InvalidPasswordException) {
            player.sendMessage(getText("Wrong password.", player.locale).color(ChatColor.RED))
        }
    }
}