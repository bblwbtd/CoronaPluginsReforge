package xyz.ldgame.corona.common.command

import com.github.ajalt.clikt.core.*
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.locale
import xyz.ldgame.corona.common.i18n.send
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

abstract class MagicCommandExecutor : CommandExecutor {

    abstract fun getCommand(): MagicCommand

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val magicCommand = getCommand()
        magicCommand.context {
            obj = MagicContext(sender)
        }
        try {
            magicCommand.parse(args)
        } catch (e: PrintHelpMessage) {
            val message = e.context?.command?.getFormattedHelp()
            sender.sendMessage(message)
            return true
        } catch (e: NoSuchSubcommand) {
            "Unknown command.".locale(sender).color(ChatColor.RED).send(sender)
            return false
        } catch (e: Exception) {
            when (e) {
                is MissingArgument, is MissingOption -> {
                    "Missing some arguments or options".locale(sender).plus(": ${e.message}").color(ChatColor.RED)
                        .send(sender)
                }
                is UsageError -> {
                    "Usage error".locale(sender).plus(": ${e.message}").color(ChatColor.RED).send(sender)
                }
                else -> {
                    e.printStackTrace()
                }
            }
            return false
        }
        return true
    }

}