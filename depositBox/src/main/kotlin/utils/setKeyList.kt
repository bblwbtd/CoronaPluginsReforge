package utils


import listener.KeyListListener
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class setKeyList: JavaPlugin(), Listener {
    private val myConfig =  getConfig()
    private val myKeys = listOf("DIAMOND","REDSTONE")

    fun generateKeyList(){
        myConfig.addDefault("keys",myKeys)
        config.options().copyDefaults(true)
        saveConfig()
        getServer().getPluginManager().registerEvents(KeyListListener(), this)
    }

    fun getKeyList(): List<String>? {
        val keyList: List<String>? = config.getList("keys") as? List<String>
        return keyList
    }




}