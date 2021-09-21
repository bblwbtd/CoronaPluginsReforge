import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintHelpMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

abstract class MagicCommand(var sender: CommandSender? = null, var help: String = "") :
    CliktCommand(help = help, printHelpOnEmptyArgs = true), CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        this.sender = sender
        passSender()
        try {
            parse(args)
        } catch (e: PrintHelpMessage) {
            val message = e.command.getFormattedHelp()
            sender.sendMessage(message)
            echo(message)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun passSender() {
        registeredSubcommands().forEach {
            it as MagicCommand
            it.sender = sender
            it.passSender()
        }
    }
}