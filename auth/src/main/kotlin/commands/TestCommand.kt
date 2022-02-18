package commands

import command.MagicCommand
import command.PrivilegeError
import org.bukkit.entity.Player
import pages.LoginPage

class TestCommand : MagicCommand() {
    override fun run() {
        if (sender?.isOp != true) {
            throw PrivilegeError(sender)
        }

        LoginPage().open(sender as Player)
    }
}