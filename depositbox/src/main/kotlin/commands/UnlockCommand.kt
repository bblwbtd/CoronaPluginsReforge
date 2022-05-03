package commands

import command.InvalidSenderException
import command.MagicCommand
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.DoubleChestInventory
import utils.*

class UnlockCommand(sender: CommandSender?): MagicCommand(sender) {
    override fun run() {
        if (sender !is Player) {
            throw InvalidSenderException()
        }

        val player = sender as Player
        val targetBlock = player.getTargetBlockExact(200)
        val chestKey = player.inventory.itemInMainHand

        if (targetBlock?.type != Material.CHEST){
            //没对准箱子
            player.sendMessage("Please aim at a Box!".locale(player).color(ChatColor.RED))
        }else{
            //player没拿着key
            if(chestKey.itemMeta == null){
                "Please select the corresponding key!".color(ChatColor.BLUE).send(player)
                return
            }
            //key不可用(key和chest存储字符串不一致)
            if (!keyCheck(chestKey,targetBlock)) {
                if (getUUID(targetBlock) != "") {
                    "Please select the corresponding key!".color(ChatColor.YELLOW).send(player)
                } else {
                    "This key has been use, please change another available key".color(ChatColor.BLUE).send(player)
                }
                return
            }

            if (chestKey.type.toString() in getKeyList()){
                //钥匙种类正确并且对准箱子
                //检查箱子是否已经上锁
                val chestState = targetBlock.state as Chest
                if (chestState.inventory is DoubleChestInventory) {//如果对准大箱子
                    val inv = chestState.inventory as DoubleChestInventory
                    val leftChest = inv.leftSide.location?.block
                    val rightChest = inv.rightSide.location?.block

                    if (getUUID(leftChest) == getUUID(rightChest)){
                        if (getUUID(leftChest) == getKey(chestKey)){//key对应
                            if (getUUID(leftChest) != "" && getKey(chestKey) != ""){
                                cleanKeyUUID(chestKey)
                                cleanChestUUID(leftChest)
                                cleanChestUUID(rightChest)
                                "Unlock successfully!".color(ChatColor.GREEN).send(player)
                            }else {
                                "This box has not been locked!".color(ChatColor.RED).send(player)
                            }

                        }
                    }

                }else {//最准小箱子
                    if (getUUID(targetBlock) == getKey(chestKey)){
                        if (getUUID(targetBlock) != "" && getKey(chestKey) != ""){
                            cleanKeyUUID(chestKey)
                            cleanChestUUID(targetBlock)
                            "Unlock successfully!".send(player)
                        }else{
                            "This box has not been locked!".color(ChatColor.RED).send(player)
                        }

                    }
                }

            }else {
                //拿的钥匙种类不对
                player.sendMessage("Please check the materials that can be used as the key!".locale(player).color(ChatColor.YELLOW))
            }
        }
    }

}