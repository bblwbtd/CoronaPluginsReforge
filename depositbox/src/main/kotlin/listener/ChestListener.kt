package listener

import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.DoubleChestInventory
import utils.*

class ChestListener : Listener {

    @EventHandler
    fun preventBreak(event: BlockBreakEvent) {
        if (getUUID(event.block) == null) return
        val item = event.player.inventory.itemInMainHand
        if (!keyCheck(item, event.block)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun preventLeak(event: InventoryMoveItemEvent) {
        val location = event.source.location
        if (location?.block == null) return
        if (getUUID(location.block) != null) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun preventBlockExplosion(event: BlockExplodeEvent) {
        val iterator = event.blockList().iterator()
        while (iterator.hasNext()) {
            val block = iterator.next()
            if (getUUID(block) != null) {
                iterator.remove()
            }
        }
    }

    @EventHandler
    fun preventEntityExplosion(event: EntityExplodeEvent) {
        val iterator = event.blockList().iterator()
        while (iterator.hasNext()) {
            val block = iterator.next()
            if (getUUID(block) != null) {
                iterator.remove()
            }
        }
    }

    @EventHandler
    fun preventOpen(event: PlayerInteractEvent) {
        event.player.run {
            val isBlockLocked = getUUID(event.clickedBlock) != null
            if (!isBlockLocked) return
            val isKeyMatch = keyCheck(inventory.itemInMainHand, event.clickedBlock)
            if (!isKeyMatch) {
                val hint = getHint(event.clickedBlock!!)
                "Please use the corresponding key to open/destroy the chest!".locale(this).color(ChatColor.RED)
                    .send(player)
                if (hint != "" && hint != null) {
                    "Hint: ".locale(player).plus(hint).send(player)
                }
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun checkBoxExtension(event: PlayerInteractEvent) {
        if (event.clickedBlock?.type != Material.CHEST) {
            return
        }
        event.player.run {
            val chestState = event.clickedBlock?.state as Chest
            if (chestState.inventory is DoubleChestInventory) {
                val inv = chestState.inventory as DoubleChestInventory
                val leftChest = inv.leftSide.location?.block
                val rightChest = inv.rightSide.location?.block
                if (leftChest == null || rightChest == null) return

                val leftUUID = getUUID(leftChest)
                val rightUUID = getUUID(rightChest)

                if (leftUUID != null && rightUUID == null) {
                    val hint = getHint(leftChest) ?: ""
                    setUUID(rightChest, leftUUID, hint)
                } else if (rightUUID != null && leftUUID == null) {
                    val hint = getHint(rightChest) ?: ""
                    setUUID(leftChest, rightUUID, hint)
                }
            }
        }

    }

}


