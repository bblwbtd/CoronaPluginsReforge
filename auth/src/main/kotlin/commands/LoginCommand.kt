package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handler.*
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import utils.isAuthenticated

class LoginCommand : MagicCommand() {
    private val password by argument()

    override fun run() {
        if (sender !is Player) {
            "Invalid sender type.".locale(sender).color(ChatColor.RED).send(sender!!)
            return
        }

        val player = sender as Player

        if (player.isAuthenticated()) {
            "You have already login".locale(sender).color(ChatColor.RED).send(player)
            return
        }

        val username = player.name
        val handler = AuthHandler()

        try {
            handler.login(username, password)
            loadInventory(player)
            PlayerState.AUTHENTICATED.setState(player)
            "Login successfully!".locale(sender).color(ChatColor.GREEN).send(player)
        } catch (e: InvalidPasswordException) {
            "Wrong password".locale(sender).color(ChatColor.RED).send(player)
        } catch (e: NoUserException) {
            "You need to register first.".locale(sender).color(ChatColor.RED).send(player)
        }
    }
}
