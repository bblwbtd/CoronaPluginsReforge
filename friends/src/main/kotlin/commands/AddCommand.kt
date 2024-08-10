package xyz.ldgame.corona.friends.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.i18n.translate
import xyz.ldgame.corona.friends.RequestHandler

class AddCommand : MagicCommand(help = "Send a friend request.") {
    private val playerName by argument("player_name".translate(sender))

    override fun run() {
        val player = checkSenderType<Player>()
        val to = Bukkit.getPlayer(playerName)
        if (to == null) {
            "Can not find the player".translate(player).color(ChatColor.RED).send(player)
            return
        }
        RequestHandler(player).sendRequest(to)
    }
}