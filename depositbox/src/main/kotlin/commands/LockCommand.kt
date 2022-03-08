package commands

import com.github.ajalt.clikt.parameters.arguments.argument
import command.InvalidSenderException
import command.MagicCommand
import i18n.color
import i18n.locale
import org.bukkit.ChatColor
import org.bukkit.block.DoubleChest
import org.bukkit.block.Chest
import org.bukkit.entity.Player


class LockCommand : MagicCommand() {

    private fun initPlayer() : Player{
        if (sender !is Player) {
            throw InvalidSenderException("Invalid sender type.".locale(sender).color(ChatColor.RED))
        }
        return sender as Player
    }

    private val player = initPlayer()
    private val targetBlock = player.getTargetBlockExact(200)






    override fun run() {
        if (targetBlock !is Chest && targetBlock !is DoubleChest){
            player.sendMessage("Please aim at a Box!".locale(sender).color(ChatColor.RED))
        }


    }


}