package commands

import command.InvalidSenderException
import command.MagicCommand
import i18n.color
import i18n.locale
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player

import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataHolder
import utils.decodeByPrivateKey
import utils.getKeyList
import utils.entropyByPublicKey
import java.security.KeyPairGenerator
import java.security.PublicKey


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
            if (chestKey.type.toString() in getKeyList()){
                //钥匙种类正确并且对准箱子
//                player.sendMessage("ojbk!".locale(player).color(ChatColor.GREEN))
                //检查箱子是否已经上锁
                val generator=KeyPairGenerator.getInstance("RSA")
                val keyPair=generator.genKeyPair()
                val publicKey=keyPair.public    //public key
                val privateKey=keyPair.private  //private key
                val ciphertext = entropyByPublicKey(targetBlock.x,targetBlock.y,targetBlock.z,publicKey)    //ciphertext

//                targetBlock.setMetadata("publickey", publicKey)

//                player.sendMessage(ciphertext.locale(player).color(ChatColor.BLUE))
//                val decodeResult = decodeByPrivateKey(ciphertext, privateKey)
//                player.sendMessage(decodeResult.locale(player).color(ChatColor.YELLOW))





            }else{
                //拿的钥匙种类不对
                player.sendMessage("Please check the materials that can be used as the key!".locale(player).color(ChatColor.YELLOW))
            }

        }


    }


}





