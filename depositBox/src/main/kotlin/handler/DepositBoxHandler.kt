package handler

import utils.KeyList

class DepositBoxHandler() {
    fun getValidKeyList(): List<String>{
        return KeyList().getKeyList()
    }


}