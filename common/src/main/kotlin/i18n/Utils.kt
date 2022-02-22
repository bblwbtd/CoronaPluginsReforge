package i18n

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import utils.plus
import java.util.*

fun getText(rawText: String, locale: String? = "en_us"): String {
    return rawText
}

fun getText(rawText: String, sender: CommandSender? = null): String {
    if (sender is Player) {
        return getText(rawText, sender.locale)
    }

    if (sender is ConsoleCommandSender) {
        val currentLocal = Locale.getDefault()
        return getText(rawText, "${currentLocal.language}_${currentLocal.country}")
    }

    return getText(rawText, locale = null)
}

fun String.color(color: ChatColor): String {
    return color + this
}