package command

import com.github.ajalt.clikt.core.CliktCommand
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

abstract class MagicCommand(help: String = "", var name: String? = null) :
    CliktCommand(help = help, printHelpOnEmptyArgs = false, name = name) {

    lateinit var sender: CommandSender

    override fun run() {

    }

    internal fun passSender() {
        registeredSubcommands().forEach {
            it as MagicCommand
            it.sender = sender
            it.passSender()
        }
    }

    inline fun <reified T> checkSenderType() {
        if (sender !is T) {
            "Invalid sender.".locale(sender).color(ChatColor.RED).send(sender)
            throw InvalidSenderException()
        }
    }
}