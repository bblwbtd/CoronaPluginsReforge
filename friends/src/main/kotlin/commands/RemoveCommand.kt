package xyz.ldgame.corona.friends.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import xyz.ldgame.corona.common.command.MagicCommand
import xyz.ldgame.corona.common.i18n.color
import xyz.ldgame.corona.common.i18n.send
import xyz.ldgame.corona.common.i18n.translate
import xyz.ldgame.corona.friends.RelationHandler

class RemoveCommand : MagicCommand(help = "Remove a friend.") {
    private val playerName by argument("player name", "The name of the ").default("")

    override fun run() {
        val player = checkSenderType<Player>()
        val player2 = Bukkit.getPlayer(playerName)
        if (player2 == null) {
            "No such player.".translate(sender).color(ChatColor.RED).send(player)
            return
        }

        val handler1 = RelationHandler(player)
        val handler2 = RelationHandler(player2)

        handler1.removeFriend(player2)
        handler2.removeFriend(player)

        "$playerName ".plus("is no longer your friend.".translate(player)).color(ChatColor.YELLOW).send(player)
    }
}