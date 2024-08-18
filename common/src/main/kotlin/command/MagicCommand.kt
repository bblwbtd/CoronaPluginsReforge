package xyz.ldgame.corona.common.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.requireObject
import org.bukkit.command.CommandSender
import xyz.ldgame.corona.common.i18n.translate

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
    private val magicContext by requireObject<MagicContext>()
    val sender: CommandSender? by lazy {
        magicContext.sender
    }

    override fun commandHelp(context: Context): String {
        return super.commandHelp(context).translate(sender)
    }

    init {
        context {
            localization = CommandLocalization { sender }
        }
    }

    override fun run() {

    }

    open fun getArgumentOptions(s: String): List<String> {
        return emptyList()
    }

    inline fun <reified T> checkSenderType(): T {
        if (sender !is T) {
            throw InvalidSenderException()
        }

        return sender as T
    }

    open fun getTabCompleteOptions(): List<String> {
        return registeredSubcommandNames()
    }
}