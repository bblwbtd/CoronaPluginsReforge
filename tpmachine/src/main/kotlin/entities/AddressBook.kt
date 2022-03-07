package entities

import Main
import java.util.*

data class AddressBook(
    var limit: Int = Main.plugin.config.getInt("initialAddressLimit"),
    var address: LinkedList<Address> = LinkedList()
)
