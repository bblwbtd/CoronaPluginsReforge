import com.github.ajalt.clikt.core.PrintHelpMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

abstract class CustomCommandExecutor : CommandExecutor {

    abstract fun getCommand(): CustomCommand

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val magicCommand = getCommand()
        magicCommand.passSender(sender)
        try {
            magicCommand.parse(args)
            return true
        } catch (e: PrintHelpMessage) {
            val message = e.command.getFormattedHelp()
            sender.sendMessage(message)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

}