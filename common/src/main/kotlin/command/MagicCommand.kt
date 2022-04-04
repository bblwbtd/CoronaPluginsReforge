package command

import com.github.ajalt.clikt.core.CliktCommand
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

abstract class MagicCommand(var sender: CommandSender?, help: String = "", var name: String? = null) :
    CliktCommand(help = help.locale(sender), printHelpOnEmptyArgs = false, name = name?.locale(sender)) {

    override fun run() {

    }

    open fun getArgumentOptions(s: String): List<String> {
        return emptyList()
    }

    inline fun <reified T> checkSenderType(): T {
        if (sender == null) {
            throw InvalidSenderException()
        }
        if (sender !is T) {
            "Invalid sender.".locale(sender).color(ChatColor.RED).send(sender)
            throw InvalidSenderException()
        }

        return sender as T
    }
}