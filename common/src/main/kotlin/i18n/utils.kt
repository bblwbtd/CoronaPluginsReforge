package i18n

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import utils.plus
import java.util.*

fun String.locale(locale: CommandSender? = null): String {
    return getText(this, locale)
}

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

fun String.send(sender: CommandSender) {
    sender.sendMessage(this)
}

fun String.onClick(handleClick: () -> ClickEvent): TextComponent {
    val event = handleClick()
    val textComponent = TextComponent(this)
    textComponent.clickEvent = event
    return textComponent
}

fun TextComponent.send(sender: CommandSender) {
    sender.spigot().sendMessage(this)
}