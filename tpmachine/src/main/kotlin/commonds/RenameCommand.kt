package commonds

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand

class RenameCommand : MagicCommand() {
    val oldName by argument()

}