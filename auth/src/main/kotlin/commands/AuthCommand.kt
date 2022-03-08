package commands

import command.MagicCommand

class AuthCommand : MagicCommand() {

    override fun run() {

    }

    override fun aliases(): Map<String, List<String>> {
        return mapOf(
            "l" to listOf("login"),
            "r" to listOf("register"),
            "u" to listOf("update")
        )
    }
}