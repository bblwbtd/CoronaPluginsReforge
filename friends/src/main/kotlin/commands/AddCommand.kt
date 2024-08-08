package xyz.ldgame.corona.friends.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.friends.RequestHandler
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.locale
import xyz.ldgame.corona.common.i18n.send
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class AddCommand : MagicCommand(help = "Send a friend request.") {
    private val playerName by argument("player_name".locale(sender))

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