package utils



import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.Listener

class KeyList() {

    fun getKeyList(): List<String> {


        val keyList: List<String> = myConfig.getList("keys") as List<String>
        return keyList
    }




}