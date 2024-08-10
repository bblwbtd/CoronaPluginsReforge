package xyz.ldgame.corona.common.i18n

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import xyz.ldgame.corona.common.utils.plus

fun String.color(color: ChatColor): String {
    return color + this
}

fun String.send(sender: CommandSender?) {
    sender?.sendMessage(this)
}

fun String.onClick(handleClick: () -> ClickEvent?): TextComponent {
    val event = handleClick()
    val textComponent = TextComponent(this)
    textComponent.clickEvent = event
    return textComponent
}

fun TextComponent.send(sender: CommandSender) {
    sender.spigot().sendMessage(this)
}

operator fun TextComponent.plus(s: String) {
    addExtra(s)
}

operator fun String.plus(s: TextComponent): TextComponent {
    return TextComponent(this).apply {
        addExtra(s)
    }
}

fun String.broadcast() {
    Bukkit.broadcastMessage(this)
}

