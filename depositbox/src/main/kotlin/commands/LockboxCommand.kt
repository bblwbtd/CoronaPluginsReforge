package commands

import command.InvalidSenderException
import command.MagicCommand
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Chest
import org.bukkit.block.TileState
import org.bukkit.entity.Player
import org.bukkit.inventory.DoubleChestInventory

import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType
import utils.*
import java.security.KeyPairGenerator
import java.security.PublicKey
import java.util.UUID


class LockboxCommand : MagicCommand() {

    override fun run() {
        if (sender !is Player) {
            throw InvalidSenderException("Invalid sender type.".locale(sender).color(ChatColor.RED))
        }

        val player = sender as Player
        val targetBlock = player.getTargetBlockExact(200)
        val chestKey = player.inventory.itemInMainHand

        if (targetBlock?.type != Material.CHEST){
            //没对准箱子
            player.sendMessage("Please aim at a Box!".locale(player).color(ChatColor.RED))
        }else{

            //key不可用(key和chest存储字符串不一致)
            if (!keyCheck(chestKey,targetBlock)){
                "This key has been use, please change another available key".color(ChatColor.BLUE).send(player)
                return
            }
            //key可以用
            if (chestKey.type.toString() in getKeyList()){
                //钥匙种类正确并且对准箱子
                //检查箱子是否已经上锁
                val chestState = targetBlock.state as Chest
                val uuid = UUID.randomUUID().toString()
                if (chestState.inventory is DoubleChestInventory) {
                    val inv = chestState.inventory as DoubleChestInventory
                    val leftChest = inv.leftSide.location?.block
                    val rightChest = inv.rightSide.location?.block

                    if (getUUID(leftChest)=="" && getUUID(rightChest)==""){
                        if (setUUID(leftChest,uuid) && setUUID(rightChest,uuid)){//第一次set
                            setKey(chestKey, uuid)
                            "Lock successfully!".send(player)
                        }
                    }else{
                        if (getUUID(leftChest)=="" || getUUID(rightChest)==""){//从一个depositbox拓展要再锁一次
                            when (getUUID(leftChest)) {
                                "" -> getUUID(rightChest)?.let { setUUID(leftChest, it) }
                                else -> getUUID(leftChest)?.let { setUUID(rightChest, it) }
                            }
                            "Lock successfully!".send(player)
                        }else {
                            //已经set过了
                            if (getUUID(targetBlock) == getKey(chestKey)){
                                "Already locked!".color(ChatColor.YELLOW).send(player)
                            }
                        }
                    }

                }else {
                    if (setUUID(targetBlock,uuid)){
                        setKey(chestKey, uuid)
                        "Lock successfully!".send(player)
                    }else{
                        //已经set过了
                        if (getUUID(targetBlock) == getKey(chestKey)){
                            "Already locked!".send(player)
                        }
                    }
                }


            }else{
                //拿的钥匙种类不对
                player.sendMessage("Please check the materials that can be used as the key!".locale(player).color(ChatColor.YELLOW))
            }

        }


    }


}





