package xyz.ldgame.corona.common.command

import com.github.ajalt.clikt.core.PrintHelpMessage
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.parse
import net.md_5.bungee.api.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.send

abstract class MagicCommandExecutor : CommandExecutor {

    abstract fun getCommand(): MagicCommand

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val magicCommand = getCommand()
        magicCommand.context {
            obj = MagicContext(sender)
            helpFormatter = { ctx -> PlaintextHelpFormatter(ctx) }
        }

        try {
            magicCommand.parse(args)
        } catch (e: PrintHelpMessage) {
            e.context?.command?.getFormattedHelp()?.send(sender)
            return true
        } catch (e: UsageError) {
            e.context?.command?.getFormattedHelp(e)?.color(ChatColor.RED)?.send(sender)
            return false
        } catch (e: InvalidSenderException) {
            "invalidSender".color(ChatColor.RED).send(sender)
            return false
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }

}