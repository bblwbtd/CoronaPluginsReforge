package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handler.AuthHandler
import exceptions.DuplicatedUserException
import exceptions.InvalidPasswordException
import i18n.color
import i18n.locale
import i18n.send
import listener.PlayerAuthEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RegisterCommand(sender: CommandSender?) : MagicCommand(sender) {
    private val password by argument()
    private val handler = AuthHandler()
    override val commandHelp: String
        get() = "Password's length must be longer than 6.".locale(sender)

    override fun run() {
        val player = checkSenderType<Player>()
        try {
            handler.register(player.name, password)
            Bukkit.getPluginManager().callEvent(PlayerAuthEvent(player))
        } catch (e: InvalidPasswordException) {
            "Invalid password.".locale(sender).color(ChatColor.RED).send(player)
        } catch (e: DuplicatedUserException) {
            "This user has already registered.".locale(sender).color(ChatColor.RED).send(player)
        }
    }
}
