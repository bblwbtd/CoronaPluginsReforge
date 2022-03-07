package listener


import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import utils.setKeyList

class KeyListListener: Listener {

    @EventHandler
    fun initKeyLists(event: PlayerJoinEvent){
        event.player.run {
            setKeyList().generateKeyList()
        }
    }

    @EventHandler
    fun getKeyListsbyCommand(event: AsyncPlayerChatEvent){
        event.player.run {
            setKeyList().getKeyList()
        }
    }



}