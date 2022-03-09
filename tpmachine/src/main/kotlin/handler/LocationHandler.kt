package handler

import Main
import entities.Address
import entities.AddressBook
import org.bukkit.entity.Player
import utils.mapper
import java.io.File
import java.nio.file.Paths

class LocationHandler(private val player: Player, private val dataDir: String = Main.plugin.dataFolder.path) {

    private fun getUserAddressBookFile(): File {
        return Paths.get(dataDir, player.name).toFile()
    }

    fun getUserAddressBook(): AddressBook? {
        val file = getUserAddressBookFile()
        if (!file.exists()) {
            return null
        }
        return mapper.readValue(file, AddressBook::class.java)
    }

    fun savePlayerLocation(name: String) {
        val location = player.location
        val address = Address(name, location.x, location.y, location.z, location.world!!.name)
        val book = getUserAddressBook() ?: AddressBook()
        book.address.add(address)
        mapper.writeValue(getUserAddressBookFile(), book)
    }
}