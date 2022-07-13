package listener


import handler.DepositBoxHandler
import i18n.color
import i18n.send
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.DoubleChestInventory
import utils.*


class KeyListListener(): Listener {

    private val depositBoxHandler = DepositBoxHandler()

    @EventHandler
    fun getKeyListsByCommand(event: PlayerCommandPreprocessEvent) {
        event.player.run {
            depositBoxHandler.getValidKeyList()
        }
    }

    @EventHandler
    fun checkDepositBoxChange(event: PlayerInteractEvent) {
        event.player.run {
            if (event.clickedBlock?.type == Material.CHEST) {
                //检查小箱子
                if (!keyCheck(inventory.itemInMainHand, event.clickedBlock) && getUUID(event.clickedBlock) != null) {
                    player?.let {
                        "Please use the corresponding key to open/destroy the chest!".color(ChatColor.RED).send(it)
                    }
                    event.isCancelled = true
                }

            }
        }
    }

    @EventHandler
    fun checkDoubleChestChange(event: PlayerInteractEvent) {
        event.player.run {
            val chestState = event.clickedBlock?.state as Chest
            if (chestState.inventory is DoubleChestInventory){
                val inv = chestState.inventory as DoubleChestInventory
                val leftChest = inv.leftSide.location?.block
                val rightChest = inv.rightSide.location?.block

                if ((!keyCheck(inventory.itemInMainHand, leftChest) || !keyCheck(inventory.itemInMainHand, rightChest)) && getUUID(event.clickedBlock) != "") {
                    event.isCancelled = true
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

    @EventHandler
    fun checkBoxExtension(event: PlayerInteractEvent) {
        event.player.run {
            val chestState = event.clickedBlock?.state as Chest
            if (chestState.inventory is DoubleChestInventory) {
                val inv = chestState.inventory as DoubleChestInventory
                val leftChest = inv.leftSide.location?.block
                val rightChest = inv.rightSide.location?.block
                if (getUUID(leftChest) == null || getUUID(rightChest) == null ) {
                    if (getUUID(leftChest) == null) {
                        getUUID(rightChest)?.let { setUUID(leftChest, it) }
                    } else {
                        getUUID(leftChest)?.let { setUUID(rightChest, it) }
                    }


                    if (getUUID(leftChest) == getKey(inventory.itemInMainHand)){
                        "Deposit box changed successfully!".color(ChatColor.GREEN).send(event.player)
                        event.isCancelled = false
                    } else if (getUUID(leftChest) == getKey(inventory.itemInMainHand)){
                        "Deposit box changed successfully!".color(ChatColor.GREEN).send(event.player)
                        event.isCancelled = false
                    } else {
                        event.isCancelled = true
                    }


                }else{
                    event.isCancelled = false
                }


            }

        }
    }


}