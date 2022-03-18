package handler

import Main
import entities.Address
import entities.AddressBook
import i18n.color
import i18n.locale
import i18n.send
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import utils.mapper
import java.io.File
import java.nio.file.Paths

class AddressHandler(private val player: Player, private val dataDir: String = Main.plugin.dataFolder.path) {

    private val file = getUserAddressBookFile()
    private val book = getPlayerAddressBook()
    private val invalidAddressNameMessage = "Invalid address name.".locale(player).color(ChatColor.RED)


    private fun getUserAddressBookFile(): File {
        return Paths.get(dataDir, "${player.name}.json").toFile()
    }

    fun getPlayerAddressBook(): AddressBook {
        val file = getUserAddressBookFile()
        if (!file.exists()) {
            return AddressBook()
        }
        return mapper.readValue(file, AddressBook::class.java)
    }

    fun savePlayerLocation(name: String): Boolean {
        if (!validateAddressName(name)) {
            invalidAddressNameMessage.send(player)
            return false
        }

        val location = player.location
        val address = Address(name, location.x, location.y, location.z, location.world!!.name)

        if (book.address.size >= book.limit) {
            "Can't save address due to maximum limit.".locale(player).color(ChatColor.RED).send(player)
            return false
        }

        if (book.address.find { item -> item.name == name } != null) {
            "Duplicated address name.".locale(player).color(ChatColor.RED).send(player)
            return false
        }

        book.address.add(address)
        saveBook()
        return true
    }

    fun renamePlayerLocation(oldName: String, newName: String) {
        if (!validateAddressName(newName)) {
            invalidAddressNameMessage.send(player)
            return
        }
        val location = book.address.find { item -> item.name == oldName }

        if (location == null) {
            "Can't find the address with name ".locale(player).plus(oldName).color(ChatColor.RED).send(player)
            return
        }
        location.name = newName
        saveBook()
        "Address $oldName has been rename to $newName".locale(player).color(ChatColor.GREEN).send(player)
    }

    private fun validateAddressName(name: String): Boolean {
        if (name.isBlank()) {
            return false
        } else if (name.length > 20) {
            return false
        }

        return true
    }

    private fun saveBook() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, Runnable {
            mapper.writeValue(file, book)
        })
    }

    fun removeAddress(addressName: String) {
        book.address.removeIf { it.name == addressName }
        saveBook()
        "Address has been removed".locale(player).color(ChatColor.GREEN).send(player)
    }

    fun updateAddress(addressName: String) {
        val address = book.getAddressByName(addressName)
        if (address == null) {
            "No address with name".locale(player).plus(": $addressName").send(player)
            return
        }

        player.location.apply {
            address.x = x
            address.y = y
            address.z = z
        }

        saveBook()
        "Address has benn updated".locale(player).color(ChatColor.GREEN).send(player)
    }
}