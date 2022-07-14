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
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.DoubleChestInventory
import utils.*

class ChestListener : Listener {

    @EventHandler
    fun preventBreak(event: BlockBreakEvent) {
        if (getUUID(event.block) == null) return
        val item = event.player.inventory.itemInMainHand
        if (!keyCheck(item, event.block)) event.isCancelled = true
    }

    @EventHandler
    fun preventLeak(event: InventoryMoveItemEvent) {
        val location = event.initiator.location
        if (location?.block == null) return
        if (getUUID(location.block) != null) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun preventBlockExplosion(event: BlockExplodeEvent) {
        event.blockList().removeIf {
            getUUID(it) != null
        }
    }

    @EventHandler
    fun preventEntityExplosion(event: BlockExplodeEvent) {
        event.blockList().removeIf {
            getUUID(it) != null
        }
    }

    @EventHandler
    fun preventOpen(event: PlayerInteractEvent) {
        event.player.run {
            if (!keyCheck(inventory.itemInMainHand, event.clickedBlock) && getUUID(event.clickedBlock) != null) {
                player?.let {
                    "Please use the corresponding key to open/destroy the chest!".locale(this).color(ChatColor.RED)
                        .send(it)
                }
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun checkBoxExtension(event: PlayerInteractEvent) {
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
                    setUUID(rightChest, leftUUID)
                } else if (rightUUID != null && leftUUID == null) {
                    setUUID(leftChest, rightUUID)
                }

            }
        }

    }

    @EventHandler
    fun clearKey(event: BlockBreakEvent) {
        event.player.run {
            if (event.block.type == Material.CHEST) {
                val chestState = event.block.state as Chest
                if (chestState.inventory !is DoubleChestInventory) {
                    cleanKeyUUID(inventory.itemInMainHand)
                } else {
                    event.isCancelled = false
                }
            }
        }
    }
}


