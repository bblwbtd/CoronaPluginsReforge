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

    override fun run() {
        checkSenderType<Player>()

        sender.sendMessage(page.toString())

        val book = LocationHandler(sender as Player).getUserAddressBook()

        if (book == null) {
            "You have no saved location.".locale(sender).send(sender)
            return
        }

        val limit = (book.address.size / 10) + 1

        if (page <= 0 || limit < page) {
            "Nothing to display.".locale(sender).color(ChatColor.YELLOW).send(sender)
            return
        }

        //
//        book?.run {
//            book.address.subList((page - 1) * ).map {
//                "${it.name}: ${it.world},${it.x.toInt()},${it.y.toInt()},${it.z.toInt()}"
//            }.joinToString("\n").plus("Page".locale(sender)).plus(": ${page}/${(book.address.size / 10) + 1}")
//        }?.send(sender as Player)


    }
}