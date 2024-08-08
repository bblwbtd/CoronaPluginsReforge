package xyz.ldgame.corona.common.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.requireObject
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.locale
import xyz.ldgame.corona.common.i18n.send

abstract class MagicCommand(
    var name: String? = null,
    val help: String = "",
    invokeWithoutSubcommand: Boolean = false
) :
    CliktCommand(
        name = name,
        help = help,
        printHelpOnEmptyArgs = false,
        invokeWithoutSubcommand = invokeWithoutSubcommand
    ) {
    private val context by requireObject<MagicContext>()
    val sender: CommandSender? by lazy {
        context.sender
    }

    override fun commandHelp(context: Context): String {
        return super.commandHelp(context).locale(sender)
    }

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

    open fun getTabCompleteOptions(): List<String> {
        return registeredSubcommandNames()
    }
}