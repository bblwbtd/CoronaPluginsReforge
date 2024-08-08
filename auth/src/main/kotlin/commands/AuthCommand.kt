package xyz.ldgame.corona.auth.commands

import xyz.ldgame.corona.common.command.MagicCommand

class AuthCommand : MagicCommand() {

    override fun aliases(): Map<String, List<String>> {
        return mapOf(
            "l" to listOf("login"),
            "r" to listOf("register"),
            "u" to listOf("update")
        )
    }
}