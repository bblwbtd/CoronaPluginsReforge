package commonds

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.types.int
import command.MagicCommand
import handler.LocationHandler
import i18n.color
import i18n.locale
import i18n.onClick
import i18n.send
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import kotlin.math.min

class ListCommand : MagicCommand() {
    private val page by argument(help = "Page number").int().default(1)
    private val pageSize = 10

    override fun run() {
        val player = checkSenderType<Player>()

        val book = LocationHandler(player).getPlayerAddressBook()

        if (book == null) {
            "You have no saved location.".locale(sender).send(player)
            return
        }

        val limit = (book.address.size / 10) + 1

        if (page <= 0 || limit < page) {
            "Nothing to display.".locale(sender).color(ChatColor.YELLOW).send(player)
            return
        }
        ("Address book".locale(sender).color(ChatColor.YELLOW) + "(${page}/${limit}):\n").send(player)
        book.address.subList(
            (page - 1) * pageSize,
            min(page * pageSize, book.address.size)
        ).forEach {
            "${
                it.name.color(ChatColor.GREEN)
            }${ChatColor.WHITE}: ${it.world}, x: ${it.x.toInt()}, y: ${it.y.toInt()}, z: ${it.z.toInt()}".onClick {
                ClickEvent(ClickEvent.Action.OPEN_URL, "https://google.com")
            }.send(player)
        }
    }
}