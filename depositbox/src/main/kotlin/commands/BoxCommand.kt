package commands

import command.MagicCommand

class BoxCommand: MagicCommand() {
    override fun run() {

    }

    override fun aliases(): Map<String, List<String>> {
        return mapOf(
            "l" to listOf("lock")
        )
    }
}