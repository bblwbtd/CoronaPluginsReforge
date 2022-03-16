package pages

import handler.AddressHandler
import org.bukkit.entity.Player
import viewRender.MagicLayout
import viewRender.MagicView

class ListDetailPage : MagicView() {
    override fun getLayout(): MagicLayout {
        val player = inventory.viewers.first() as Player
        val book = AddressHandler(player).getPlayerAddressBook()

        return MagicLayout()
    }
}