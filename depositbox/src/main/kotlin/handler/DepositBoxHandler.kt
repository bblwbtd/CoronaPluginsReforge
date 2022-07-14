package handler

import expections.InvalidKeyMaterialException
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.Chest
import org.bukkit.entity.Player
import org.bukkit.inventory.DoubleChestInventory
import org.bukkit.inventory.ItemStack
import utils.*
import java.util.*

class DepositBoxHandler(private val player: Player) {

    fun copyKey(from: ItemStack, to: ItemStack) {
        val secret = getKey(from) ?: return
        val label = from.itemMeta?.displayName ?: ""

        if (to.type.name !in getKeyList()) {
            throw InvalidKeyMaterialException()
        }

        setKey(to, secret, label)
        "Copy successfully!".locale(player).color(ChatColor.GREEN).send(player)
    }

    fun lockBox(block: Block, label: String) {
        if (block.type != Material.CHEST) {
            "Please aim at a chest!".locale(player).color(ChatColor.RED).send(player)
            return
        }

        if (getUUID(block) != null) {
            "Already locked!".locale(player).color(ChatColor.YELLOW).send(player)
            return
        }

        val chestKey = player.inventory.itemInMainHand
        if (chestKey.type.toString() !in getKeyList()) {
            "Please check the materials that can be used as the key!".locale(player).color(ChatColor.YELLOW)
                .send(player)
            return
        }


        val chestState = block.state as Chest
        val uuid = UUID.randomUUID().toString()
        setKey(chestKey, uuid, label)
        if (chestState.inventory is DoubleChestInventory) {
            val inv = chestState.inventory as DoubleChestInventory
            val leftChest = inv.leftSide.location?.block!!
            val rightChest = inv.rightSide.location?.block!!

            setUUID(leftChest, uuid)
            setUUID(rightChest, uuid)
        } else {
            setUUID(block, uuid)
        }
        "Chest is locked".locale(player).color(ChatColor.GREEN).send(player)
    }

    fun unlockBox(block: Block) {
        if (block.type != Material.CHEST) {
            "Please aim at a chest!".locale(player).color(ChatColor.RED).send(player)
            return
        }

        if (getUUID(block) == null) {
            "This chest isn't locked".locale(player).color(ChatColor.RED).send(player)
            return
        }

        val chestKey = player.inventory.itemInMainHand
        if (chestKey.type == Material.AIR) {
            "Please select the corresponding key!".locale(player).color(ChatColor.BLUE).send(player)
            return
        }

        if (!keyCheck(chestKey, block)) {
            "You need to hold the key in your main hand before unlocking".locale(player).color(ChatColor.RED)
                .send(player)
        }

        val chestState = block.state as Chest
        if (chestState.inventory is DoubleChestInventory) {
            val inv = chestState.inventory as DoubleChestInventory
            val leftChest = inv.leftSide.location?.block
            val rightChest = inv.rightSide.location?.block

            cleanChestUUID(leftChest)
            cleanChestUUID(rightChest)
        } else {
            cleanChestUUID(block)
        }
        "Chest is unlocked!".locale(player).color(ChatColor.RED).send(player)
    }
}
