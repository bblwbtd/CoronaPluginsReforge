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
import utils.clearKeyUUID
import utils.getUUID
import utils.keyCheck


class KeyListListener(): Listener {

    private val depositBoxHandler = DepositBoxHandler()

    @EventHandler
    fun getKeyListsByCommand(event: PlayerCommandPreprocessEvent){
        event.player.run {
            depositBoxHandler.getValidKeyList()
        }
    }

    @EventHandler
    fun checkDepositBoxChange(event: PlayerInteractEvent){
        event.player.run{
            if (event.clickedBlock?.type == Material.CHEST){
                if (!keyCheck(inventory.itemInMainHand, event.clickedBlock) && getUUID(event.clickedBlock) != ""){
                    player?.let { "Please use the corresponding key to open/destroy the chest!".color(ChatColor.RED).send(it) }
                    event.isCancelled = true
                }

            }
        }
    }

    @EventHandler
    fun clearKey(event: BlockBreakEvent){
        event.player.run {
            if (event.block?.type == Material.CHEST){
                val chestState = event.block.state as Chest
                if (chestState.inventory !is DoubleChestInventory) {
                    clearKeyUUID(inventory.itemInMainHand)
                }else{
                    event.isCancelled = false
                }

            }
        }
    }





}