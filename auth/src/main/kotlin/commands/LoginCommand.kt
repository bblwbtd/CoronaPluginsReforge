package commands

import command.MagicCommand
import getText

class LoginCommand : MagicCommand() {
    override fun getFormattedHelp(): String {
        return getText("Login with your password.")
    }

    override fun run() {
        TODO("Not yet implemented")
    }
}