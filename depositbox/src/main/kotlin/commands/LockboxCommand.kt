package commands

import command.InvalidSenderException
import command.MagicCommand
import i18n.color
import i18n.locale
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.DoubleChest
import org.bukkit.block.Chest
import org.bukkit.entity.Player
import utils.getKeyList


class LockboxCommand : MagicCommand() {

    override fun run() {
        if (sender !is Player) {
            throw InvalidSenderException("Invalid sender type.".locale(sender).color(ChatColor.RED))
        }

        val player = sender as Player
        val targetBlock = player.getTargetBlockExact(200)?.type
        val chestKey = player.inventory.itemInMainHand

        if (targetBlock != Material.CHEST){
            //没对准箱子
            player.sendMessage("Please aim at a Box!".locale(player).color(ChatColor.RED))
        }else{
            //对准箱子
            if (chestKey.type.toString() in getKeyList()){
                //钥匙种类正确并且对准箱子
                player.sendMessage("ojbk!".locale(player).color(ChatColor.GREEN))

            }else{
                //拿的钥匙种类不对
                player.sendMessage("wdnmd!".locale(player).color(ChatColor.GREEN))
            }

        }


    }


}