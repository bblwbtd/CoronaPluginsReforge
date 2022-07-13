package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import command.MagicCommand
import handlers.RelationHandler
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RemoveCommand(sender: CommandSender?) : MagicCommand(sender, help = "Remove a friend.") {
    private val playerName by argument("player name", "The name of the ").default("")

    override fun run() {
        val player = checkSenderType<Player>()
        val player2 = Bukkit.getPlayer(playerName)
        if (player2 == null) {
            "No such player.".locale(sender).color(ChatColor.RED).send(player)
            return
        }

        val handler1 = RelationHandler(player)
        val handler2 = RelationHandler(player2)

        handler1.removeFriend(player2)
        handler2.removeFriend(player)

        "$playerName ".plus("is no longer your friend.".locale(player)).color(ChatColor.YELLOW).send(player)
    }
}