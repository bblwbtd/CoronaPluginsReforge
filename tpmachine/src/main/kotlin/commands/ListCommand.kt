package xyz.ldgame.corona.tpmachine.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.types.int
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.tpmachine.AddressHandler
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.locale
import xyz.ldgame.corona.common.i18n.onClick
import xyz.ldgame.corona.common.i18n.send
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import kotlin.math.min

class ListCommand : MagicCommand(help = "List all saved addresses.") {
    private val page by argument(help = "Page number".locale(sender)).int().default(1)
    private val pageSize = 9

    override fun run() {
        val player = checkSenderType<Player>()

        val book = AddressHandler(player).getPlayerAddressBook()

        var limit = book.address.size / pageSize
        if (book.address.size % pageSize > 0) {
            limit += 1
        }
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
                ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpm to ${it.name}")
            }.send(player)
        }
    }


}