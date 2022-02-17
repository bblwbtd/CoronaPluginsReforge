package commands

import command.MagicCommand
import command.PrivilegeError

class TestCommand : MagicCommand() {
    override fun run() {
        if (sender?.isOp != true) {
            throw PrivilegeError(sender)
        }


    }
}