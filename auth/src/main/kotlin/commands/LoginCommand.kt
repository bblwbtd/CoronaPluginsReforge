package commands

import Main
import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import command.SenderTypeException
import getText
import handler.LoginFailureException
import handler.login
import org.bukkit.Bukkit
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

        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin) { _ ->
            try {
                login(sender!!.name, password)
            } catch (e: LoginFailureException) {

            } catch (e: Exception) {

            }
        }

    }
}