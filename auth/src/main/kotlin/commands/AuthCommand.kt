package commands

import command.MagicCommand

class AuthCommand : MagicCommand() {

    override fun run() {
        sender?.sendMessage(getFormattedHelp())
    }

    override fun aliases(): Map<String, List<String>> {
        return mapOf(
            "l" to listOf("login")
        )
    }
}