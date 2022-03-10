package commands

import command.MagicCommand

class BoxCommand: MagicCommand() {

    override fun aliases(): Map<String, List<String>> {
        return mapOf(
            "l" to listOf("lock"),
            "k" to listOf("keys"),
            "lo" to listOf("lockbox")
        )
    }
}