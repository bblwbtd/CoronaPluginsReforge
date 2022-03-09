package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handler.*
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class RegisterCommand : MagicCommand() {
    private val password by argument()
    private val handler = AuthHandler()
    override val commandHelp: String
        get() = "Password's length must be longer than 6.".locale(sender)

    override fun run() {
        checkSenderType<Player>()

        val player = sender as Player
        try {
            handler.register(player.name, password)
            "Register successfully.".locale(sender).color(ChatColor.GREEN).send(player)
            loadInventory(player)
            PlayerState.AUTHENTICATED.setState(player)
        } catch (e: InvalidPasswordException) {
            "Invalid password.".locale(sender).color(ChatColor.RED).send(player)
        } catch (e: DuplicatedUserException) {
            "This user has already registered.".locale(sender).color(ChatColor.RED).send(player)
        }
    }
}
