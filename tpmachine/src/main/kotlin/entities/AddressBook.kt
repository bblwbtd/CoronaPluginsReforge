package entities

import Main
import java.util.*

data class AddressBook(
    var limit: Int = Main.plugin.config.getInt("initialMaxAddress"),
    var address: MutableList<Address> = LinkedList()
) {
    fun getAddressByName(name: String): Address? {
        return address.find { it.name == name }
    }
}
