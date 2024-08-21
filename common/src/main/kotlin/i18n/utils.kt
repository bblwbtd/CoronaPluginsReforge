package xyz.ldgame.corona.common.i18n

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

fun String.color(color: org.bukkit.ChatColor): MagicString {
    return MagicString(this).color(color.asBungee())
}

fun String.color(color: ChatColor): MagicString {
    return MagicString(this).color(color)
}

fun String.send(sender: CommandSender?, variables: Map<String, String> = emptyMap()) {
    MagicString(this).send(sender ?: return, variables)
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

