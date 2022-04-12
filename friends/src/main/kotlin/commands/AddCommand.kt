package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.MagicCommand
import handlers.RequestHandler
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddCommand(sender: CommandSender?) : MagicCommand(sender) {
    private val playerName by argument("player name".locale(sender))

    override fun run() {
        val player = checkSenderType<Player>()
        val to = Bukkit.getPlayer(playerName)
        if (to == null) {
            "Can not find the player".locale(player).color(ChatColor.RED).send(player)
            return
        }
        RequestHandler(player).sendRequest(to)
    }
}