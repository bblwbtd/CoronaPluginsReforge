package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import command.SenderTypeException
import handler.login
import i18n.getText
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

        login((sender as Player).name, password)
    }
}