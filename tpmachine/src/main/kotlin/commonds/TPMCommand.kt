package commonds

import command.MagicCommand

class TPMCommand : MagicCommand(help = "A tool for teleportation.") {
    override fun aliases(): Map<String, List<String>> {
        return mapOf()
    }
}