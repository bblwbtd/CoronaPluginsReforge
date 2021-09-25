import com.github.ajalt.clikt.core.CliktCommand
import org.bukkit.command.CommandSender

abstract class CustomCommand(var sender: CommandSender? = null, var help: String = "", var name: String? = null) :
    CliktCommand(help = help, name = name) {

    internal fun passSender(sender: CommandSender) {
        this.sender = sender
        registeredSubcommands().forEach {
            it as CustomCommand
            it.passSender(sender)
        }
    }
}