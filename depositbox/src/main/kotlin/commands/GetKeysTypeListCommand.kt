package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.InvalidSenderException
import command.MagicCommand
import handler.DepositBoxHandler
import handler.UnauthorizedException
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import utils.isAuthenticated

class GetKeysTypeListCommand: MagicCommand() {

    private val handler = DepositBoxHandler()

    override fun run() {
        if (sender !is Player) {
            "Invalid sender type.".locale(sender).color(ChatColor.RED).send(sender!!)
            return
        }

        val player = sender as Player

        try {
            if (player.isAuthenticated()){
                val keyList = handler.getValidKeyList()
                val resultList = ""
                keyList.forEach{resultList+it}
                player.sendMessage(resultList+"sss".locale(sender).color(ChatColor.YELLOW))
            }
        }catch (exception: UnauthorizedException){
            player.sendMessage("Please login to confirm your identity!".locale(sender).color(ChatColor.RED))
        }


    }
}