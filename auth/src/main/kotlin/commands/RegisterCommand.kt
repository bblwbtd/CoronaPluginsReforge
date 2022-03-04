package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.InvalidSenderException
import command.MagicCommand
import handler.AuthHandler
import handler.DuplicatedUserException
import handler.InvalidPasswordException
import handler.PlayerState
import i18n.color
import i18n.locale
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class RegisterCommand : MagicCommand() {
    private val password by argument()
    private val handler = AuthHandler()
    override val commandHelp: String
        get() = "Password's length must be longer than 6.".locale(sender)

    override fun run() {
        if (sender !is Player) {
            throw InvalidSenderException("Invalid sender type.".locale(sender).color(ChatColor.RED))
        }

        val player = sender as Player
        try {
            handler.register(player.name, password)
            player.sendMessage("Register successfully.".locale(sender).color(ChatColor.GREEN))
            player.loadData()
            PlayerState.AUTHENTICATED.setState(player)
        } catch (e: InvalidPasswordException) {
            player.sendMessage("Invalid password.".locale(sender).color(ChatColor.RED))
        } catch (e: DuplicatedUserException) {
            player.sendMessage("This user has already registered.".locale(sender).color(ChatColor.RED))
        }
    }
}