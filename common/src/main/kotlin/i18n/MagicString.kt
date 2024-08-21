package xyz.ldgame.corona.common.i18n

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MagicString(
    val string: String,
    var color: ChatColor? = null,
    var clickEvent: ClickEvent? = null
) {
    var previousString: MagicString? = null

    fun onClick(clickEvent: ClickEvent?): MagicString {
        this.clickEvent = clickEvent
        return this
    }

    fun color(color: ChatColor): MagicString {
        this.color = color
        return this
    }

    operator fun plus(s: MagicString): MagicString {
        s.previousString = this
        return s
    }

    operator fun plus(s: String): MagicString {
        return this + MagicString(s)
    }

    fun toTextComponent(locale: String = "en", variables: Map<String, String> = emptyMap()): TextComponent {
        val currentComponent = TextComponent(getText(string, locale, variables))
        currentComponent.clickEvent = clickEvent
        currentComponent.color = color

        if (previousString != null) {
            val previousComponent = previousString!!.toTextComponent(locale, variables)
            previousComponent.addExtra(currentComponent)
            return previousComponent
        }

        return currentComponent
    }


    fun send(sender: CommandSender?, variables: Map<String, String> = emptyMap()) {
        sender?.spigot()?.sendMessage(
            toTextComponent(
                if (sender is Player) sender.locale else "en",
                variables
            )
        )
    }
}