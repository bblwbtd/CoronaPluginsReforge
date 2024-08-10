package xyz.ldgame.corona.auth.commands

import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.corona.auth.exceptions.DuplicatedRegisterException
import xyz.ldgame.corona.auth.exceptions.InvalidPasswordException
import xyz.ldgame.corona.auth.handler.AuthHandler
import xyz.ldgame.corona.auth.listener.PlayerAuthEvent
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.i18n.translate

class RegisterCommand : MagicCommand() {
    private val password by argument()
    private val handler = AuthHandler()

    override fun commandHelp(context: Context): String {
        return "Password's length must be longer than 6.".translate(sender)
    }
    override fun run() {
        val player = checkSenderType<Player>()
        try {
            handler.register(player.name, password)
            Bukkit.getPluginManager().callEvent(PlayerAuthEvent(player))
        } catch (e: InvalidPasswordException) {
            "Invalid password.".translate(sender).color(ChatColor.RED).send(player)
        } catch (e: DuplicatedRegisterException) {
            "This user has already registered.".translate(sender).color(ChatColor.RED).send(player)
        }
    }
}
