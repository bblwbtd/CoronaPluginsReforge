package entities

import Main
import org.bukkit.Location
import java.util.*

data class AddressBook(
    var limit: Int = Main.plugin.config.getInt("initialAddressLimit"),
    var address: MutableList<Address> = LinkedList()
) {
    fun add(name: String, location: Location) {
        address.add(Address(name, location.x, location.y, location.z, location.world!!.name))
    }
}
