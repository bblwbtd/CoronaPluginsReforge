package commonds

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.types.int
import command.MagicCommand
import handler.LocationHandler
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class ListCommand : MagicCommand() {
    private val page by argument(help = "Page number").int().default(1)
    private val pageSize = 10

    override fun run() {
        val player = checkSenderType<Player>()

        sender.sendMessage(page.toString())

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

        book.address.subList((page - 1) * pageSize, page * pageSize).joinToString("\n") {
            "${it.name}: ${it.world},${it.x.toInt()},${it.y.toInt()},${it.z.toInt()}"
        }.send(sender)
    }
}