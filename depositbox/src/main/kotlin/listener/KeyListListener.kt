package listener


import handler.DepositBoxHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class KeyListListener(): Listener {

    private val depositBoxHandler = DepositBoxHandler()


    @EventHandler
    fun getKeyListsbyCommand(event: PlayerCommandPreprocessEvent){
        event.player.run {
            depositBoxHandler.getValidKeyList()
        }
    }



}