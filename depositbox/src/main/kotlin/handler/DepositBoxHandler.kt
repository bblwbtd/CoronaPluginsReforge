package handler

import org.bukkit.configuration.file.FileConfiguration
import utils.KeyList

class DepositBoxHandler() {



    fun getValidKeyList(): List<String>{
        return KeyList().getKeyList()
    }


}