package listener


import handler.DepositBoxHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerJoinEvent

class KeyListListener(): Listener {

    private val depositBoxHandler = DepositBoxHandler()


    @EventHandler
    fun getKeyListsByCommand(event: PlayerCommandPreprocessEvent){
        event.player.run {
            depositBoxHandler.getValidKeyList()
        }
    }

    @EventHandler
    fun initPersistentContainer(event: PlayerJoinEvent){
        event.player.run{
            depositBoxHandler.initPersistentData()
        }
    }




}