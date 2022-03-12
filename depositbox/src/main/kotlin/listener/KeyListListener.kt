package listener


import handler.DepositBoxHandler
import i18n.color
import i18n.locale
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.TileState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.FurnaceBurnEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.persistence.PersistentDataType
import utils.entropyByPublicKey
import utils.getKeyList
import java.security.KeyPairGenerator

class KeyListListener(): Listener {

    private val depositBoxHandler = DepositBoxHandler()


    @EventHandler
    fun getKeyListsByCommand(event: PlayerCommandPreprocessEvent){
        event.player.run {
            depositBoxHandler.getValidKeyList()
        }
    }



}