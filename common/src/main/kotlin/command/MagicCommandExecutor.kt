package command

import com.github.ajalt.clikt.core.NoSuchSubcommand
import com.github.ajalt.clikt.core.PrintHelpMessage
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
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
        } catch (e: NoSuchSubcommand) {
            "Unknown command.".locale(sender).color(ChatColor.RED).send(sender)
            return false
        } catch (e: Exception) {
            return false
        }
        return true
    }

}