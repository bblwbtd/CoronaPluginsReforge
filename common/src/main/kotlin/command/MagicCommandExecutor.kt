package command

import com.github.ajalt.clikt.core.PrintHelpMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

abstract class MagicCommandExecutor : CommandExecutor {

    abstract fun getCommand(): MagicCommand

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val magicCommand = getCommand()
        magicCommand.sender = sender
        magicCommand.passSender()
        try {
            magicCommand.parse(args)
        } catch (e: PrintHelpMessage) {
            val message = e.command.getFormattedHelp()
            sender.sendMessage(message)
            return true
        } catch (e: Exception) {
            return false
        }
        return true
    }

}