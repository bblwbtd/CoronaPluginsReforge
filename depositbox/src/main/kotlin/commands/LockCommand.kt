package commands

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
import java.util.*


class LockCommand(sender: CommandSender?) : MagicCommand(sender, help = "Lock a chest.") {

    override fun run() {
        val player = checkSenderType<Player>()
        val targetBlock = player.getTargetBlockExact(10)
        val chestKey = player.inventory.itemInMainHand

        if (targetBlock?.type != Material.CHEST) {
            //没对准箱子
            player.sendMessage("Please aim at a Box!".locale(player).color(ChatColor.RED))
            return
        }

        //player没拿着key
        if (chestKey.itemMeta == null) {
            "Please select an available key!".locale(player).color(ChatColor.BLUE).send(player)
            return
        }
        //key不可用(key和chest存储字符串不一致)
        if (!keyCheck(chestKey, targetBlock)) {
            if (getUUID(targetBlock) != null) {
                "Already locked!".locale(player).color(ChatColor.YELLOW).send(player)
            } else {
                "This key has been use, please change another available key".locale(player).color(ChatColor.BLUE).send(player)
            }
            return
        }

        //钥匙种类正确并且对准箱子
        if (chestKey.type.toString() !in getKeyList()) {
            "Please check the materials that can be used as the key!".locale(player).color(ChatColor.YELLOW)
                .send(player)
            return
        }

        //检查箱子是否已经上锁
        val chestState = targetBlock.state as Chest
        val uuid = UUID.randomUUID().toString()
        if (chestState.inventory is DoubleChestInventory) {//如果对准大箱子
            val inv = chestState.inventory as DoubleChestInventory
            val leftChest = inv.leftSide.location?.block
            val rightChest = inv.rightSide.location?.block

            if (getUUID(leftChest) == null && getUUID(rightChest) == null) {
                if (setUUID(leftChest, uuid) && setUUID(rightChest, uuid)) {//第一次set
                    setKey(chestKey, uuid)
                    "Lock successfully!".locale(player).color(ChatColor.GREEN).send(player)
                }
            } else if (getUUID(leftChest) == null || getUUID(rightChest) == null) {//从一个deposit box拓展要再锁一次
                if (getUUID(leftChest) == null) {
                    getUUID(rightChest)?.let { setUUID(leftChest, it) }
                } else {
                    getUUID(leftChest)?.let { setUUID(rightChest, it) }
                }
                "Lock successfully!".send(player)
            } else {
                //已经set过了
                if (getUUID(targetBlock) == getKey(chestKey)) {
                    "Already locked!".color(ChatColor.YELLOW).send(player)
                }
            }

        } else {//最准小箱子
            if (setUUID(targetBlock, uuid)) {
                setKey(chestKey, uuid)
                "Lock successfully!".send(player)
            } else {
                //已经set过了
                if (getUUID(targetBlock) == getKey(chestKey)) {
                    "Already locked!".color(ChatColor.YELLOW).send(player)
                }
            }
        }


    }


}





