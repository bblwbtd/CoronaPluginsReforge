package entities

data class AddressBook(
    var limit: Int = Main.plugin.config.getInt("initialAddressLimit"),
    var address: List<Address>
)
