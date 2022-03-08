package commands

import command.MagicCommand

class DepositBoxCommand: MagicCommand() {
    override fun run() {

    }

    override fun aliases(): Map<String, List<String>> {
        return mapOf(
            "getk" to listOf("getkeytype")
        )
    }
}